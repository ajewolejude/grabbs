package com.example.grabbs.controller;

import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.TyreService;
import com.example.grabbs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {


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
		return "index";
	}

	@GetMapping("/dashboard-saas")
	public String sass(Model model) {
		model.addAttribute("title", "Dashboard");
		model.addAttribute("item", "Dashboards");
		model.addAttribute("subitem", "Saas");
		return "dashboard-saas";
	}
	
	@GetMapping("/dashboard-crypto")
	public String crypto(Model model) {
		model.addAttribute("title", "Crypto");
		model.addAttribute("item", "Dashboards");
		model.addAttribute("subitem", "Crypto");
		return "dashboard-crypto";
	}
	
	@GetMapping("/dashboard-blog")
	public String blog(Model model) {
		model.addAttribute("title", "Blog");
		model.addAttribute("item", "Dashboards");
		model.addAttribute("subitem", "Blog");
		return "dashboard-blog";
	}
	
	@GetMapping("/dashboard-job")
	public String job(Model model) {
		model.addAttribute("title", "Job");
		model.addAttribute("item", "Dashboards");
		model.addAttribute("subitem", "Job");
		return "dashboard-job";
	}

	@GetMapping("/layouts-horizontal")
	public String layoutshorizontal(Model model) {
		model.addAttribute("template", "layout-horizontal");
		model.addAttribute("title", "Horizontal Layout");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Layout");
		return "index";
	}
	
	@GetMapping("/layouts-hori-topbar-light")
	public String layoutshorizontaltopbarlight(Model model) {
		model.addAttribute("template", "layouts-hori-topbar-light");
		model.addAttribute("title", "Horizontal Topbar Light");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Topbar Light");
		return "index";
	}
	
	@GetMapping("/layouts-hori-boxed-width")
	public String layoutshoriboxedwidth(Model model) {
		model.addAttribute("template", "layouts-hori-boxed-width");
		model.addAttribute("title", "Horizontal Boxed Width");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Boxed Width");
		return "index";
	}
	
	@GetMapping("/layouts-light-sidebar")
	public String layoutslightsidebar(Model model) {
		model.addAttribute("template", "layouts-light-sidebar");
		model.addAttribute("title", "Light Sidebar");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Light Sidebar");
		return "index";
	}
	
	@GetMapping("/layouts-compact-sidebar")
	public String layoutscompactsidebar(Model model) {
		model.addAttribute("template", "layouts-compact-sidebar");
		model.addAttribute("title", "Compact Sidebar");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Compact Sidebar");
		return "index";
	}
	
	@GetMapping("/layouts-icon-sidebar")
	public String layoutsiconsidebar(Model model) {
		model.addAttribute("template", "layouts-icon-sidebar");
		model.addAttribute("title", "Icon Sidebar");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Icon Sidebar");
		return "index";
	}
	
	@GetMapping("/layouts-boxed")
	public String layoutsboxed(Model model) {
		model.addAttribute("template", "layouts-boxed");
		model.addAttribute("title", "Boxed Width");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Boxed Width");
		return "index";
	}
	
	@GetMapping("/layouts-preloader")
	public String layoutspreloader(Model model) {
		model.addAttribute("template", "layouts-preloader");
		model.addAttribute("title", "Preloader Layout");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Preloader Layout");
		return "index";
	}
	
	@GetMapping("/layouts-colored-sidebar")
	public String layoutscoloredsidebar(Model model) {
		model.addAttribute("template", "layouts-colored-sidebar");
		model.addAttribute("title", "Colored Sidebar");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Colored Sidebar");
		return "index";
	}
	
	@GetMapping("/layouts-scrollable")
	public String layoutsscrollable(Model model) {
		model.addAttribute("template", "layouts-scrollable");
		model.addAttribute("title", "Scrollable Layout");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Scrollable Layout");
		return "index";
	}
	
	@GetMapping("/layouts-hori-preloader")
	public String layoutshoripreloader(Model model) {
		model.addAttribute("template", "layouts-hori-preloader");
		model.addAttribute("title", "Horizontal Preloader Layout");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Preloader Layout");
		return "index";
	}
	
	@GetMapping("/layouts-hori-colored-header")
	public String layoutshoricoloredheader(Model model) {
		model.addAttribute("template", "layouts-hori-colored-header");
		model.addAttribute("title", "Horizontal Topbar Colored");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Topbar Colored");
		return "index";
	}
	
	@GetMapping("/layouts-hori-scrollable")
	public String layoutshoriscrollable(Model model) {
		model.addAttribute("template", "layouts-hori-scrollable");
		model.addAttribute("title", "Horizontal Scrollable Layout");
		model.addAttribute("item", "Layouts");
		model.addAttribute("subitem", "Horizontal Scrollable Layout");
		return "index";
	}
	
	@GetMapping("/chat")
	public String chart(Model model) {
		model.addAttribute("template", "layouts-hori-scrollable");
		model.addAttribute("title", "Chat");
		model.addAttribute("item", "Skote");
		model.addAttribute("subitem", "Chat");
		return "chat";
	}
	
	@GetMapping("/apps-filemanager")
	public String apps_filemanager(Model model) {
//		model.addAttribute("template", "layouts-hori-scrollable");
		model.addAttribute("title", "File Manager");
		model.addAttribute("item", "Apps");
		model.addAttribute("subitem", "File Manager");
		return "apps-filemanager";
	}
}