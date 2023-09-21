package com.example.grabbs.controller;

import com.example.grabbs.model.Attachment;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.AttachmentService;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.service.UserServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
@RequestMapping("procurement")
public class ProcureController {
    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private TyreService tyreService;

    @Autowired
    private AttachmentService attachmentService;


    @GetMapping("/")
    public String index(Model model) {

        List<Tyre> tyres = tyreService.getTyresByState("TO PROCURE");
        model.addAttribute("tyres", tyres);
        model.addAttribute("template", "layout");
        model.addAttribute("title", "Tyre List");
        model.addAttribute("item", "Dashboards");
        model.addAttribute("subitem", "Dashboard");

        return "procure/index";
    }

}
