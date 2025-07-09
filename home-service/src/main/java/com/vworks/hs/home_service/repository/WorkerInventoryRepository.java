package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.WorkerInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerInventoryRepository extends JpaRepository<WorkerInventoryEntity, String> {
    Optional<WorkerInventoryEntity> findByUserIdAndMaterialId(String userId, String materialId);
}
