package com.example.grabbs.controller;

import com.example.grabbs.model.Commission;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.model.User;
import com.example.grabbs.service.CommissionService;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/")
    public String index(Model model) {
        //if(!userService.isUserAuthenticated()) return "login";
        List<Commission> commissions = commissionService.getAllCommissions();
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("commissions", commissions);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Dashboard");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");
        return "commission/index";
    }

    @GetMapping("/add")
    public String createTyreForm(Model model) {
        model.addAttribute("commission", new Commission());
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Commission a new tyre");
        model.addAttribute("item", "Commission");
        return "commission/add";
    }

    @PostMapping("/create")
    public String createCommission(@ModelAttribute("commission") Commission commission, BindingResult result,
                                   Model model) {
//        Commission existingCommission = commissionService.findTyreByTyreId(tyre.getSerialNumber());
//        if(existingTyre != null && existingTyre.getSerialNumber() != null && !existingTyre.getSerialNumber().isEmpty()){
//            result.rejectValue("serialNumber", null,
//                    "There is already a tyre registered with the serial number");
//        }

        if(result.hasErrors()){
            model.addAttribute("commission", commission);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Commission a new tyre");
            model.addAttribute("item", "Commission");
            return "commission/add";
        }

        try {
            // Set created date to the current date and time
            commission.setCreatedDate(LocalDateTime.now());

            // Save the tyre entity to the database
            commissionService.save(commission);
            return "redirect:/commission/add?success";
        } catch (Exception e) {
            return "commission/add";
            //return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
