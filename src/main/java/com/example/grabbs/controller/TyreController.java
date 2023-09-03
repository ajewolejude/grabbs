package com.example.grabbs.controller;

import com.example.grabbs.model.Truck;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.service.UserServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("tyre")
public class TyreController {
    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private TyreService tyreService;


    @GetMapping("/")
    public String index(Model model) {
        List<Tyre> tyres = tyreService.getAllTyres();
        model.addAttribute("tyres", tyres);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Tyre List");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");

        return "tyre/index";
    }

    @GetMapping("/add")
    public String createTyreForm(Model model) {
        model.addAttribute("tyre", new Tyre());
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Add new Tyre");
        model.addAttribute("item", "Tyre");
        return "tyre/add";
    }

    @PostMapping("/create")
    public String createTyre(@Valid @ModelAttribute("tyre") Tyre tyre, BindingResult result,
                                              Model model) {
        Tyre existingTyreByVin = tyreService.findTyreBySerialNumber(tyre.getSerialNumber());


        if(existingTyreByVin != null && existingTyreByVin.getSerialNumber() != null && !existingTyreByVin.getSerialNumber().isEmpty()){
            result.rejectValue("serialNumber", null,
                    "There is already a tyre registered with the serial number");
        }

        Tyre existingTyreByTakId = tyreService.findTyreByTakId(tyre.getTakId());
        if(existingTyreByTakId != null && existingTyreByTakId.getTakId() != null && !existingTyreByTakId.getTakId().isEmpty()){
            result.rejectValue("takId", null,
                    "There is already a tyre registered with the tak id");
        }


        if(result.hasErrors()){
            model.addAttribute("tyre", tyre);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Add new Tyre");
            model.addAttribute("item", "Tables");
            return "tyre/add";
        }

        try {
            // Set created date to the current date and time
            tyre.setCreatedDate(LocalDateTime.now());

            // Save the tyre entity to the database
            tyreService.save(tyre);
            return "redirect:/tyre/add?success";
        } catch (Exception e) {
            return "tyre/add";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view/{id}")
    public String viewTruckPage(@PathVariable Long id, Model model) {
        Optional<Tyre> tyreOptional = tyreService.findTyreById(id);

        if (tyreOptional.isPresent()) {
            Tyre tyre = tyreOptional.get();
            model.addAttribute("tyre", tyre);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Tyre");
            model.addAttribute("item", "View");
            return "tyre/view";
        } else {
            // Handle case where truck with given ID is not found
            return "redirect:/tyre"; // Redirect to a truck list page or handle as appropriate
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditTyrePage(@PathVariable Long id, Model model) {
        Optional<Tyre> tyre = tyreService.findTyreById(id);

        if (tyre.isPresent()) {
            model.addAttribute("tyre", tyre.get());
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Tyre");
            model.addAttribute("item", "Edit");
            return "tyre/edit"; // Thymeleaf HTML template name
        } else {
            // Handle not found scenario
            return "redirect:/tyre"; // Redirect to tyre list page
        }
    }


    @PostMapping("/update")
    public String updateTyre(@ModelAttribute Tyre tyre,BindingResult result,
                             Model model) throws NotFoundException {
        tyreService.update(tyre);

        Tyre existingTyreByVin = tyreService.findTyreBySerialNumber(tyre.getSerialNumber());


        if(existingTyreByVin != null && existingTyreByVin.getSerialNumber() != null && !existingTyreByVin.getSerialNumber().isEmpty()){
            if (!Objects.equals(existingTyreByVin.getId(), tyre.getId())){
                result.rejectValue("serialNumber", null,
                        "There is already a tyre registered with the serial number");
            }
        }



        Tyre existingTyreByTakId = tyreService.findTyreByTakId(tyre.getTakId());
        if(existingTyreByTakId != null && existingTyreByTakId.getSerialNumber() != null && !existingTyreByTakId.getSerialNumber().isEmpty()){
            if (!Objects.equals(existingTyreByTakId.getId(), tyre.getId())){
                result.rejectValue("takId", null,
                        "There is already a tyre registered with the tak id");
            }
        }



        if(result.hasErrors()){
            model.addAttribute("tyre", tyre);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Tyre");
            model.addAttribute("item", "Edit");
            return "tyre/edit";
        }

        try {
            // Save the tyre entity to the database
            tyreService.save(tyre);
            return "redirect:/tyre/edit/" + tyre.getId() + "?success";
        } catch (Exception e) {
            return "tyre/edit";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
