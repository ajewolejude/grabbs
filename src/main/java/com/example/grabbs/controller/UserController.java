package com.example.grabbs.controller;

import com.example.grabbs.dto.UserDto;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.model.User;
import com.example.grabbs.service.AuthService;
import com.example.grabbs.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthService authService;


	@GetMapping("/")
	public String listUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("template", "layout");
		model.addAttribute("title", "Users List");
		model.addAttribute("item", "User");
		return "user/users";
	}

	@GetMapping("/add")
	public String addUsersPage(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		model.addAttribute("template", "layout");
		model.addAttribute("title", "Add new Users");
		model.addAttribute("item", "User");
		return "user/add_user";
	}

	@GetMapping("/profile")
	public String userProfilePage(Model model) {
		User user = userService.findUserByEmail(authService.getCurrentUserEmail());
		model.addAttribute("user", user);
		model.addAttribute("template", "layout");
		model.addAttribute("title", "User Profile");
		model.addAttribute("item", "User");
		return "user/profile";
	}

	@GetMapping("/profile/edit")
	public String editUsersPage(Model model) {

		// Fetch the existing user data from the database using the currently logged-in user ID
		User existingUser = userService.findUserByEmail(authService.getCurrentUserEmail()); // Replace this with your own method to get the current user data

		// Map the existing user data to the UserDto object
		UserDto user = new UserDto();
		user.setName(existingUser.getName());
		user.setPhone(existingUser.getPhone());
		user.setEmail(existingUser.getEmail());

		model.addAttribute("user", user);
		model.addAttribute("template", "layout");
		model.addAttribute("title", "Edit Profile");
		model.addAttribute("item", "User");
		return "user/edit_profile";
	}

	@GetMapping("/settings/edit")
	public String editSettingsPage(Model model) {

		// Fetch the existing user data from the database using the currently logged-in user ID
		User existingUser = userService.findUserByEmail(authService.getCurrentUserEmail()); // Replace this with your own method to get the current user data

		// Map the existing user data to the UserDto object
		UserDto user = new UserDto();
		user.setEmail(existingUser.getEmail());

		model.addAttribute("user", user);
		model.addAttribute("template", "layout");
		model.addAttribute("title", "Edit Settings");
		model.addAttribute("item", "User");
		return "user/edit_settings";
	}


	// handler method to handle profile modify form submit request

	 @PostMapping("/profile/modify")
	public String modifyProfile(@Valid @ModelAttribute("user") UserDto userDto,
							   BindingResult result,
							   Model model){

		if(result.hasErrors()){
			model.addAttribute("user", userDto);
			model.addAttribute("template", "layout");
			model.addAttribute("title", "Edit");
			model.addAttribute("item", "Profile");
			return "user/edit_profile";
		}

		userService.editProfile(userDto);
		return "redirect:/user/profile/edit?success";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/save")
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
			model.addAttribute("template", "layout");
			model.addAttribute("title", "Add new Users");
			model.addAttribute("item", "Tables");
			return "user/add_user";
		}

		userService.saveUser(userDto);
		return "redirect:/user/add?success";
	}

	@GetMapping("/enable/{id}")
	public String enableUser(@PathVariable Long id, Model model) {
		User existingUser = userService.findUserById(id);

		try {
			userService.enableUser(existingUser);
			return "redirect:/user/?success_enable";
		} catch (Exception e) {
			return "user/?error";
		}

	}

	@GetMapping("/disable/{id}")
	public String disableUser(@PathVariable Long id, Model model) {
		User existingUser = userService.findUserById(id);

		try {
			userService.disableUser(existingUser);
			return "redirect:/user/?success_disable";
		} catch (Exception e) {
			return "user/?error";
			//return new ResponseEntity<>("Error creating tyre", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}