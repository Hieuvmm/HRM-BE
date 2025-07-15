package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.ServiceCategory;
import com.vworks.hs.home_service.models.request.serviceCategory.AddServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.DeleteServiceCategoryRequestBody;
import com.vworks.hs.home_service.models.request.serviceCategory.UpdateServiceCategoryRequestBody;
import com.vworks.hs.home_service.repository.ServiceCategoryRepository;
import com.vworks.hs.home_service.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    private final ServiceCategoryRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<?> add(AddServiceCategoryRequestBody request) {
        ServiceCategory entity = modelMapper.map(request, ServiceCategory.class);
        entity.setCreatedAt(new Timestamp(currentTimeMillis()));
        entity.setIsActive(true);
        repository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> update(UpdateServiceCategoryRequestBody request) throws WarehouseMngtSystemException {
        ServiceCategory entity = repository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "CATEGORY_NOT_FOUND", "Danh mục không tồn tại"));
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCreatedAt(new Timestamp(currentTimeMillis()));
        repository.save(entity);
        return new BaseResponse<>(entity);
    }

    @Override
    public BaseResponse<?> delete(DeleteServiceCategoryRequestBody request) throws WarehouseMngtSystemException {
        ServiceCategory entity = repository.findById(request.getId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "CATEGORY_NOT_FOUND", "Danh mục không tồn tại"));
        entity.setIsActive(false);
        repository.save(entity);
        return new BaseResponse<>("Deleted logically");
    }

    @Override
    public BaseResponse<?> list() {
        return new BaseResponse<>(repository.findAll());
    }

    @Override
    public BaseResponse<?> getById(Long id) {
        return new BaseResponse<>(repository.findById(id));
    }
}
