package com.example.clinic.analysis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "analyses")
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    @Id
    private Long id;

    @Column("analysis_type")
    private String type;

    @Column("sample_date")
    private LocalDate sampleDate;

    @Column("result")
    private String result;

    @Column("status")
    private String status;

    @Column("patient_id")
    private Long patient;
}
