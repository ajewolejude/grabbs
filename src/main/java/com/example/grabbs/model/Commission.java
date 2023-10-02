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

    @ManyToOne
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    @Column(nullable = false)
    private String reasonForCommissioning;

    @Column(nullable = false)
    private String dateOfCommissioning;

    private double odometer;

    private String conditionReport;

    @Column(nullable = false)
    private String responsibleOfficer;

    @Column(nullable = false)
    private String state;

    private String initialComments;

    private String approvalComments;

    private String completionComments;

    @Column(nullable=false)
    private LocalDateTime createdDate;

    private LocalDateTime approvalDate;

    private LocalDateTime completionDate;

    private LocalDateTime cancellationDate;

    private LocalDateTime rejectionDate;
}
