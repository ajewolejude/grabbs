package com.example.grabbs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tyre_id", nullable = false)
    private Tyre tyre;

    @Column(nullable = false)
    private String reasonForCommissioning;

    private Long decommissioningRequestId;

    @Column(nullable = false)
    private LocalDate dateOfCommissioning;

    private double mileage;

    private String conditionReport;

    @Column(nullable = false)
    private String responsibleOfficer;

    @Column(nullable = false)
    private String approvalStatus;

    private String approvalComments;

    @Column(nullable=false)
    private LocalDateTime createdDate;
}
