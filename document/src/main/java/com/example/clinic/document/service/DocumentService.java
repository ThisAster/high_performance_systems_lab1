package com.example.clinic.document.service;

import com.example.clinic.document.dto.DocumentCreationDTO;
import com.example.clinic.document.entity.Document;
import com.example.clinic.document.exception.EntityNotFoundException;
import com.example.clinic.document.mapper.DocumentMapper;
import com.example.clinic.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Document createDocument(DocumentCreationDTO documentDto, Long patientId) {
        Document document = documentMapper.documentDtoToEntity(documentDto);

        document.setPatientId(patientId);

        return documentRepository.save(document);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Document updateDocument(Long id, DocumentCreationDTO documentDto) {
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