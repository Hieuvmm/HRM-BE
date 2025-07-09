package com.vworks.hs.home_service.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "worker_inventory", schema = "home-service")
public class WorkerInventoryEntity {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "user_id")
    private String userId;

    private String materialId;

    private Integer quantity;

    private Timestamp updated_at;
}
