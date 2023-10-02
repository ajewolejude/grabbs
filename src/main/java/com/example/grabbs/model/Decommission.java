package com.example.grabbs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "decommission")
public class Decommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tyre_id", nullable = false)
    private Tyre tyre;

    @OneToOne
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    @Column(nullable = false)
    private String reasonForDecommissioning;

    @Column(nullable = false)
    private String dateOfDecommissioning;

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
