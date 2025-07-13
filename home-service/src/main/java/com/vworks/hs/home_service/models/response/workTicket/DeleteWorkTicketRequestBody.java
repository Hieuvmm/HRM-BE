package com.vworks.hs.home_service.models.response.workTicket;

import lombok.Data;

@Data
public class DeleteWorkTicketRequestBody {
    private String ticketId;
    private String deletedBy;
}
