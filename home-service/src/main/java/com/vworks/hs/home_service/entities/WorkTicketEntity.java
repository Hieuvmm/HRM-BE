package com.vworks.hs.home_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "work_ticket", schema = "home-service")
public class WorkTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String createdBy;
    private String customerId;
    private String address;
    private String serviceRequest;
    private String note;
    private String status;
    private String assignedWorkerId;
    private Timestamp scheduledAt;
    private Timestamp completedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String updatedBy;
}
