package com.vworks.hs.home_service.models.request.exportRequest;

import lombok.Data;

@Data
public class ApproveExportRequestBody {
    private Integer requestId;
    private Integer approvedBy;
}
