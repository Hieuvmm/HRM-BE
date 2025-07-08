package com.vworks.hs.home_service.models.request.exportRequest;

import lombok.Data;

@Data
public class ExportRequestMaterialBody {
    private Integer materialId;
    private Integer quantity;
    private Integer unitTypeId;
}
