package com.example.clinic.minio.controller;

import com.example.clinic.minio.dto.FileResponse;
import com.example.clinic.minio.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class MinioController {

    @Autowired
    private MinioService minioService;

    private final String bucket = "filesdemo";

    @PostMapping("/upload")
    public String uploadFile(@RequestBody() MultipartFile file) {
        try (InputStream fileStream = file.getInputStream()) {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            minioService.uploadFile(bucket, fileName, fileStream, contentType);
            return "File successfully uploaded";
        } catch (Exception e) {
            throw new RuntimeException("Upload failed", e);
        }
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileResponse> downloadFile(@PathVariable String fileName) {
        try (InputStream fileStream = minioService.downloadFile(bucket, fileName)) {

            String content = new BufferedReader(new InputStreamReader(fileStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            FileResponse response = new FileResponse(fileName, content);

        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
        try {
            minioService.deleteFile(bucket, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Delete failed" + e);
        }
    }


}
