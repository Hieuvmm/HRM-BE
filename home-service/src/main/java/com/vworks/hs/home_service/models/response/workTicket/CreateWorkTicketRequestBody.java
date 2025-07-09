package com.vworks.hs.home_service.models.response.workTicket;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreateWorkTicketRequestBody {
    private String customerId;
    private String address;
    private String serviceRequest;
    private String note;
    private Timestamp scheduledAt;
    private String createdBy;
}
