package com.vworks.wms.warehouse_service.models.response.material;

import lombok.*;

import java.sql.Timestamp;

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
}
