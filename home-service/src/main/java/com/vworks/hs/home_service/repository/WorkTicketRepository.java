package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.WorkTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTicketRepository extends JpaRepository<WorkTicketEntity, String> {
}
