package com.vworks.wms.warehouse_service.service;

import com.vworks.wms.warehouse_service.entities.BannerEntity;
import com.vworks.wms.warehouse_service.entities.ContentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EditorService {
    // Banner APIs
    List<BannerEntity> getBanners();
    BannerEntity uploadBanner(MultipartFile file, String label, int position);
    void updateBanner(Integer id, String label, Integer position);
    void deleteBanner(Integer id);

    // Content APIs
    List<ContentEntity> getAllContents();
    ContentEntity getContent(Integer position);
    ContentEntity updateContent(Integer position, ContentEntity updatedContent);
    ContentEntity uploadContentImage(Integer position, MultipartFile file);
}
