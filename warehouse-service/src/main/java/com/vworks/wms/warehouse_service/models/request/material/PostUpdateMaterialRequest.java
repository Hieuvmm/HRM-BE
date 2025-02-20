package com.vworks.wms.warehouse_service.models.request.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostUpdateMaterialRequest extends BaseMaterialRequest {
    private String id;
}
