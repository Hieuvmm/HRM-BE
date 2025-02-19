package com.vworks.wms.common_lib.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadFileToMinio(MultipartFile file, String bucketName, String pathFile);
}
