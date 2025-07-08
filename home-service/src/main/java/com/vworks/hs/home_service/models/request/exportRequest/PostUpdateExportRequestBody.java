package com.vworks.hs.home_service.models.request.exportRequest;

import com.vworks.hs.home_service.models.ProductExModel;
import lombok.Data;

import java.util.List;

@Data
public class PostUpdateExportRequestBody {
    private Integer id;
    private Integer warehouseId;
    private String note;
    private List<ExportRequestMaterialBody> materials;
    private String status;
}
