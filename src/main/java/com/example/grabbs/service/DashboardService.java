package com.example.grabbs.service;

import com.example.grabbs.dto.DashboardCommissionDto;
import com.example.grabbs.dto.DashboardDataDto;
import com.example.grabbs.model.Decommission;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.repository.TruckRepository;
import com.example.grabbs.repository.TyreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.runtime.internal.Conversions.intValue;

@Service
public class DashboardService {

    @Autowired
    private TruckService truckService;

    @Autowired
    private TyreService tyreService;

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private DecommissionService decommissionService;

    @PersistenceContext
    private EntityManager entityManager;

    public DashboardDataDto getDashboardData() {
        DashboardDataDto dashboardData = new DashboardDataDto();

        // Fetch data from repositories
        Long numberOfTrucks = truckService.getAllTrucks().stream().count();
        Long numberOfTyres = tyreService.getAllTyres().stream().count();

        dashboardData.setNumberOfTrucks(numberOfTrucks);
        dashboardData.setNumberOfTyres(numberOfTyres);

        List<Object[]>  list = getTop4BrandsWithHighestDecommissionedState();
        int[] seriesData = new int[list.size()];
        String[] categories = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Object[] objects =  list.get(i);
            categories[i] = (String) objects[0];
            seriesData[i] = intValue(objects[1]) ;
        }

        List<Object[]>  list2 = getTop4Brands();
        Map<String, Integer> formattedResult = returnFormattedResult(list2);
        int[] seriesDataAnalytics = new int[4];
        seriesDataAnalytics[0] = formattedResult.get("AVAILABLE");
        seriesDataAnalytics[1] = formattedResult.get("COMMISSIONED");
        seriesDataAnalytics[2] = formattedResult.get("DECOMMISSIONED");
        seriesDataAnalytics[3] = formattedResult.get("UNDER MAINTENANCE");



//        List<Object[]>  list3 = getTop4BrandsWithLowestDecommissionedState();
//        int[] seriesData = new int[4];
//        String[] categories = new String[4];
//        for (int i = 0; i < list3.size(); i++) {
//            Object[] objects =  list3.get(i);
//            categories[i] = (String) objects[0];
//            seriesData[i] = intValue(objects[1]) ;
//        }


        dashboardData.setSeriesData(seriesData);
        dashboardData.setCategories(categories);
        dashboardData.setSeriesDataAnalytics(seriesDataAnalytics);
        dashboardData.setTruckIncreasePercentage(calculateTruckIncreasePercentage());
        dashboardData.setTyreIncreasePercentage(calculateTyreIncreasePercentage());

        return dashboardData;
    }

    public List<Object[]> getTop4BrandsWithHighestDecommissionedState() {
        // Write a native SQL query to get the top 4 brands with the highest decommissioned state
        String sqlQuery = "SELECT brand, COUNT(*) AS count " +
                "FROM tyre " +
                "WHERE state = 'DECOMMISSIONED' " +
                "GROUP BY brand " +
                "ORDER BY count DESC " +
                "LIMIT 4";

        Query query = entityManager.createNativeQuery(sqlQuery);
        return query.getResultList();
    }

    public List<Object[]> getTop4BrandsWithLowestDecommissionedState() {
        // Write a native SQL query to get the top 4 brands with the highest decommissioned state
        String sqlQuery = "SELECT brand, COUNT(*) AS count " +
                "FROM tyre " +
                "WHERE state = 'DECOMMISSIONED' " +
                "GROUP BY brand " +
                "ORDER BY count ASC " +
                "LIMIT 4";

        Query query = entityManager.createNativeQuery(sqlQuery);
        return query.getResultList();
    }
    public List<Object[]> getTop4Brands() {
        // Write a native SQL query to get the top 4 brands with the highest decommissioned state
        String sqlQuery = "SELECT state, COUNT(*) AS count " +
                "FROM tyre " +
                "WHERE state IN ('AVAILABLE', 'COMMISSIONED', 'DECOMMISSIONED', 'UNDER MAINTENANCE') " +
                "GROUP BY state " +
                "ORDER BY count DESC; ";

        Query query = entityManager.createNativeQuery(sqlQuery);
        return query.getResultList();
    }

    public Map<String, Integer> returnFormattedResult(List<Object[]> resultFromDatabase){
        // Simulated result from the database query

        // Create a map to store the formatted result
        Map<String, Integer> formattedResult = new HashMap<>();

        // Initialize the formatted result with 0 counts for each state
        formattedResult.put("AVAILABLE", 0);
        formattedResult.put("COMMISSIONED", 0);
        formattedResult.put("DECOMMISSIONED", 0);
        formattedResult.put("UNDER MAINTENANCE", 0);

        // Populate the counts from the database result
        for (Object[] row : resultFromDatabase) {
            String state = (String) row[0];
            int count = ((Number) row[1]).intValue(); // Convert to integer
            formattedResult.put(state, count);
        }

        return formattedResult;

    }


    public DashboardCommissionDto getDashboardCommissionData() {
        DashboardCommissionDto dashboardCommissionDto = new DashboardCommissionDto();

        // Fetch data from repositories
        Long numberOfCompleted = commissionService.getByState("COMPLETED").stream().count();
        Long numberOfSubmitted = commissionService.getByState("SUBMITTED").stream().count();
        Long numberOfApproved = commissionService.getByState("APPROVED").stream().count();
        Long numberOfCancelled = commissionService.getByState("CANCELLED").stream().count();


        // Populate the DTO
        dashboardCommissionDto.setNumberOfApproved(numberOfApproved);
        dashboardCommissionDto.setNumberOfCompleted(numberOfCompleted);
        dashboardCommissionDto.setNumberOfSubmitted(numberOfSubmitted);
        dashboardCommissionDto.setNumberOfCancelled(numberOfCancelled);


        return dashboardCommissionDto;
    }


    public DashboardCommissionDto getDashboardDecommissionData() {
        DashboardCommissionDto dashboardDecommissionDto = new DashboardCommissionDto();

        // Fetch data from repositories
        Long numberOfCompleted = decommissionService.getByState("COMPLETED").stream().count();
        Long numberOfSubmitted = decommissionService.getByState("SUBMITTED").stream().count();
        Long numberOfApproved = decommissionService.getByState("APPROVED").stream().count();
        Long numberOfCancelled = decommissionService.getByState("CANCELLED").stream().count();


        // Populate the DTO
        dashboardDecommissionDto.setNumberOfApproved(numberOfApproved);
        dashboardDecommissionDto.setNumberOfCompleted(numberOfCompleted);
        dashboardDecommissionDto.setNumberOfSubmitted(numberOfSubmitted);
        dashboardDecommissionDto.setNumberOfCancelled(numberOfCancelled);


        return dashboardDecommissionDto;
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
