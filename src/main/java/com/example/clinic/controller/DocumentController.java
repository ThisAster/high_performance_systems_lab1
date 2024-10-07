package com.example.clinic.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic.dto.DocumentDto;
import com.example.clinic.entity.Document;
import com.example.clinic.mapper.DocumentMapper;
import com.example.clinic.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(@RequestBody DocumentDto documentDto, @RequestParam Long patientId) {
        Document document = documentService.createDocument(documentDto, patientId);
        DocumentDto createdDocumentDto = documentMapper.entityToDocumentDto(document);
        return ResponseEntity.created(URI.create("/api/documents/" + createdDocumentDto.id())).body(createdDocumentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long id, @RequestBody DocumentDto documentDto) {
        Document updatedDocument = documentService.updateDocument(id, documentDto);
        DocumentDto updatedDocumentDto = documentMapper.entityToDocumentDto(updatedDocument);
        return ResponseEntity.ok(updatedDocumentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        DocumentDto documentDto = documentMapper.entityToDocumentDto(document);
        return ResponseEntity.ok(documentDto);
    }
}
