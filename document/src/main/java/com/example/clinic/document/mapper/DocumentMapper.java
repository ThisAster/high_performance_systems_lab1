package com.example.clinic.document.mapper;

import com.example.clinic.document.dto.DocumentCreationDTO;
import com.example.clinic.document.dto.DocumentDto;
import com.example.clinic.document.entity.Document;
import org.springframework.stereotype.Component;

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

    public Document documentDtoToEntity(DocumentCreationDTO documentDto) {
        if (documentDto == null) {
            return null;
        }
        Document document = new Document();
        document.setType(documentDto.type());
        document.setDate(documentDto.date());
        document.setContent(documentDto.content());
        document.setStatus(documentDto.status());

        return document;
    }
}
