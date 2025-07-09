package com.vworks.hs.home_service.models.response.workTicket;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateWorkTicketRequestBody {
    private String ticketId;
    private String address;
    private String serviceRequest;
    private String note;
    private Timestamp scheduledAt;
    private String updatedBy;
}
