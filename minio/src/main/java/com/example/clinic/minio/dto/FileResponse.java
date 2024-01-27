package com.example.clinic.minio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {
    private String fileName;
    private String content;

    public FileResponse(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

}
