package com.vworks.wms.warehouse_service.controller;

import com.vworks.wms.warehouse_service.entities.editsEntity.BannerEntity;
import com.vworks.wms.warehouse_service.entities.editsEntity.ContentEntity;
import com.vworks.wms.warehouse_service.service.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/editor")
@RequiredArgsConstructor
public class EditorController {
    private final EditorService editorService;
    @GetMapping("/banners")
    public List<BannerEntity> getBanners() {
        return editorService.getBanners();
    }

    @PostMapping("/banners")
    public BannerEntity uploadBanner(@RequestParam("file") MultipartFile file,
                                     @RequestParam String label,
                                     @RequestParam int position) {
        return editorService.uploadBanner(file, label, position);
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<?> updateBanner(@PathVariable Integer id,
                                          @RequestParam(required = false) String label,
                                          @RequestParam(required = false) Integer position) {
        editorService.updateBanner(id, label, position);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable Integer id) {
        editorService.deleteBanner(id);
        return ResponseEntity.ok().build();
    }

    // === Content ===
    @GetMapping("/contents")
    public List<ContentEntity> getAllContents() {
        return editorService.getAllContents();
    }

    @GetMapping("/contents/{position}")
    public ContentEntity getContent(@PathVariable Integer position) {
        return editorService.getContent(position);
    }

    @PutMapping("/contents/{position}")
    public ContentEntity updateContent(@PathVariable Integer position,
                                       @RequestBody ContentEntity updatedContent) {
        return editorService.updateContent(position, updatedContent);
    }

    @PostMapping("/contents/{position}/upload-images")
    public ContentEntity uploadContent(
            @PathVariable Integer position,
            @RequestParam("title") String title,
            @RequestParam("body") String body,
            @RequestParam("type") String type,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("badge") String badgeJson, // {"text":"Nổi bật","color":"red"}
            @RequestParam("files") List<MultipartFile> files) {
        return editorService.uploadContentImages(position, title, body, type, date, badgeJson, files);
    }




}
