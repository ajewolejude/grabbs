package com.example.grabbs.service;

import com.example.grabbs.dto.DashboardDataDto;
import com.example.grabbs.repository.TruckRepository;
import com.example.grabbs.repository.TyreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TruckService truckService;

    @Autowired
    private TyreService tyreService;

    public DashboardDataDto getDashboardData() {
        DashboardDataDto dashboardData = new DashboardDataDto();

        // Fetch data from repositories
        Long numberOfTrucks = truckService.getAllTrucks().stream().count();
        Long numberOfTyres = tyreService.getAllTyres().stream().count();
        //int numberOfUsers = userRepository.countAllUsers();
       // List<String> decommissionedBrands = truckRepository.findDecommissionedBrands();

        // Populate the DTO
        dashboardData.setNumberOfTrucks(numberOfTrucks);
        dashboardData.setNumberOfTyres(numberOfTyres);
        //dashboardData.setNumberOfUsers(numberOfUsers);
        //dashboardData.setDecommissionedTruckBrands(decommissionedBrands);

        // Calculate and set percentage increase (you need to implement this)
        dashboardData.setTruckIncreasePercentage(calculateTruckIncreasePercentage());
        dashboardData.setTyreIncreasePercentage(calculateTyreIncreasePercentage());
        //dashboardData.setUserIncreasePercentage(calculateUserIncreasePercentage());

        return dashboardData;
    }

    public double calculateTruckIncreasePercentage() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);

        int trucksLastMonth = truckService.countTrucksCreatedBetween(oneMonthAgo, today);
        int trucksThisMonth = truckService.countTrucksCreatedBetween(today.withDayOfMonth(1), today);

        if (trucksLastMonth == 0) {
            return 100.0; // If no trucks last month, consider the increase as 100%
        }

        double increasePercentage = ((double) (trucksThisMonth - trucksLastMonth) / trucksLastMonth) * 100.0;
        return increasePercentage;
    }

    public double calculateTyreIncreasePercentage() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);

        int tyresLastMonth = tyreService.countTyresCreatedBetween(oneMonthAgo, today);
        int tyresThisMonth = tyreService.countTyresCreatedBetween(today.withDayOfMonth(1), today);

        if (tyresLastMonth == 0) {
            return 100.0; // If no tyres last month, consider the increase as 100%
        }

        double increasePercentage = ((double) (tyresThisMonth - tyresLastMonth) / tyresLastMonth) * 100.0;
        return increasePercentage;
    }

}
