package com.vworks.hs.home_service.models.request.contens;

import lombok.Data;

import java.util.List;

@Data
public class PostHashtagBatchRequest {
    private Long postId;
    private List<Long> hashtagIds;
}
