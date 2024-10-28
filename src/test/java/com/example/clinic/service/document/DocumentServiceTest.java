package com.example.clinic.service.document;


import com.example.clinic.app.analysis.dto.AnalysisCreationDto;
import com.example.clinic.app.analysis.entity.Analysis;
import com.example.clinic.app.analysis.service.AnalysisService;
import com.example.clinic.app.document.dto.DocumentCreationDTO;
import com.example.clinic.app.document.entity.Document;
import com.example.clinic.app.document.service.DocumentService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    void createDocumentTest(){
        DocumentCreationDTO documentCreationDTO = new DocumentCreationDTO(
                "Passport",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "5629-125414",
                "ok"
        );

        Document createdDocument = documentService.createDocument(documentCreationDTO, 1L);
        Document retrievedDocument = documentService.getDocumentById(createdDocument.getId());

        assertNotNull(retrievedDocument);
    }

    @Test
    void getDocumentByIdTest(){
        DocumentCreationDTO documentCreationDTO = new DocumentCreationDTO(
                "Passport",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "5629-125414",
                "ok"
        );

        Document createdDocument = documentService.createDocument(documentCreationDTO, 1L);
        Document retrievedDocument = documentService.getDocumentById(createdDocument.getId());

        assertNotNull(retrievedDocument);
        assertEquals(retrievedDocument.getId(), createdDocument.getId());
        assertEquals(retrievedDocument.getType(), createdDocument.getType());
        assertEquals(retrievedDocument.getDate(), createdDocument.getDate());
        assertEquals(retrievedDocument.getContent(), createdDocument.getContent());
        assertEquals(retrievedDocument.getStatus(), createdDocument.getStatus());
    }

    @Test
    void deleteDocumentTest(){
        DocumentCreationDTO documentCreationDTO = new DocumentCreationDTO(
                "Passport",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "5629-125414",
                "ok"
        );

        Document createdDocument = documentService.createDocument(documentCreationDTO, 1L);
        documentService.deleteDocument(createdDocument.getId());
        assertThrows(EntityNotFoundException.class,
                () -> documentService.getDocumentById(createdDocument.getId()));
    }

    @Test
    void updateDocumentTest(){
        DocumentCreationDTO oldDocumentCreationDTO = new DocumentCreationDTO(
                "Passport",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "5629-125414",
                "ok"
        );

        DocumentCreationDTO newDocumentCreationDTO = new DocumentCreationDTO(
                "Passport",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "2345-860396",
                "ok"
        );

        Document createdDocument = documentService.createDocument(oldDocumentCreationDTO, 1L);
        Document updateDocument = documentService.updateDocument(createdDocument.getId(), newDocumentCreationDTO);

        assertNotNull(updateDocument);
        assertEquals(newDocumentCreationDTO.type(), updateDocument.getType());
        assertEquals(newDocumentCreationDTO.date(), updateDocument.getDate());
        assertEquals(newDocumentCreationDTO.content(), updateDocument.getContent());
        assertEquals(newDocumentCreationDTO.status(), updateDocument.getStatus());
    }
}