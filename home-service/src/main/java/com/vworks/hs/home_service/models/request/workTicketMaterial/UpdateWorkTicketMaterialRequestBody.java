package com.vworks.hs.home_service.models.request.workTicketMaterial;


import lombok.Data;

@Data
public class UpdateWorkTicketMaterialRequestBody {
    private String id;
    private String quantity;
    private Double price;
    private String updatedBy;
}
