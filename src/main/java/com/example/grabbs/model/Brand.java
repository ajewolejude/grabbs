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
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String brandName;


    private String comments;

    @Column(nullable = false)
    private String responsibleOfficer;

    @Column(nullable=false)
    private String state;

    @Column(nullable=false)
    private LocalDateTime createdDate;
}
