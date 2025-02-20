package com.vworks.wms.warehouse_service.models.response.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostListObjectResBody {
    private String code;
    private String name;
    private String type;
    private String agentLevelCode;
    private String phoneNumber;
    private Integer provinceCode;
    private Integer districtCode;
    private String addressDetail;
    private String status;
}
