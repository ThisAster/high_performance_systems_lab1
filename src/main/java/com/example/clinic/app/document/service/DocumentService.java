package com.example.clinic.app.document.service;

import com.example.clinic.app.patient.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.clinic.app.document.dto.DocumentDto;
import com.example.clinic.app.document.entity.Document;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.document.mapper.DocumentMapper;
import com.example.clinic.app.document.repository.DocumentRepository;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final PatientService patientService;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Document createDocument(DocumentDto documentDto, Long patientId) {
        Patient patient = patientService.getPatientById(patientId);

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

    public Page<Document> getDocuments(Pageable page) {
        return documentRepository.findAll(page);
    }
}