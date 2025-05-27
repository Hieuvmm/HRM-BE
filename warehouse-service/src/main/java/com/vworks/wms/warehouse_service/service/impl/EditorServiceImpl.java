package com.vworks.wms.warehouse_service.service.impl;

import com.vworks.wms.common_lib.config.MinioConfigProperties;
import com.vworks.wms.common_lib.service.MinioService;
import com.vworks.wms.warehouse_service.entities.editsEntity.BannerEntity;
import com.vworks.wms.warehouse_service.entities.editsEntity.ContentEntity;
import com.vworks.wms.warehouse_service.repository.BannerRepository;
import com.vworks.wms.warehouse_service.repository.ContentRepository;
import com.vworks.wms.warehouse_service.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EditorServiceImpl implements EditorService {

    private final BannerRepository bannerRepository;
    private final ContentRepository contentRepository;
    private final MinioService minioService;
    private final MinioConfigProperties minioConfig;

    // === Banner ===
    @Override
    public List<BannerEntity> getBanners() {
        return bannerRepository.findAllByOrderByPositionAsc();
    }

    @Override
    public BannerEntity uploadBanner(MultipartFile file, String label, int position) {
        try {
            String folder = minioConfig.getMaterialImageFolderStorage();
            String bucket = minioConfig.getBucketName();
            String path = String.format("%s/banner/%d_%s", folder, System.currentTimeMillis(), file.getOriginalFilename());
            String imageUrl = minioService.uploadFileToMinio(file, bucket, path);

            BannerEntity banner = BannerEntity.builder()
                    .imageUrl(imageUrl)
                    .label(label)
                    .position(position)
                    .build();

            return bannerRepository.save(banner);
        } catch (Exception e) {
            throw new RuntimeException("Upload banner failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateBanner(Integer id, String label, Integer position) {
        BannerEntity banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        if (label != null) banner.setLabel(label);
        if (position != null) banner.setPosition(position);

        bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(Integer id) {
        BannerEntity banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        bannerRepository.delete(banner);
    }

    // === Content ===
    @Override
    public List<ContentEntity> getAllContents() {
        return contentRepository.findAll();
    }

    @Override
    public ContentEntity getContent(Integer position) {
        return contentRepository.findByPosition(position)
                .orElseThrow(() -> new RuntimeException("Content not found"));
    }

    @Override
    public ContentEntity updateContent(Integer position, ContentEntity updatedContent) {
        ContentEntity content = contentRepository.findByPosition(position)
                .orElse(ContentEntity.builder().position(position).build());

        content.setTitle(updatedContent.getTitle());
        content.setBody(updatedContent.getBody());
        content.setImageUrls(updatedContent.getImageUrls());

        return contentRepository.save(content);
    }

    @Override
    public ContentEntity uploadContentImages(Integer position, String title, String body,
                                             String type, LocalDate date,
                                             String badgeJson, List<MultipartFile> files) {
        ContentEntity content = contentRepository.findByPosition(position)
                .orElse(ContentEntity.builder()
                        .position(position)
                        .title(title != null ? title : "")
                        .body(body != null ? body : " ")
                        .imageUrls(new ArrayList<>())
                        .build());

        content.setTitle(title);
        content.setBody(body);
        content.setType(type);
        content.setDate(date);

        content.setBadge(badgeJson);

        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String folder = minioConfig.getMaterialImageFolderStorage();
                String bucket = minioConfig.getBucketName();
                String path = String.format("%s/content/%d/%d_%s", folder, position, System.currentTimeMillis(), file.getOriginalFilename());
                String imageUrl = minioService.uploadFileToMinio(file, bucket, path);
                uploadedUrls.add(imageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Upload content image failed: " + e.getMessage(), e);
            }
        }

        content.setImageUrls(uploadedUrls);

        return contentRepository.save(content);
    }




}
