package com.example.grabbs.dto;

import com.example.grabbs.model.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCommissionDto {

    private Long numberOfSubmitted;
    private Long numberOfApproved;
    private Long numberOfCancelled;
    private Long numberOfCompleted;

    // Getters and setters...
}
