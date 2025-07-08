package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.ExportRequestDetailEntity;
import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.CreateExportRequestBody;
import com.vworks.hs.home_service.models.request.exportRequest.ExportRequestMaterialBody;
import com.vworks.hs.home_service.repository.ExportRequestDetailRepository;
import com.vworks.hs.home_service.repository.ExportRequestRepository;
import com.vworks.hs.home_service.service.ExportRequestService;
import com.vworks.hs.home_service.utils.ExceptionTemplate;
import jakarta.servlet.http.HttpServletRequest;
import com.vworks.hs.common_lib.utils.StatusUtil;

import java.sql.Timestamp;
import java.util.Objects;

public class ExportRequestServiceImpl implements ExportRequestService {
    private final ExportRequestRepository exportRequestRepository;
    private final ExportRequestDetailRepository exportRequestDetailRepository;

    public ExportRequestServiceImpl(ExportRequestRepository exportRequestRepository, ExportRequestDetailRepository exportRequestDetailRepository) {
        this.exportRequestRepository = exportRequestRepository;
        this.exportRequestDetailRepository = exportRequestDetailRepository;
    }

    @Override
    public BaseResponse createExportRequest(CreateExportRequestBody requestBody, HttpServletRequest httpServletRequest)
            throws WarehouseMngtSystemException {

        if (Objects.isNull(requestBody.getRequesterId()) ||
                Objects.isNull(requestBody.getWarehouseId()) ||
                requestBody.getMaterials() == null || requestBody.getMaterials().isEmpty()) {
            throw new WarehouseMngtSystemException(400, ExceptionTemplate.INPUT_EMPTY.getCode(), "Thiếu thông tin yêu cầu xuất");
        }

        // Tạo phiếu yêu cầu xuất
        ExportRequestEntity requestEntity = new ExportRequestEntity();
        requestEntity.setCode("REQ-" + System.currentTimeMillis());
        requestEntity.setRequesterId(requestBody.getRequesterId());
        requestEntity.setWarehouseId(requestBody.getWarehouseId());
        requestEntity.setNote(requestBody.getNote());
        requestEntity.setStatus(StatusUtil.NEW.name());
        requestEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        exportRequestRepository.save(requestEntity);

        // Ghi chi tiết vật tư
        for (ExportRequestMaterialBody material : requestBody.getMaterials()) {
            ExportRequestDetailEntity detail = new ExportRequestDetailEntity();
            detail.setRequestId(requestEntity.getId());
            detail.setMaterialId(material.getMaterialId());
            detail.setQuantity(material.getQuantity());
            detail.setUnitTypeId(material.getUnitTypeId());
            exportRequestDetailRepository.save(detail);
        }

        return new BaseResponse("Yêu cầu xuất đã được gửi thành công.");
    }
}
