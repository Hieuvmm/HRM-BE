package com.vworks.hs.home_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_packages", schema = "home-service")
public class service_packages {
    @Id
    private Long id;


    private Long categoryId;


    private String name;


    private String code;

    private String description;


    private BigDecimal price;


    private String unit;


    private Integer durationMinutes;


    private String imageUrl;


    private Boolean isActive = true;


    private Integer order;


    private Timestamp createdAt;


    private Timestamp updatedAt;
}
