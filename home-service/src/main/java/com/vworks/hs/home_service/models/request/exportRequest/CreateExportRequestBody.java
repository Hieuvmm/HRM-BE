package com.vworks.hs.home_service.models.request.exportRequest;

import lombok.Data;

import java.util.List;

@Data
public class CreateExportRequestBody {
    private String requesterId;
    private String warehouseId;
    private String note;
    private List<ExportRequestMaterialBody> materials;
}
