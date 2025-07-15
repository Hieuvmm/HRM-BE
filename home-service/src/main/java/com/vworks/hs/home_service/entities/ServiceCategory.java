package com.vworks.hs.home_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "service_categories", schema = "home-service")
public class ServiceCategory {
    @Id
    private Long id;

    private String name;

    private String code;

    private String description;

    private String icon;

    private Boolean isActive = true;
    @Column(name = "\"order\"")
    private Integer order;

    private Timestamp createdAt;

    private Timestamp updatedAt;


}
