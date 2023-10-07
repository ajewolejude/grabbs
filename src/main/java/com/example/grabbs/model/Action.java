package com.example.grabbs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String odometerBeforeAction;

    @Column(nullable = false)
    private double expectedLifeSpanIncreaseOdometer;

    @Column(nullable = false)
    private double actualPSI;

    @Column(nullable = false)
    private double recPSI;

    @ManyToOne
    @JoinColumn(name = "tyre_id")
    @JsonIgnore
    private Tyre tyre;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    @JsonIgnore
    private Truck truck;

    private String comments;

    private String responsibleOfficer;

    @Column(nullable=false)
    private LocalDateTime createdDate;
}
