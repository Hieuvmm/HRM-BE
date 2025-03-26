package com.vworks.wms.warehouse_service.models.response.material;

import jakarta.persistence.ParameterMode;
import lombok.*;

import java.sql.ParameterMetaData;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseMaterialResponse {
    private String id;
    private String code;
    private String name;
    private String description;
    private String listPrice;
    private String status;
    private String createdBy;
    private Timestamp createdDate;
    private String updatedBy;
    private Timestamp updatedDate;
    private String unit;
    private String priceDiscount;
    private String materialType;
    private List<ParameterModel> parameterModels;
}
