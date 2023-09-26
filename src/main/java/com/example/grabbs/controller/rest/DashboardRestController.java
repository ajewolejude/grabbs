package com.example.grabbs.controller.rest;

import com.example.grabbs.dto.DashboardCommissionDto;
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
        DashboardDataDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }


    @GetMapping("/commission")
    public ResponseEntity<DashboardCommissionDto> getDashboardCommissionData() {
        DashboardCommissionDto dashboardCommissionDto = dashboardService.getDashboardCommissionData();
        return ResponseEntity.ok(dashboardCommissionDto);
    }

    @GetMapping("/decommission")
    public ResponseEntity<DashboardCommissionDto> getDashboardDecommissionData() {
        DashboardCommissionDto dashboardDecommissionDto = dashboardService.getDashboardDecommissionData();
        return ResponseEntity.ok(dashboardDecommissionDto);
    }
}
