package com.vworks.hs.home_service.models.request.exportRequest;

import lombok.Data;

@Data
public class ApprovalExportRequestBody {
    private String requestId;
    private String status;
    private String approvedBy;
    private String reason;
}
