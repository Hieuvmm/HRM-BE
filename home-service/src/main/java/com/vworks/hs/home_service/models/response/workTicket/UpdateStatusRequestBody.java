package com.vworks.hs.home_service.models.response.workTicket;

import lombok.Data;

@Data
public class UpdateStatusRequestBody {
    private String ticketId;
    private String status;
    private String updatedBy;
}
