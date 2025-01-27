package com.example.clinic.analysis.controller;

import com.example.clinic.analysis.dto.AnalysisCreationDto;
import com.example.clinic.analysis.dto.AnalysisDto;
import com.example.clinic.analysis.exception.EntityNotFoundException;
import com.example.clinic.analysis.mapper.AnalysisMapper;
import com.example.clinic.analysis.model.PageArgument;
import com.example.clinic.analysis.service.AnalysisService;
import com.example.clinic.analysis.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping
    public Mono<ResponseEntity<AnalysisDto>> createAnalysis(@Valid @RequestBody AnalysisCreationDto analysisDto,
                                                            @RequestParam Long patientId) {

        return analysisService.createAnalysis(analysisDto, patientId)
                .map(analysis -> ResponseEntity
                        .created(URI.create("/api/analyses/" + analysis.id()))
                        .body(analysis));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AnalysisDto>> updateAnalysis(@PathVariable Long id, @Valid @RequestBody AnalysisCreationDto analysisDto) {
        return analysisService.updateAnalysis(id, analysisDto).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteAnalysis(@PathVariable Long id) {
        try {
            analysisService.deleteAnalysis(id);
        } catch (EntityNotFoundException e) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Analysis with id " + id + " not found."));
        }
        return Mono.just(ResponseEntity.ok("Analysis with id " + id + " successfully deleted."));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AnalysisDto>> getAnalysisById(@PathVariable Long id) {
        return analysisService.getAnalysisById(id).map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<List<AnalysisDto>>> getAnalyses(PageArgument page) {
        return analysisService.getAnalyses(page.getPageRequest())
                .map(pg -> ResponseEntity.ok()
                        .headers(HeaderUtils.createPaginationHeaders(pg))
                        .body(pg.getContent()));
    }
}
