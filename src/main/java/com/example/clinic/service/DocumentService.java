package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DocumentDto;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.DocumentMapper;
import com.example.clinic.repository.DocumentRepository;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PatientRepository patientRepository;
    private final DocumentMapper documentMapper;

    @Transactional
    public Optional<Document> createDocument(DocumentDto documentDto, Long patientId) {
        Document document = documentMapper.documentDtoToEntity(documentDto);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));

        document.setPatient(patient);
        Document savedDocument = documentRepository.save(document);
        return Optional.of(savedDocument);
    }

    @Transactional
    public Optional<Document> updateDocument(Long id, DocumentDto documentDto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document with id " + id + " not found"));
        document.setType(documentDto.type());
        document.setDate(documentDto.date());
        document.setContent(documentDto.content());
        document.setStatus(documentDto.status());

        Document updatedDocument = documentRepository.save(document);
        return Optional.of(updatedDocument);
    }

    @Transactional
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    public List<Document> getAllDocuments() {
        return ((Collection<Document>) documentRepository.findAll()).stream()
                .collect(Collectors.toList());
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public List<Document> getByPatientId(Long patientId) {
        return documentRepository.findByPatientId(patientId).stream()
                .collect(Collectors.toList());
    }

    public List<Document> getByStatus(String status) {
        return documentRepository.findByStatus(status).stream()
                .collect(Collectors.toList());
    }

    public List<Document> getByType(String type) {
        return documentRepository.findByType(type).stream()
                .collect(Collectors.toList());
    }
}