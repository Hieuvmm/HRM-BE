package com.vworks.hs.home_service.models.request.workTicketMaterial;

import lombok.Data;

@Data
public class AddWorkTicketMaterialRequestBody {
    private String workTicketId;
    private String materialId;
    private String quantity;
    private String unitTypeId;
    private Double price;
    private String addedBy;
}
