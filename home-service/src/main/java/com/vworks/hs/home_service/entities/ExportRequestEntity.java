package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "export_request", schema = "home-service")
public class ExportRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "requester_id")
    private String requesterId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    private Timestamp approvedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;


    public ExportRequestEntity(Timestamp createdAt, Timestamp approvedAt, String approvedBy, String status, String note, String warehouseId, String requesterId, String code, String id) {
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
        this.approvedBy = approvedBy;
        this.status = status;
        this.note = note;
        this.warehouseId = warehouseId;
        this.requesterId = requesterId;
        this.code = code;
        this.id = id;
    }

    public ExportRequestEntity() {}
}
