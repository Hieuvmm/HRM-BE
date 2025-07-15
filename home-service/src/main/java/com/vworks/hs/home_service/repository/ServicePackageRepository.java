package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ServicePackages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicePackageRepository extends JpaRepository<ServicePackages, Long> {
    List<ServicePackages> findByCategoryId(Long categoryId);
    Optional<ServicePackages> findByCode(String code);
}
