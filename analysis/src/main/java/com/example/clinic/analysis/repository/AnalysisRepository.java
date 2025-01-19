package com.example.clinic.analysis.repository;

import com.example.clinic.analysis.entity.Analysis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thisaster
 */
@Repository
public interface AnalysisRepository extends
        PagingAndSortingRepository<Analysis, Long>,
        CrudRepository<Analysis, Long>
{}