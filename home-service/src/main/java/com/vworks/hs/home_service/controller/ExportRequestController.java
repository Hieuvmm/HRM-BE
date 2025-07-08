package com.vworks.hs.home_service.controller;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.*;
import com.vworks.hs.home_service.service.ExportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/approval")
    public BaseResponse<?> approvalExportRequest(@RequestBody ApprovalExportRequestBody requestBody,
                                                 HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportRequestService.approvalExportRequest(requestBody, httpServletRequest);
    }

    @PostMapping("/search")
    public BaseResponse<?> search(@RequestBody SearchExportRequestBody requestBody) throws WarehouseMngtSystemException {
        return exportRequestService.searchExportRequest(requestBody);
    }
    @PostMapping("/detail")
    public BaseResponse<?> detail(@RequestBody PostGetDetailExportRequestBody requestBody) throws WarehouseMngtSystemException {
        return exportRequestService.detailExportRequest(requestBody);
    }
    @PostMapping("/delete")
    public BaseResponse<?> deletePost(@RequestBody PostDeleteExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportRequestService.deleteExportRequest(requestBody, httpServletRequest);
    }
    @PostMapping("/update")
    public BaseResponse<?> updatePost(@RequestBody PostUpdateExportRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportRequestService.updateExportRequest(requestBody, httpServletRequest);
    }
}
