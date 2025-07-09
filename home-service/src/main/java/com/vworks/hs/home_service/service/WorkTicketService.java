package com.vworks.hs.home_service.service;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.models.response.workTicket.*;
import jakarta.servlet.http.HttpServletRequest;

public interface WorkTicketService {
    BaseResponse<?> createWorkTicket(CreateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> updateWorkTicket(UpdateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;

    BaseResponse<?> assignWorker(AssignWorkerRequestBody request) throws WarehouseMngtSystemException;

    BaseResponse<?> updateStatus(UpdateStatusRequestBody request) throws WarehouseMngtSystemException;

    BaseResponse<?> getWorkTicketDetail(GetDetailWorkTicketRequestBody request) throws WarehouseMngtSystemException;

    BaseResponse<?> searchWorkTicket(SearchWorkTicketRequestBody request) throws WarehouseMngtSystemException;

    BaseResponse<?> deleteWorkTicket(DeleteWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException;
}

