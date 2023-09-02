package com.example.grabbs.controller.rest;

import com.example.grabbs.dto.DashboardDataDto;
import com.example.grabbs.service.DashboardService;
import com.example.grabbs.service.TruckService;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard-rest")
public class DashboardRestController {

    @Autowired
    private DashboardService dashboardService;


    @GetMapping("/data")
    public ResponseEntity<DashboardDataDto> getDashboardData() {
        DashboardDataDto dashboardData = new DashboardDataDto();
        
        // Fetch and set the required data using your service methods
        dashboardData.setNumberOfTrucks(dashboardService.getDashboardData().getNumberOfTrucks());
        dashboardData.setTruckIncreasePercentage(dashboardService.getDashboardData().getTruckIncreasePercentage());
        dashboardData.setNumberOfTyres(dashboardService.getDashboardData().getNumberOfTyres());
        dashboardData.setTyreIncreasePercentage(dashboardService.getDashboardData().getTyreIncreasePercentage());
        // Fetch other data...

        return ResponseEntity.ok(dashboardData);
    }
}
