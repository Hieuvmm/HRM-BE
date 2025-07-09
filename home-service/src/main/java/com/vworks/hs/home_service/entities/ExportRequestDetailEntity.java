package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "export_request_detail", schema = "home-service")
public class ExportRequestDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_type_id")
    private String unitTypeId;

}
