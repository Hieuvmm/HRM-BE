package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "categories", schema = "home-service")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String slug;
    private String parentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
