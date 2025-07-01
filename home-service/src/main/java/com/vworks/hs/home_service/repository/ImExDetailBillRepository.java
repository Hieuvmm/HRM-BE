package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ImExDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImExDetailBillRepository extends JpaRepository<ImExDetailEntity, String> {
    List<ImExDetailEntity> findAllByBillCodeAndStatus(String billCode, String status);

    List<ImExDetailEntity> findAllByBillCode(String billCode);

    void deleteAllByBillCode(String billCode);
}
