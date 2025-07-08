package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.request.exportRequest.CreateExportRequestBody;
import com.vworks.hs.home_service.service.ExportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_WH_TRANSFER)
public class ExportRequestController {

    private final ExportRequestService exportRequestService;

    @PostMapping("/create")
    public BaseResponse<?> createExRequestController(@RequestBody CreateExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportRequestService.createExportRequest(requestBody, httpServletRequest);
    }
}
