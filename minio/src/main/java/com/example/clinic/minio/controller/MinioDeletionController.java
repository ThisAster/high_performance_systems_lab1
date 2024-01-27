package com.example.clinic.minio.controller;

import com.example.clinic.minio.service.MinioService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class MinioDeletionController {

    private final MinioService minioService;
    private final String bucket = "filesdemo";

    public MinioDeletionController(MinioService minioService) {
        this.minioService = minioService;
    }

    @MessageMapping("/delete")
    @SendTo("/ws/topic/deleted")
    public String delete(String fileName) {
        try {
            minioService.deleteFile(bucket, fileName);
        } catch (Exception e) {
//            throw new RuntimeException("Delete failed" + e);
            return "Failed to delete " + fileName;
        }
        return "File " + fileName + " has been deleted at " + LocalDateTime.now();
    }
}
