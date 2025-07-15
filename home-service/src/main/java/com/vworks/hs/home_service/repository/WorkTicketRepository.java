package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.WorkTicketEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface WorkTicketRepository extends JpaRepository<WorkTicketEntity, String> {
    @Query("SELECT wt FROM WorkTicketEntity wt " +
            "WHERE (:status IS NULL OR wt.status = :status) " +
            "AND (:customerId IS NULL OR wt.customerId = :customerId) " +
            "AND (:workerId IS NULL OR wt.assignedWorkerId = :workerId) " +
            "AND (:fromDate IS NULL OR wt.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR wt.createdAt <= :toDate) " +
            "ORDER BY wt.createdAt DESC")
    List<WorkTicketEntity> searchCustom(
            @Param("status") String status,
            @Param("customerId") String customerId,
            @Param("workerId") String workerId,
            @Param("fromDate") Timestamp fromDate,
            @Param("toDate") Timestamp toDate
    );

}
