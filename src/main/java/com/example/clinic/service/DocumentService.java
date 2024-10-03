package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DocumentDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.DocumentMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.repository.DocumentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PatientService patientService;
    private final DocumentMapper documentMapper;
    private final PatientMapper patientMapper;

    @Transactional
    public Optional<DocumentDto> createDocument(DocumentDto documentDto, Long patientId) {
        Document document = documentMapper.documentDtoToEntity(documentDto);
        PatientDto patienDto = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + patientId + " not found"));

        Patient patient = patientMapper.patientDtoToEntity(patienDto);
        document.setPatient(patient);
        Document savedDocument = documentRepository.save(document);
        return Optional.of(documentMapper.entityToDocumentDto(savedDocument));
    }

    @Transactional
    public Optional<DocumentDto> updateDocument(Long id, DocumentDto documentDto) {
        return documentRepository.findById(id)
                .map(document -> {
                    documentMapper.updateEntityFromDto(document, documentDto);
                    Document updatedDocument = documentRepository.save(document);
                    return documentMapper.entityToDocumentDto(updatedDocument);
                });
    }

    @Transactional
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    public List<DocumentDto> getAllDocuments() {
        return ((Collection<Document>) documentRepository.findAll()).stream()
                .map(documentMapper::entityToDocumentDto)
                .collect(Collectors.toList());
    }

    public Optional<DocumentDto> getDocumentById(Long id) {
        return documentRepository.findById(id)
                .map(documentMapper::entityToDocumentDto);
    }

    public List<DocumentDto> getByPatientId(Long patientId) {
        return documentRepository.findByPatientId(patientId).stream()
                .map(documentMapper::entityToDocumentDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getByStatus(String status) {
        return documentRepository.findByStatus(status).stream()
                .map(documentMapper::entityToDocumentDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getByType(String type) {
        return documentRepository.findByType(type).stream()
                .map(documentMapper::entityToDocumentDto)
                .collect(Collectors.toList());
    }
}