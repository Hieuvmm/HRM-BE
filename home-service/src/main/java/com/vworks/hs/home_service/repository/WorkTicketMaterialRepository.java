package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.WorkTicketMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTicketMaterialRepository extends JpaRepository<WorkTicketMaterialEntity,String> {
    List<WorkTicketMaterialEntity> findByWorkTicketId(String workTicketId);

}
