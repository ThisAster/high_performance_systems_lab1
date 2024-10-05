package com.example.clinic.service;

import java.util.Optional;

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
}