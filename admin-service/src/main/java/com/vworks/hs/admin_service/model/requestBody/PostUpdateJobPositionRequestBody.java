package com.vworks.hs.admin_service.model.requestBody;

import lombok.Data;

@Data
public class PostUpdateJobPositionRequestBody {
    private String code;
    private String name;
    private String status;
    private String desc;
}
