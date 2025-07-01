package com.vworks.hs.home_service.models.request.exportBill;

import lombok.Data;

import java.util.List;

@Data
public class PostAssignApprovalRequestBody {
    private List<String> exportBillCodes;
    private List<String> follows;
    private List<String> approves;
}
