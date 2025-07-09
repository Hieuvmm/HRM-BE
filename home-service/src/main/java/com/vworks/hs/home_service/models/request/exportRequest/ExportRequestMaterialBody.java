package com.vworks.hs.home_service.models.request.exportRequest;

import lombok.Data;

@Data
public class ExportRequestMaterialBody {
    private String materialId;
    private Integer quantity;
    private String unitTypeId;
}
