package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "work_ticket_material", schema = "home-service")
public class WorkTicketMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String workTicketId;
    private String materialId;
    private String quantity;
    private String unitTypeId;
    private Double price;
    private String addedBy;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String updatedBy;
}
