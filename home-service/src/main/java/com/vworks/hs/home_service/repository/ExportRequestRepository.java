package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.ExportRequestMaterialBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportRequestRepository extends JpaRepository<ExportRequestEntity,String> {
    List<ExportRequestEntity> findAllByStatusAndRequesterId(String status, String requesterId);
    List<ExportRequestEntity> findAllByRequesterId(String requesterId);

    List<ExportRequestEntity> findAllByStatus(String status);
}
