package com.example.grabbs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tyre")
public class Tyre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable=false)
    private String takId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private double treadDepth;

    @Column(nullable = false)
    private String manufactureDate;

    @Column(nullable = false)
    private String purchaseDate;

    private double purchasePrice;

    private String position;

    private double odometer;

    private LocalDate commissioningDate;

    private LocalDate decommissioningDate;

    private String comments;

    @Column(nullable = false)
    private String state;

    @Column(nullable=false)
    private LocalDateTime createdDate;

    @Transient
    String file;

}
