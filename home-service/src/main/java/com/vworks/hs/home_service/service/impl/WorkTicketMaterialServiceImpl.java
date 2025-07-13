package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.home_service.entities.WorkTicketMaterialEntity;
import com.vworks.hs.home_service.models.request.workTicketMaterial.AddWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.DeleteWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.models.request.workTicketMaterial.UpdateWorkTicketMaterialRequestBody;
import com.vworks.hs.home_service.repository.WorkTicketMaterialRepository;
import com.vworks.hs.home_service.service.WorkTicketMaterialService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTicketMaterialServiceImpl implements WorkTicketMaterialService {

    private final WorkTicketMaterialRepository repository;

    @Override
    public BaseResponse<?> add(AddWorkTicketMaterialRequestBody request) {
        WorkTicketMaterialEntity entity = new WorkTicketMaterialEntity();
        entity.setWorkTicketId(request.getWorkTicketId());
        entity.setMaterialId(request.getMaterialId());
        entity.setQuantity(request.getQuantity());
        entity.setUnitTypeId(request.getUnitTypeId());
        entity.setPrice(request.getPrice());
        entity.setAddedBy(request.getAddedBy());
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        repository.save(entity);
        return new  BaseResponse<>();
    }

    @Override
    public BaseResponse<?> update(UpdateWorkTicketMaterialRequestBody request) {
        WorkTicketMaterialEntity entity = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vật tư"));

        entity.setQuantity(request.getQuantity());
        entity.setPrice(request.getPrice());
        entity.setUpdatedBy(request.getUpdatedBy());
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        repository.save(entity);
        return new  BaseResponse<>();
    }

    @Override
    public BaseResponse<?> delete(DeleteWorkTicketMaterialRequestBody request) {
        repository.deleteById(request.getId());
        return new  BaseResponse<>();
    }

    @Override
    public BaseResponse<?> list(String workTicketId) {
        List<WorkTicketMaterialEntity> list = repository.findByWorkTicketId(workTicketId);
        return new  BaseResponse<>();
    }
}
