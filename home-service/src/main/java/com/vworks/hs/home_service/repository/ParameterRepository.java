package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ParameterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<ParameterEntity, String> {
    Optional<ParameterEntity> findByCodeOrName(String code, String name);

    Page<ParameterEntity> findAll(Specification<ParameterEntity> spec, Pageable pageable);
}
