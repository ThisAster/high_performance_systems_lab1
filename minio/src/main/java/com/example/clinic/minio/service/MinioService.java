package com.example.clinic.minio.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    private final KafkaProducerMinioService kafkaProducerMinioService;

    public void uploadFile(@Value("${minio.bucket}") String bucket, String fileName,
                           InputStream fileStream,
                           String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .stream(fileStream, fileStream.available(), -1)
                            .contentType(contentType)
                            .build());
            String message = "Файл: " + fileName + " успешно загружен";
            kafkaProducerMinioService.sendMessage("minio-notifications", message);

        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO: " + e);
        }
    }

    public InputStream downloadFile(@Value("${minio.bucket}") String bucket, String objectName) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build());

            String message = "Файл: " + objectName + " успешно скачан";
            kafkaProducerMinioService.sendMessage("minio-notifications", message);

            return inputStream;
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file from MinIO", e);
        }
    }

    public void deleteFile(@Value("${minio.bucket}") String bucket, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build());
            String message = "Файл: " + objectName + " успешно удален";
            kafkaProducerMinioService.sendMessage("minio-notifications", message);

        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO: " + e);
        }
    }
}
