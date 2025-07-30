package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts", schema = "home-service")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;

    private String content;

    private String featuredImage;
    private String seoTitle;
    private String metaDesc;
    private String status;

    private Long authorId;
    private Long categoryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
