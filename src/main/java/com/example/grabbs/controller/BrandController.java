package com.example.grabbs.controller;

import com.example.grabbs.model.Attachment;
import com.example.grabbs.model.Brand;
import com.example.grabbs.service.AttachmentService;
import com.example.grabbs.service.BrandService;
import com.example.grabbs.service.UserServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private BrandService brandService;

    @Autowired
    private AttachmentService attachmentService;


    @GetMapping("/")
    public String index(Model model) {
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Brand List");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");

        return "brand/index";
    }

    @GetMapping("/add")
    public String createBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Add new Brand");
        model.addAttribute("item", "Brand");
        return "brand/add";
    }

    @PostMapping("/create")
    public String createBrand(@Valid @ModelAttribute("brand") Brand brand, BindingResult result,
                             Model model) {

        Brand existingBrandByBrandNameAndType = brandService.findBrandByBrandNameAndType(brand.getBrandName(), brand.getType());
        if (existingBrandByBrandNameAndType != null && existingBrandByBrandNameAndType.getId() != null && !existingBrandByBrandNameAndType.getBrandName().isEmpty()) {
            result.rejectValue("brandName", null,
                    "There is already a brand registered with the name and type");
        }

        brand.setResponsibleOfficer(userService.getCurrentUserEmail());

        if (result.hasErrors()) {
            model.addAttribute("brand", brand);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Add new Brand");
            model.addAttribute("item", "Tables");
            return "brand/add";
        }

        try {
            // Set created date to the current date and time
            brand.setCreatedDate(LocalDateTime.now());

            // Save the brand entity to the database
            brandService.save(brand);
            return "redirect:/brand/add?success";
        } catch (Exception e) {
            return "brand/add";
            //return new ResponseEntity<>("Error creating brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view/{id}")
    public String viewBrandPage(@PathVariable Long id, Model model) {
        Optional<Brand> brandOptional = brandService.getBrandById(id);

        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            model.addAttribute("brand", brand);;
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Brand");
            model.addAttribute("item", "View");
            return "brand/view";
        } else {
            // Handle case where truck with given ID is not found
            return "redirect:/brand"; // Redirect to a truck list page or handle as appropriate
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditBrandPage(@PathVariable Long id, Model model) {
        Optional<Brand> brand = brandService.getBrandById(id);

        if (brand.isPresent()) {
            model.addAttribute("brand", brand.get());
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Brand");
            model.addAttribute("item", "Edit");
            return "brand/edit"; // Thymeleaf HTML template name
        } else {
            // Handle not found scenario
            return "redirect:/brand"; // Redirect to brand list page
        }
    }


    @PostMapping("/update")
    public String updateBrand(@ModelAttribute Brand brand, BindingResult result,
                             Model model) throws NotFoundException {

        Brand existingBrandByBrandNameAndType = brandService.findBrandByBrandNameAndType(brand.getBrandName(), brand.getType());
        if (existingBrandByBrandNameAndType != null && existingBrandByBrandNameAndType.getId() != null && !existingBrandByBrandNameAndType.getBrandName().isEmpty()) {
            result.rejectValue("brandName", null,
                    "There is already a brand registered with the name and type");
        }



        if (result.hasErrors()) {
            model.addAttribute("brand", brand);
            model.addAttribute("template", "layout");
            model.addAttribute("title", "Brand");
            model.addAttribute("item", "Edit");
            return "brand/edit";
        }

        try {
            // Save the brand entity to the database
            brandService.update(brand);
            return "redirect:/brand/edit/" + brand.getId() + "?success";
        } catch (Exception e) {
            return "brand/edit";
            //return new ResponseEntity<>("Error creating brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
