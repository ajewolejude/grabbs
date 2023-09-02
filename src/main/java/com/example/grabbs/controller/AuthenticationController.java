package com.example.grabbs.controller;

import com.example.grabbs.dto.UserDto;
import com.example.grabbs.model.User;
import com.example.grabbs.service.UserService;
import com.example.grabbs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


	@Autowired
	private UserServiceImpl userService;

	// handler method to handle login request
	@GetMapping("/login")
	public String login(){
		return "auth/login";
	}
//
//	// handler method to handle user registration form submit request
//	@PostMapping("/login")
//	public String loginAction(@Valid @ModelAttribute("user") UserDto userDto,
//							   BindingResult result,
//							   Model model){
//		User existingUser = userService.findUserByEmail(userDto.getEmail());
//
//		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//			userService.(userDto);
//			return "redirect:/index";
//		}else{
//			model.addAttribute("user", userDto);
//			return "redirect:/auth/login?error";
//		}
//	}
	// handler method to handle user registration form request
	@GetMapping("/register")
	public String showRegistrationForm(Model model){
		// create model object to store form data
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "auth/register";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto,
							   BindingResult result,
							   Model model){
		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
			result.rejectValue("email", null,
					"There is already an account registered with the same email");
		}

		if(result.hasErrors()){
			model.addAttribute("user", userDto);
			return "auth/register";
		}

		userService.saveUser(userDto);
		return "redirect:/auth/register?success";
	}

	// handler method to handle list of users
	@GetMapping("/users")
	public String users(Model model){
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}


	@GetMapping("/recover")
	public String recoverpw(Model model) {
		return "auth/recoverpw";
	}

//
//	@GetMapping("/register-2")
//	public String register2(Model model) {
//		return "auth/register-2";
//	}
//
//	@GetMapping("/recoverpw")
//	public String recoverpw(Model model) {
//		return "auth/recoverpw";
//	}
//
//	@GetMapping("/recoverpw-2")
//	public String recoverpw2(Model model) {
//		return "auth/recoverpw-2";
//	}
//
//	@GetMapping("/lock-screen")
//	public String lock_screen(Model model) {
//		return "auth/lock-screen";
//	}
//
//	@GetMapping("/lock-screen-2")
//	public String lock_screen2(Model model) {
//		return "auth/lock-screen-2";
//	}
//
//	@GetMapping("/confirm-mail")
//	public String confirm_mail(Model model) {
//		return "auth/confirm-mail";
//	}
//
//	@GetMapping("/confirm-mail-2")
//	public String confirm_mail2(Model model) {
//		return "auth/confirm-mail-2";
//	}
//
//	@GetMapping("/email-verification")
//	public String email_verification(Model model) {
//		return "auth/email-verification";
//	}
//
//	@GetMapping("/email-verification-2")
//	public String email_verification2(Model model) {
//		return "auth/email-verification-2";
//	}
//
//	@GetMapping("/two-step-verification")
//	public String two_step_verification(Model model) {
//		return "auth/two-step-verification";
//	}
//
//	@GetMapping("/two-step-verification-2")
//	public String two_step_verification2(Model model) {
//		return "auth/two-step-verification-2";
//	}
//

}
