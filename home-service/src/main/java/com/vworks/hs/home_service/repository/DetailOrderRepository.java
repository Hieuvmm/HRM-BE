package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.DetailOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrderEntity, String> {

    List<DetailOrderEntity> findAllByOrderCode(String orderCode);
}
