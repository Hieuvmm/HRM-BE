package com.vworks.wms.warehouse_service.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetailWareHouseModel {
    private String productCode;
    private String category;
    private String productName;
    private String unit;
    private String quantity;
    private String price;
    private String ccy;
    private String sellPrice;
    private String provider;
}
