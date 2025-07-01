package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.WarehouseDetailEntity;
import com.vworks.hs.home_service.models.DetailWareHouseModel;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WareHouseDetailRepository extends JpaRepository<WarehouseDetailEntity, String> {
    List<WarehouseDetailEntity> findAllByWarehouseCode(String whCode);

    Optional<WarehouseDetailEntity> findFirstByMaterialCode(String code);

    Optional<WarehouseDetailEntity> findAllByWarehouseCodeAndMaterialCode(String code, String materialCode);



    @Query("SELECT new com.vworks.hs.home_service.models.DetailWareHouseModel(" +
            "dm.code, " +
            "m.name, " +
            "dm.name, " +
            "dm.measureKeyword, " +
            "wd.quantity, " +
            "dm.listPrice, " +
            "'USD', " +
            "dm.sellPrice, " +
            "o.name, " +
            "dm.minInventory, " +
            "dm.status" +
            ") " +
            "FROM WarehouseDetailEntity wd " +
            "JOIN DetailMaterialsEntity dm ON wd.materialCode = dm.code " +
            "LEFT JOIN MaterialsEntity m ON dm.materialTypeCode = m.code " +
            "LEFT JOIN ObjectEntity o ON dm.providerCode = o.code AND o.type = 'PROVIDER' " +
            "WHERE wd.warehouseCode = :warehouseCode " +
            "ORDER BY dm.name")
    List<DetailWareHouseModel> getDetailByWarehouseCode(@Param("warehouseCode") String warehouseCode);

}
