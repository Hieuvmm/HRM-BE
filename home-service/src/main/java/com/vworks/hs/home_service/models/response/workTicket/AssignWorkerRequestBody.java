package com.vworks.hs.home_service.models.response.workTicket;


import lombok.Data;

@Data
public class AssignWorkerRequestBody {
    private String ticketId;
    private String assignedWorkerId;
    private String updatedBy;
}
