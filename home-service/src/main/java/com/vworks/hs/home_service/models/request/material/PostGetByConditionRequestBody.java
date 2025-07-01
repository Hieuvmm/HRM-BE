package com.vworks.hs.home_service.models.request.material;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostGetByConditionRequestBody {
    private String positionCode;
    private String materialTypeCode;
}
