package com.vworks.hs.home_service.models.request.contens;

import lombok.Data;

@Data
public class PostHashtagCreateRequest {
    private Long postId;
    private Long hashtagId;
}
