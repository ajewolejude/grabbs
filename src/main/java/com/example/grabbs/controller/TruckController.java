package com.example.grabbs.controller;

import com.example.grabbs.model.*;
import com.example.grabbs.model.Truck;
import com.example.grabbs.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("truck")
public class TruckController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommissionService commissionService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private DecommissionService decommissionService;


    @Autowired
    private TruckService truckService;


    @GetMapping("/")
    public String index(Model model) {
        List<Truck> trucks = truckService.getAllTrucks();
        model.addAttribute("trucks", trucks);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Truck List");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");

        return "truck/index";
    }

    @GetMapping("/add")
    public String createTruckForm(Model model) {
        List<Brand> brands = brandService.getAllBrandsByTypeAndState("Truck","active");
        model.addAttribute("brands", brands);
        model.addAttribute("truck", new Truck());
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Add new Truck");
        model.addAttribute("item", "Truck");
        return "truck/add";
    }

    @PostMapping("/create")
    public String createTruck(@Valid @ModelAttribute("truck") Truck truck, BindingResult result,
                                              Model model) {
        Truck existingTruckByVin = truckService.findTruckByVin(truck.getVin());
        if(existingTruckByVin != null && existingTruckByVin.getVin() != null && !existingTruckByVin.getVin().isEmpty()){
            result.rejectValue("vin", null,
                    "There is already a truck registered with the vin");
        }

        Truck existingTruckByTakId = truckService.findTruckByTakId(truck.getTakId());
        if(existingTruckByTakId != null && existingTruckByTakId.getTakId() != null && !existingTruckByTakId.getTakId().isEmpty()){
            result.rejectValue("takId", null,
                    "There is already a truck registered with the tak id");
        }

        if(result.hasErrors()){
            model.addAttribute("truck", truck);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Add new Truck");
            model.addAttribute("item", "Tables");
            return "truck/add";
        }

        try {
            // Set created date to the current date and time
            truck.setCreatedDate(LocalDateTime.now());

            // Save the truck entity to the database
            truckService.save(truck);
            return "redirect:/truck/add?success";
        } catch (Exception e) {
            return "truck/add";
            //return new ResponseEntity<>("Error creating truck", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/view/{id}")
    public String viewTruckPage(@PathVariable Long id, Model model) {
        Optional<Truck> truckOptional = truckService.getTruckById(id);

        List<Commission> previousCommissions = commissionService.getByTruckIdOrderByCreatedDateDesc(id);
        List<Decommission> previousDecommissions = decommissionService.getByTruckIdOrderByCreatedDateDesc(id);

        if (truckOptional.isPresent()) {
            Truck truck = truckOptional.get();
            model.addAttribute("tyres", truck.getTyres());
            model.addAttribute("truck", truck);
            model.addAttribute("action", new Action());
            model.addAttribute("previousCommissions", previousCommissions);
            model.addAttribute("previousDecommissions", previousDecommissions);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "View Truck - " + truck.getTakId());
            model.addAttribute("item", "View");
            return "truck/view";
        } else {
            // Handle case where truck with given ID is not found
            return "redirect:/truck"; // Redirect to a truck list page or handle as appropriate
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditTruckPage(@PathVariable Long id, Model model) {

        List<Brand> brands = brandService.getAllBrandsByTypeAndState("Truck","active");
        model.addAttribute("brands", brands);
        Optional<Truck> truck = truckService.getTruckById(id);

        if (truck.isPresent()) {
            model.addAttribute("truck", truck.get());
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Truck");
            model.addAttribute("item", "Edit");
            return "truck/edit"; // Thymeleaf HTML template name
        } else {
            // Handle not found scenario
            return "redirect:/truck"; // Redirect to truck list page
        }
    }


    @PostMapping("/update")
    public String updateTruck(@ModelAttribute Truck truck,BindingResult result,
                             Model model) throws NotFoundException {

        Truck existingTruck = truckService.findTruckByVin(truck.getVin());
        if(existingTruck != null && existingTruck.getVin() != null && !existingTruck.getVin().isEmpty()){
            if (!Objects.equals(existingTruck.getId(), truck.getId())){
                result.rejectValue("vin", null,
                        "There is already a truck registered with the vin");
            }
        }


        Truck existingTruckByTakId = truckService.findTruckByTakId(truck.getTakId());
        if(existingTruckByTakId != null && existingTruckByTakId.getTakId() != null && !existingTruckByTakId.getTakId().isEmpty()){
            if (!Objects.equals(existingTruckByTakId.getId(), truck.getId())){
                result.rejectValue("takId", null,
                        "There is already a truck registered with the tak id");
            }
        }


        if(result.hasErrors()){
            model.addAttribute("truck", truck);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Truck");
            model.addAttribute("item", "Edit");
            return "truck/edit";
        }

        try {
            // Save the truck entity to the database
            truckService.update(truck);
            return "redirect:/truck/edit/" + truck.getId() + "?success";
        } catch (Exception e) {
            return "truck/edit";
            //return new ResponseEntity<>("Error creating truck", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
