package com.vworks.hs.home_service.models.response.workTicket;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SearchWorkTicketRequestBody {
    private String status;
    private String customerId;
    private String assignedWorkerId;
    private Timestamp fromDate;
    private Timestamp toDate;}
