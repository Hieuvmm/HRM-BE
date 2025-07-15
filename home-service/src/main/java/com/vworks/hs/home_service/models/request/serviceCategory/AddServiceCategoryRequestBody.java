package com.vworks.hs.home_service.models.request.serviceCategory;

import lombok.Data;

@Data
public class AddServiceCategoryRequestBody {
    private Long id;
    private String name;
    private String code;
    private String description;
}
