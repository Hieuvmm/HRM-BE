package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.ExportRequestDetailEntity;
import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.entities.WorkerInventoryEntity;
import com.vworks.hs.home_service.models.request.exportRequest.*;
import com.vworks.hs.home_service.repository.ExportRequestDetailRepository;
import com.vworks.hs.home_service.repository.ExportRequestRepository;
import com.vworks.hs.home_service.repository.WorkerInventoryRepository;
import com.vworks.hs.home_service.service.ExportRequestService;
import com.vworks.hs.home_service.utils.Commons;
import com.vworks.hs.home_service.utils.ExceptionTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import com.vworks.hs.common_lib.utils.StatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j

public class ExportRequestServiceImpl implements ExportRequestService {
    private final ExportRequestRepository exportRequestRepository;
    private final ExportRequestDetailRepository exportRequestDetailRepository;
    private final WorkerInventoryRepository workerInventoryRepository;

    public ExportRequestServiceImpl(ExportRequestRepository exportRequestRepository, ExportRequestDetailRepository exportRequestDetailRepository, WorkerInventoryRepository workerInventoryRepository) {
        this.exportRequestRepository = exportRequestRepository;
        this.exportRequestDetailRepository = exportRequestDetailRepository;
        this.workerInventoryRepository = workerInventoryRepository;
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
    @Override
    public BaseResponse approvalExportRequest(ApprovalExportRequestBody requestBody, HttpServletRequest httpServletRequest)
            throws WarehouseMngtSystemException {

        ExportRequestEntity request = exportRequestRepository.findById(requestBody.getApprovedBy())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "NOT_FOUND", "Không tìm thấy phiếu yêu cầu"));

        if (!StatusUtil.NEW.name().equals(request.getStatus())) {
            throw new WarehouseMngtSystemException(400, "INVALID_STATUS", "Phiếu không ở trạng thái chờ duyệt");
        }

        String username = httpServletRequest.getHeader(Commons.USER_CODE_FIELD);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (requestBody.getStatus().equalsIgnoreCase(StatusUtil.APPROVED.name())) {
            request.setStatus(StatusUtil.APPROVED.name());
            request.setApprovedAt(now);
            request.setApprovedBy(username);

            List<ExportRequestDetailEntity> details = exportRequestDetailRepository.findByRequestId(request.getId());

            for (ExportRequestDetailEntity item : details) {
                WorkerInventoryEntity wi = new WorkerInventoryEntity();
                wi.setUserId(request.getRequesterId());
                wi.setMaterialId(item.getMaterialId());
                wi.setQuantity(item.getQuantity());
                wi.setUpdated_at(new Timestamp(System.currentTimeMillis()));

                workerInventoryRepository.save(wi);
            }


        } else if (requestBody.getStatus().equalsIgnoreCase(StatusUtil.REFUSED.name())) {
            if (StringUtils.isEmpty(requestBody.getReason())) {
                throw new WarehouseMngtSystemException(400, "REASON_EMPTY", "Lý do từ chối không được để trống.");
            }
            request.setStatus(StatusUtil.REFUSED.name());
            request.setNote((request.getNote() != null ? request.getNote() : "") + "\nLý do từ chối: " + requestBody.getReason());
        }

        request.setApprovedAt(now);
        request.setApprovedBy(username);
        exportRequestRepository.save(request);

        return new BaseResponse("Phiếu yêu cầu đã được xử lý.");
    }
    @Override
    public BaseResponse<?> searchExportRequest(SearchExportRequestBody requestBody) throws WarehouseMngtSystemException {
        List<ExportRequestEntity> result;

        if (StringUtils.isNotEmpty(requestBody.getStatus()) && requestBody.getCode() != null) {
            result = exportRequestRepository.findAllByStatusAndRequesterId(
                    requestBody.getStatus(), requestBody.getRequesterId());
        } else if (requestBody.getRequesterId() != null) {
            result = exportRequestRepository.findAllByRequesterId(requestBody.getRequesterId());
        } else if (StringUtils.isNotEmpty(requestBody.getStatus())) {
            result = exportRequestRepository.findAllByStatus(requestBody.getStatus());
        } else {
            result = exportRequestRepository.findAll();
        }

        return new BaseResponse<>(result);
    }

    @Override
    public BaseResponse<?> deleteExportRequest(PostDeleteExportRequestBody requestBody, HttpServletRequest httpServletRequest)
            throws WarehouseMngtSystemException {
        String id = requestBody.getId();
        ExportRequestEntity entity = getExportRequestById(id);

        exportRequestDetailRepository.deleteAllByRequestId(id);
        exportRequestRepository.deleteById(id);

        return new BaseResponse<>("Đã xoá phiếu yêu cầu.");
    }


    @Override
    public BaseResponse<?> updateExportRequest(PostUpdateExportRequestBody requestBody, HttpServletRequest httpServletRequest)
            throws WarehouseMngtSystemException {

        String exportRequestId = requestBody.getId();
        ExportRequestEntity entity = getExportRequestById(exportRequestId);

        // Xoá chi tiết cũ
        exportRequestDetailRepository.deleteAllByRequestId(exportRequestId);

        // Cập nhật thông tin phiếu
        entity.setNote(requestBody.getNote());
        entity.setWarehouseId(requestBody.getWarehouseId());
        exportRequestRepository.save(entity);

        // Ghi lại chi tiết vật tư mới
        for (ExportRequestMaterialBody item : requestBody.getMaterials()) {
            ExportRequestDetailEntity detail = new ExportRequestDetailEntity();
            detail.setRequestId(exportRequestId);
            detail.setMaterialId(item.getMaterialId());
            detail.setQuantity(item.getQuantity());
            detail.setUnitTypeId(item.getUnitTypeId());
            exportRequestDetailRepository.save(detail);
        }

        return new BaseResponse<>("Cập nhật thành công.");
    }


    @Override
    public BaseResponse<?> detailExportRequest(PostGetDetailExportRequestBody requestBody) throws WarehouseMngtSystemException {
        String id = requestBody.getId();
        ExportRequestEntity entity = getExportRequestById(id);
        List<ExportRequestDetailEntity> details = exportRequestDetailRepository.findByRequestId(id);

        return new BaseResponse<>(new Object() {
            public ExportRequestEntity header = entity;
            public List<ExportRequestDetailEntity> detail = details;
        });
    }

    private ExportRequestEntity getExportRequestById(String id) throws WarehouseMngtSystemException {
        return exportRequestRepository.findById(id)
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "NOT_FOUND", "Không tìm thấy phiếu yêu cầu"));
    }
}
