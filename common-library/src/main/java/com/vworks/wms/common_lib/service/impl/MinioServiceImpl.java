package com.vworks.wms.common_lib.service.impl;

import com.vworks.wms.common_lib.service.MinioService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;

    @Override
    public String uploadFileToMinio(MultipartFile file, String bucketName, String pathFile) {
        try {
            // Tạo bucket nếu chưa tồn tại
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(pathFile)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            // Lưu file lên MinIO
            minioClient.putObject(objectArgs);

            return "/" + bucketName + "/" + pathFile;

        } catch (Exception e) {
            log.error("{} UploadFileToMinio exception: {}", getClass().getSimpleName(), e);
            return null;
        }
    }
}
