package com.vworks.wms.warehouse_service.repository;

import com.vworks.wms.warehouse_service.entities.WarehouseDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WareHouseDetailRepository extends JpaRepository<WarehouseDetailEntity, String> {
    List<WarehouseDetailEntity> findAllByWarehouseCode(String whCode);

    Optional<WarehouseDetailEntity> findFirstByMaterialCode(String code);

    Optional<WarehouseDetailEntity> findAllByWarehouseCodeAndMaterialCode(String code, String materialCode);
}
