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
@Table(name="truck")
public class Truck
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String truckType;

    @Column(nullable=false)
    private String licensePlateNumber;

    @Column(nullable=false)
    private String vin;

    @Column(nullable=false)
    private String takId;

    @Column(nullable=false)
    private String model;

    @Column(nullable=false)
    private String brand;

    @Column(nullable=false)
    private String manufactureDate;

    @Column(nullable=false)
    private String purchaseDate;

    @Column(nullable=false)
    private double odometer;

    @Column(nullable=false)
    private String state;

    private String comments;

    @Column(nullable=false)
    private LocalDateTime createdDate;

}