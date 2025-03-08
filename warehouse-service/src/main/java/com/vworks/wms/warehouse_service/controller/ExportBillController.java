package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.common_lib.base.BaseResponse;
import com.vworks.wms.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.wms.warehouse_service.models.request.SearchExBillRequestBody;
import com.vworks.wms.warehouse_service.models.request.exportBill.*;
import com.vworks.wms.warehouse_service.service.ExportBillService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${whs-properties.api-prefix}/ex-bill/")
@CrossOrigin("*")
public class ExportBillController {
    @Autowired
    private ExportBillService exportBillService;

    @PostMapping("create")
    BaseResponse createExBillController(@RequestBody CreateExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportBillService.createExBill(requestBody, httpServletRequest);
    }

    @PostMapping("search")
    BaseResponse searchExBillController(@RequestBody SearchExBillRequestBody requestBody) throws WarehouseMngtSystemException {
//        return exportBillService.searchExBill(requestBody);
        return new BaseResponse(exportBillService.searchExBillV2(requestBody));
    }

    @PostMapping("approval")
    BaseResponse approvalController(@RequestBody PostApprovalExBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportBillService.approvalExBill(requestBody, httpServletRequest);
    }

    @PostMapping("detail")
    BaseResponse<?> detailController(@RequestBody PostGetDetailExportBillRequestBody requestBody) throws WarehouseMngtSystemException {
        return new BaseResponse<>(exportBillService.deTailExBill(requestBody));
    }

    @PostMapping("update")
    BaseResponse updateController(@RequestBody PostUpdateExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportBillService.updateExBill(requestBody, httpServletRequest);
    }

    @PostMapping("delete")
    BaseResponse deleteController(@RequestBody PostDeleteExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportBillService.deleteExBill(requestBody, httpServletRequest);
    }
    @PostMapping("assign-approval")
    BaseResponse assignApprovalController(@RequestBody PostAssignApprovalRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return exportBillService.assignAproval(requestBody, httpServletRequest);
    }
}
