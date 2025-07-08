package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface ExportRequestService {
    BaseResponse<?> createExportRequest(CreateExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> approvalExportRequest(ApprovalExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> searchExportRequest(SearchExportRequestBody requestBody) throws WarehouseMngtSystemException;

    BaseResponse<?> deleteExportRequest(PostDeleteExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> updateExportRequest(PostUpdateExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> detailExportRequest(PostGetDetailExportRequestBody requestBody) throws WarehouseMngtSystemException;

}

