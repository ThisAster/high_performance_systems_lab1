package com.example.clinic.app.document.mapper;

import org.springframework.stereotype.Component;

import com.example.clinic.app.document.dto.DocumentDto;
import com.example.clinic.app.document.entity.Document;

@Component
public class DocumentMapper {

    public DocumentDto entityToDocumentDto(Document document) {
        if (document == null) {
            return null;
        }
        return new DocumentDto(
            document.getId(),
            document.getType(),
            document.getDate(),
            document.getContent(),
            document.getStatus()
        );
    }

    public Document documentDtoToEntity(DocumentDto documentDto) {
        if (documentDto == null) {
            return null;
        }
        Document document = new Document();
        document.setId(documentDto.id());
        document.setType(documentDto.type());
        document.setDate(documentDto.date());
        document.setContent(documentDto.content());
        document.setStatus(documentDto.status());
        
        return document;
    }
}
