package com.vworks.wms.warehouse_service.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialOrderModel {
    private String code;
    private String name;
    private int quantity;
    private BigDecimal price;
}
