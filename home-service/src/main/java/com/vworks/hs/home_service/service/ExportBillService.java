package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.request.SearchExBillRequestBody;
import com.vworks.hs.home_service.models.request.exportBill.*;

import com.vworks.hs.home_service.models.response.SearchExBillResponseBody;
import com.vworks.hs.home_service.models.response.exportBill.PostGetExportBillDetailResponseBody;
import jakarta.servlet.http.HttpServletRequest;

public interface ExportBillService {
    BaseResponse<?> createExBill(CreateExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> searchExBill(SearchExBillRequestBody requestBody) throws WarehouseMngtSystemException;

    BaseResponse<?> approvalExBill(PostApprovalExBillRequestBody requestBody, HttpServletRequest httpRequest) throws WarehouseMngtSystemException;

    PostGetExportBillDetailResponseBody deTailExBill(PostGetDetailExportBillRequestBody requestBody) throws WarehouseMngtSystemException;

    BaseResponse<?> updateExBill(PostUpdateExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    SearchExBillResponseBody searchExBillV2(SearchExBillRequestBody requestBody);

    BaseResponse<?> deleteExBill(PostDeleteExportBillRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> assignAproval(PostAssignApprovalRequestBody requestBody, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}
