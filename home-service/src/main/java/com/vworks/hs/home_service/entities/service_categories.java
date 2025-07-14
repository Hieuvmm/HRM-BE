package com.vworks.hs.home_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_categories", schema = "home-service")
public class service_categories {
    @Id
    private Long id;

    private String name;

    private String code;

    private String description;

    private String icon;

    private Boolean isActive = true;

    private Integer order;

    private Timestamp createdAt;

    private Timestamp updatedAt;


}
