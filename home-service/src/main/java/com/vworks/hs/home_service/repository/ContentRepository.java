package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.editsEntity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {
    Optional<ContentEntity> findByPosition(Integer position);
}
