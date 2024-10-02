package com.example.clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.DocumentDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.DocumentRepository;

import jakarta.transaction.Transactional;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private ModelMapper documentMapper;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Autowired
    public void setDocumentMapper(ModelMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Transactional
    public DocumentDto createDocument(DocumentDto documentDto, Patient patientDto, Doctor doctorDto) {
        Document document = documentMapper.map(documentDto, Document.class);
        Patient patient = documentMapper.map(patientDto, Patient.class);
        Doctor doctor = documentMapper.map(doctorDto, Doctor.class);
        document.setPatient(patient);
        document.setDoctor(doctor);
        Document savedDocument = documentRepository.save(document);
        return documentMapper.map(savedDocument, DocumentDto.class);
    }

    public List<DocumentDto> getAllDocuments() {
        return ((List<Document>) documentRepository.findAll()).stream()
                .map(document -> documentMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    public DocumentDto getDocumentById(Long id) {
        Optional<Document> documentOpt = documentRepository.findById(id);
        if (documentOpt.isEmpty()) {
            throw new IllegalArgumentException("Document with id " + id + " not found");
        }
        return documentMapper.map(documentOpt.get(), DocumentDto.class);
    }

    @Transactional
    public DocumentDto updateDocument(Long id, DocumentDto documentDto) {
        Optional<Document> documentOpt = documentRepository.findById(id);
        if (documentOpt.isEmpty()) {
            throw new IllegalArgumentException("Document with id " + id + " not found");
        }
        Document document = documentOpt.get();
        document.setType(documentDto.type());
        document.setDate(documentDto.date());
        document.setContent(documentDto.content());
        document.setStatus(documentDto.status());
        Document updatedDocument = documentRepository.save(document);
        return documentMapper.map(updatedDocument, DocumentDto.class);
    }

    @Transactional
    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new IllegalArgumentException("Document with id " + id + " not found");
        }
        documentRepository.deleteById(id);
    }

    public List<DocumentDto> findByPatientId(Long patientId) {
        return documentRepository.findByPatientId(patientId).stream()
                .map(document -> documentMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    public List<DocumentDto> findByDoctorId(Long doctorId) {
        return documentRepository.findByDoctorId(doctorId).stream()
                .map(document -> documentMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    public List<DocumentDto> findByStatus(String status) {
        return documentRepository.findByStatus(status).stream()
                .map(document -> documentMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    public List<DocumentDto> findByType(String type) {
        return documentRepository.findByType(type).stream()
                .map(document -> documentMapper.map(document, DocumentDto.class))
                .collect(Collectors.toList());
    }
}
