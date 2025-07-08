package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.ExportRequestMaterialBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportRequestRepository extends JpaRepository<ExportRequestEntity,Integer> {
    List<ExportRequestEntity> findAllByStatusAndRequesterId(String status, Long requesterId);
    List<ExportRequestEntity> findAllByRequesterId(Long requesterId);

    List<ExportRequestEntity> findAllByStatus(String status);
}
