package com.example.grabbs.controller;

import com.example.grabbs.model.*;
import com.example.grabbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private DecommissionService decommissionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TyreService tyreService;

    @Autowired
    private TruckService truckService;


    @GetMapping("/")
    public String index(Model model) {
        //if(!userService.isUserAuthenticated()) return "login";
        List<Commission> commissions = commissionService.getAllCommissions();
        model.addAttribute("commissions", commissions);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Tyre");
        model.addAttribute("item", "Commissions");
        model.addAttribute("subitem", "Dashboard");
        return "commission/index";
    }

    @GetMapping("/add")
    public String createTyreForm(Model model) {
        List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
        List<Truck> trucks = truckService.getAllTrucks();
        model.addAttribute("tyres", tyres);
        model.addAttribute("trucks", trucks);
        model.addAttribute("commission", new Commission());
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Commission a new tyre");
        model.addAttribute("item", "Commission");
        return "commission/add";
    }


    @GetMapping("/approve/{id}")
    public String approveCommissionForm(@PathVariable Long id, Model model) {
        Commission commission = commissionService.getCommissionById(id).get();
        List<Commission> previousCommissions = commissionService.getByTruckId(commission.getTruck().getId());
        List<Decommission> previousDecommissions = decommissionService.getByTruckId(commission.getTruck().getId());

        model.addAttribute("commission", commission);
        model.addAttribute("previousDecommissions", previousDecommissions);
        model.addAttribute("previousCommissions", previousCommissions);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Approve tyre commission");
        model.addAttribute("item", "Commission");
        return "commission/approve";
    }

    @GetMapping("/complete/{id}")
    public String completeCommissionForm(@PathVariable Long id, Model model) {
        Commission commission = commissionService.getCommissionById(id).get();
        model.addAttribute("commission", commission);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Complete tyre commission");
        model.addAttribute("item", "Commission");
        return "commission/complete";
    }

    @PostMapping("/create")
    public String createCommission(@ModelAttribute("commission") Commission commission, BindingResult result,
                                   Model model) {
        Tyre existingTyre = tyreService.findTyreById(commission.getTyre().getId()).get();
        if(existingTyre != null && existingTyre.getSerialNumber() != null && !existingTyre.getSerialNumber().isEmpty()){
            if(!Objects.equals(existingTyre.getState(), "AVAILABLE")){
                result.rejectValue("tyre", null,
                        "The tyre registered with the serial number is currently unavailable");
            }

        }

        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("commission", commission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Commission a new tyre");
            model.addAttribute("item", "Commission");
            return "commission/add";
        }

        try {
            // Set created date to the current date and time
            commission.setResponsibleOfficer(userService.getCurrentUserEmail());
            commission.setCreatedDate(LocalDateTime.now());

            // Save the tyre entity to the database
            commissionService.save(commission);
            return "redirect:/commission/add?success";
        } catch (Exception e) {
            return "commission/add";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/approve")
    public String approveCommission(@ModelAttribute("commission") Commission commission, BindingResult result,
                                   Model model) {

        Commission existingCommission = commissionService.getCommissionById(commission.getId()).get();
        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("commission", existingCommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Commission a new tyre");
            model.addAttribute("item", "Commission");
            return "commission/approve";
        }

        try {
            // Save the tyre entity to the database
            existingCommission.setApprovalComments(commission.getApprovalComments());
            existingCommission.setApprovalDate(LocalDateTime.now());
            commissionService.approve(existingCommission);
            return "redirect:/commission/?approved";
        } catch (Exception e) {
            return "commission/approve";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/complete")
    public String completeCommission(@ModelAttribute("commission") Commission commission, BindingResult result,
                                   Model model) {

        Commission existingCommission = commissionService.getCommissionById(commission.getId()).get();

        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("commission", existingCommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Commission a new tyre");
            model.addAttribute("item", "Commission");
            return "commission/complete";
        }

        try {
            // Save the tyre entity to the database
            existingCommission.setCompletionComments(commission.getCompletionComments());
            existingCommission.setCompletionDate(LocalDateTime.now());
            commissionService.complete(existingCommission);
            return "redirect:/commission/?completed";
        } catch (Exception e) {
            return "commission/complete";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/view/{id}")
    public String viewCommissionPage(@PathVariable Long id, Model model) {
        Optional<Commission> commissionOptional = commissionService.getCommissionById(id);

        if (commissionOptional.isPresent()) {
            Commission commission = commissionOptional.get();
            model.addAttribute("commission", commission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Commission");
            model.addAttribute("item", "View");
            return "commission/view";
        } else {
            // Handle case where truck with given ID is not found
            return "redirect:/commission"; // Redirect to a truck list page or handle as appropriate
        }

    }
    @GetMapping("/cancel/{id}")
    public String cancelCommission(@PathVariable Long id) {

        Commission existingCommission = commissionService.getCommissionById(id).get();

        try {
            // Save the tyre entity to the database
            existingCommission.setCancellationDate(LocalDateTime.now());
            commissionService.cancel(existingCommission);
            return "redirect:/commission/?canceled";
        } catch (Exception e) {
            return "commission/complete";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
