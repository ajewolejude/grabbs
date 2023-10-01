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
@RequestMapping("/decommission")
public class DecommissionController {

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
        List<Tyre> commissionedTyres = tyreService.getTyresByState("COMMISSIONED");
        model.addAttribute("commissionedTyres", commissionedTyres);
        List<Tyre> decommissionedTyres = tyreService.getTyresByState("DECOMMISSIONED");
        model.addAttribute("decommissionedTyres", decommissionedTyres);
        List<Decommission> decommissions = decommissionService.getAllDecommissions();
        model.addAttribute("decommissions", decommissions);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Tyre");
        model.addAttribute("item", "Decommissions");
        model.addAttribute("subitem", "Dashboard");
        return "decommission/index";
    }

    @GetMapping("/add/{tyre_id}")
    public String createTyreForm(@PathVariable Long tyre_id, Model model) {
        List<Commission> commission = commissionService.getByTyreIdAndState(tyre_id, "COMPLETED");
        Decommission decommission = new Decommission();
        decommission.setTyre(commission.get(0).getTyre());
        decommission.setTruck(commission.get(0).getTruck());
        model.addAttribute("decommission", decommission);
        List<Tyre> tyres = tyreService.findTyresByState("COMMISSIONED");
        List<Truck> trucks = truckService.getAllTrucks();
        model.addAttribute("commission", commission.get(0));
        model.addAttribute("tyres", tyres);
        model.addAttribute("trucks", trucks);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Decommission a new tyre");
        model.addAttribute("item", "Decommission");
        return "decommission/add";
    }


    @GetMapping("/approve/{id}")
    public String approveCommissionForm(@PathVariable Long id, Model model) {
        Decommission decommission = decommissionService.getDecommissionById(id).get();
        model.addAttribute("decommission", decommission);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Approve tyre decommission");
        model.addAttribute("item", "Decommission");
        return "decommission/approve";
    }

    @GetMapping("/complete/{id}")
    public String completeCommissionForm(@PathVariable Long id, Model model) {
        Decommission decommission = decommissionService.getDecommissionById(id).get();
        model.addAttribute("decommission", decommission);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Complete tyre decommission");
        model.addAttribute("item", "Decommission");
        return "decommission/complete";
    }

    @PostMapping("/create")
    public String createDecommission(@ModelAttribute("decommission") Decommission decommission, BindingResult result,
                                   Model model) {
        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("decommission", decommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Decommission a new tyre");
            model.addAttribute("item", "Decommission");
            return "decommission/add";
        }

        try {
            // Set created date to the current date and time
            decommission.setCreatedDate(LocalDateTime.now());
            decommission.setResponsibleOfficer(userService.getCurrentUserEmail());

            // Save the tyre entity to the database
            Decommission decommission1 = decommissionService.save(decommission);
            return "redirect:/decommission/view/"+decommission1.getId();
        } catch (Exception e) {
            return "decommission/add";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/approve")
    public String approveCommission(@ModelAttribute("decommission") Decommission decommission, BindingResult result,
                                   Model model) {

        Decommission existingCommission = decommissionService.getDecommissionById(decommission.getId()).get();

        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("decommission", existingCommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Decommission a new tyre");
            model.addAttribute("item", "Decommission");
            return "decommission/approve";
        }

        try {
            // Save the tyre entity to the database
            existingCommission.setApprovalComments(decommission.getApprovalComments());
            existingCommission.setApprovalDate(LocalDateTime.now());
            decommissionService.approve(existingCommission);
            return "redirect:/decommission/?approved";
        } catch (Exception e) {
            return "decommission/approve";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/complete")
    public String completeCommission(@ModelAttribute("decommission") Decommission decommission, BindingResult result,
                                   Model model) {

        Decommission existingCommission = decommissionService.getDecommissionById(decommission.getId()).get();

        if(result.hasErrors()){
            List<User> users = userService.getAllUsers();
            List<Tyre> tyres = tyreService.findTyresByState("AVAILABLE");
            model.addAttribute("tyres", tyres);
            model.addAttribute("users", users);
            model.addAttribute("decommission", existingCommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Decommission a new tyre");
            model.addAttribute("item", "Decommission");
            return "decommission/complete";
        }

        try {
            // Save the tyre entity to the database
            existingCommission.setCompletionComments(decommission.getCompletionComments());
            existingCommission.setCompletionDate(LocalDateTime.now());
            decommissionService.complete(existingCommission);
            return "redirect:/decommission/?completed";
        } catch (Exception e) {
            return "decommission/complete";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/view/{id}")
    public String viewCommissionPage(@PathVariable Long id, Model model) {
        Optional<Decommission> decommissionOptional = decommissionService.getDecommissionById(id);

        if (decommissionOptional.isPresent()) {
            Decommission decommission = decommissionOptional.get();
            model.addAttribute("decommission", decommission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Decommission");
            model.addAttribute("item", "View");
            return "decommission/view";
        } else {
            // Handle case where truck with given ID is not found
            return "redirect:/decommission"; // Redirect to a truck list page or handle as appropriate
        }

    }
    @GetMapping("/cancel/{id}")
    public String cancelCommission(@PathVariable Long id) {

        Decommission existingCommission = decommissionService.getDecommissionById(id).get();

        try {
            // Save the tyre entity to the database
            existingCommission.setCancellationDate(LocalDateTime.now());
            decommissionService.cancel(existingCommission);
            return "redirect:/decommission/?canceled";
        } catch (Exception e) {
            return "decommission/complete";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
