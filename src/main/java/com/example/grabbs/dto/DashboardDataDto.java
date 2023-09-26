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
public class DashboardDataDto {

    private Long numberOfTrucks;
    private Double truckIncreasePercentage;
    private Long numberOfTyres;
    private Double tyreIncreasePercentage;
    private Long numberOfUsers;
    private Double userIncreasePercentage;
    private List<Truck> decommissionedTruckBrands;
    private int[] seriesData;
    private String[] categories;
    private int[] seriesDataAnalytics;

    // Getters and setters...
}
