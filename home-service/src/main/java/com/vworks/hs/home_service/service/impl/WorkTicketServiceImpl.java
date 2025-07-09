package com.vworks.hs.home_service.service.impl;

import com.vworks.hs.common_lib.base.BaseResponse;
import com.vworks.hs.common_lib.exception.WarehouseMngtSystemException;
import com.vworks.hs.home_service.entities.WorkTicketEntity;
import com.vworks.hs.home_service.models.response.workTicket.*;
import com.vworks.hs.home_service.repository.WorkTicketRepository;
import com.vworks.hs.home_service.service.WorkTicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTicketServiceImpl implements WorkTicketService {

    private WorkTicketRepository workTicketRepository;

    @Override
    public BaseResponse<?> createWorkTicket(CreateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = new WorkTicketEntity();
        entity.setCustomerId(request.getCustomerId());
        entity.setAddress(request.getAddress());
        entity.setServiceRequest(request.getServiceRequest());
        entity.setNote(request.getNote());
        entity.setScheduledAt(request.getScheduledAt());
        entity.setCreatedBy(request.getCreatedBy());
        entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        entity.setStatus("NEW");

        workTicketRepository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> updateWorkTicket(UpdateWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = workTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "TICKET_NOT_FOUND", "Không tìm thấy phiếu"));

        entity.setAddress(request.getAddress());
        entity.setServiceRequest(request.getServiceRequest());
        entity.setNote(request.getNote());
        entity.setScheduledAt(request.getScheduledAt());
        entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(request.getUpdatedBy());

        workTicketRepository.save(entity);
        return new BaseResponse<>();
    }
    @Override
    public BaseResponse<?> assignWorker(AssignWorkerRequestBody request) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = workTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "TICKET_NOT_FOUND", "Không tìm thấy phiếu"));

        entity.setAssignedWorkerId(request.getAssignedWorkerId());
        entity.setStatus("ASSIGNED");
        entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(request.getUpdatedBy());

        workTicketRepository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> updateStatus(UpdateStatusRequestBody request) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = workTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "TICKET_NOT_FOUND", "Không tìm thấy phiếu"));

        entity.setStatus(request.getStatus());
        entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(request.getUpdatedBy());

        workTicketRepository.save(entity);
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> getWorkTicketDetail(GetDetailWorkTicketRequestBody request) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = workTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "TICKET_NOT_FOUND", "Không tìm thấy phiếu"));

        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> searchWorkTicket(SearchWorkTicketRequestBody request) throws WarehouseMngtSystemException {
        List<WorkTicketEntity> result = workTicketRepository.searchCustom(
                request.getStatus(),
                request.getCustomerId(),
                request.getAssignedWorkerId(),
                request.getFromDate(),
                request.getToDate()
        );
        return new BaseResponse<>();
    }

    @Override
    public BaseResponse<?> deleteWorkTicket(DeleteWorkTicketRequestBody request, HttpServletRequest httpServletRequest) throws WarehouseMngtSystemException {
        WorkTicketEntity entity = workTicketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new WarehouseMngtSystemException(404, "TICKET_NOT_FOUND", "Không tìm thấy phiếu"));

        workTicketRepository.delete(entity);
        return new BaseResponse<>();
    }

}
