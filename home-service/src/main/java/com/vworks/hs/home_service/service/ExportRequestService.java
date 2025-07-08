package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.exportRequest.CreateExportRequestBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface ExportRequestService {
    BaseResponse createExportRequest(CreateExportRequestBody requestBody, HttpServletRequest request) throws WarehouseMngtSystemException;

}
