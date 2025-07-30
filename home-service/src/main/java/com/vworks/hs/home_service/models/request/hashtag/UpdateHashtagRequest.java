package com.vworks.hs.home_service.models.request.hashtag;

import lombok.Data;

@Data
public class UpdateHashtagRequest {
    private Long id;
    private String name;
    private String slug;
}