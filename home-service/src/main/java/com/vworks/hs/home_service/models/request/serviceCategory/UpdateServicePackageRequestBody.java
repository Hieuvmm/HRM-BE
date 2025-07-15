package com.vworks.hs.home_service.models.request.serviceCategory;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateServicePackageRequestBody {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String unit;
    private Integer durationMinutes;
    private String imageUrl;
}
