package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "export_request_detail", schema = "home-service")
public class ExportRequestDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_type_id")
    private Integer unitTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }
}
