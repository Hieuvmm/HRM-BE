package com.vworks.hs.home_service.models.request.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
    private String seoTitle;
    private String metaDescription;
    private String status;
    private Long createdBy;
}
