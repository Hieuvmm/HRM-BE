package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "service_packages", schema = "home-service")
public class ServicePackages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
