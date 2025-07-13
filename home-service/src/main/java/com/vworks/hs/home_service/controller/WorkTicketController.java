package com.vworks.hs.home_service.controller;


import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.config.WhsConstant;
import com.vworks.hs.home_service.models.response.workTicket.*;
import com.vworks.hs.home_service.service.WorkTicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(WhsConstant.RequestMapping.WHS_WORK_TICKET)
public class WorkTicketController {
    private final WorkTicketService workTicketService;

    @PostMapping("/create")
    public BaseResponse<?> create(@RequestBody CreateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return workTicketService.createWorkTicket(request, httpServletRequest);
    }

    @PostMapping("/update")
    public BaseResponse<?> update(@RequestBody UpdateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return workTicketService.updateWorkTicket(request, httpServletRequest);
    }

    @PostMapping("/assign")
    public BaseResponse<?> assign(@RequestBody AssignWorkerRequestBody request) throws WarehouseMngtSystemException {
        return workTicketService.assignWorker(request);
    }

    @PostMapping("/status")
    public BaseResponse<?> updateStatus(@RequestBody UpdateStatusRequestBody request) throws WarehouseMngtSystemException {
        return workTicketService.updateStatus(request);
    }

    @PostMapping("/detail")
    public BaseResponse<?> getDetail(@RequestBody GetDetailWorkTicketRequestBody request) throws WarehouseMngtSystemException {
        return workTicketService.getWorkTicketDetail(request);
    }

    @PostMapping("/search")
    public BaseResponse<?> search(@RequestBody SearchWorkTicketRequestBody request) throws WarehouseMngtSystemException {
        return workTicketService.searchWorkTicket(request);
    }

    @PostMapping("/delete")
    public BaseResponse<?> delete(@RequestBody DeleteWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        return workTicketService.deleteWorkTicket(request, httpServletRequest);
    }
}
