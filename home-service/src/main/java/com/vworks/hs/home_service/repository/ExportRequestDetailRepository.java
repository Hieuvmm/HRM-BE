package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ExportRequestDetailEntity;
import com.vworks.hs.home_service.models.request.exportRequest.ExportRequestMaterialBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportRequestDetailRepository extends JpaRepository<ExportRequestDetailEntity,Integer> {
    List<ExportRequestDetailEntity> findByRequestId(Integer requestId);
    void deleteAllByRequestId(Integer requestId);
}
