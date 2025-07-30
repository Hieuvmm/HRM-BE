package com.vworks.hs.home_service.models.request.hashtag;

import lombok.Data;

@Data
public class CreateHashtagRequest {
    private String name;
    private String slug;
}