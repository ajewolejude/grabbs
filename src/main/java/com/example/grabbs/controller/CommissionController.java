package com.example.grabbs.controller;

import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.TyreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private TyreService tyreService;


    @GetMapping("/")
    public String index(Model model) {
        //if(!userService.isUserAuthenticated()) return "login";
        List<Tyre> tyres = tyreService.getAllTyres();
        model.addAttribute("tyres", tyres);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Dashboard");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");
        return "commission/index";
    }

}
