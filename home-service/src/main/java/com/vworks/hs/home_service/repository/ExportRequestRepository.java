package com.vworks.hs.home_service.repository;

import com.vworks.hs.home_service.entities.ExportRequestEntity;
import com.vworks.hs.home_service.models.request.exportRequest.ExportRequestMaterialBody;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportRequestRepository extends JpaRepository<ExportRequestEntity,String> {
}
