package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.ServicePackages;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServicePackageRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServicePackageRequestBody;
import com.vworks.hs.home_service.repository.ServicePackageRepository;
import com.vworks.hs.home_service.service.ServicePackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;


import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicePackageServiceImpl implements ServicePackageService {
    private final ServicePackageRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<?> add(AddServicePackageRequestBody request) {
        ServicePackages entity = modelMapper.map(request, ServicePackages.class);
        entity.setCreatedAt(new Timestamp(currentTimeMillis()));
        entity.setIsActive(true);
        repository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> update(UpdateServicePackageRequestBody request) throws WarehouseMngtSystemException {
        ServicePackages entity = repository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "PACKAGE_NOT_FOUND", "Không tìm thấy gói dịch vụ"));
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setUnit(request.getUnit());
        entity.setDurationMinutes(request.getDurationMinutes());
        entity.setImageUrl(request.getImageUrl());
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));



        repository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> delete(DeleteServicePackageRequestBody request) throws WarehouseMngtSystemException {
        ServicePackages entity = repository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "PACKAGE_NOT_FOUND", "Không tìm thấy gói dịch vụ"));
        entity.setIsActive(false);
        repository.save(entity);
        return new BaseResponse<>("Deleted logically");
    }

    @Override
    public BaseResponse<?> list() {
        return new BaseResponse<>(repository.findAll());
    }

    @Override
    public BaseResponse<?> getByCategoryId(Long categoryId) {
        return new BaseResponse<>(repository.findByCategoryId(categoryId));
    }
}
