package com.example.clinic.service;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DocumentDto;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.DocumentMapper;
import com.example.clinic.repository.DocumentRepository;
import com.example.clinic.repository.PatientRepository;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final PatientRepository patientRepository;
    private final DocumentMapper documentMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Document createDocument(DocumentDto documentDto, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " not found"));

        Document document = documentMapper.documentDtoToEntity(documentDto);

        document.setPatient(patient);

        return documentRepository.save(document);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Document updateDocument(Long id, DocumentDto documentDto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));
                
        document.setType(documentDto.type());
        document.setDate(documentDto.date());
        document.setContent(documentDto.content());
        document.setStatus(documentDto.status());

        return documentRepository.save(document);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document with id " + id + " not found");
        }
        documentRepository.deleteById(id);
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document with id " + id + " not found"));
    } 
}