package com.prasant.spring.mvc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prasant.spring.mvc.model.Message;
import com.prasant.spring.mvc.model.User;
import com.prasant.spring.mvc.service.UserService;
import com.prasant.spring.mvc.validationgroup.PersistenceValidationGroup;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Validated(PersistenceValidationGroup.class) User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "newaccount";
		}
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		if (userService.userExists(user.getUsername())) {
			bindingResult.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		try {
			userService.createUser(user);
		} catch (DuplicateKeyException e) {
			bindingResult.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		return "accountcreated";
	}
	
	@RequestMapping("/logout")
	public String showLogout() {
		return "logout";
	}
	
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "admin";
	}
	
	@RequestMapping(value="/getmessages", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getMessages(Principal principal) {
		List<Message> messages = null;
		if (principal == null) {
			messages = new ArrayList<>();
		} else {
			String username = principal.getName();
			messages = userService.getMessages(username);
		}
		Map<String, Object> outputData = new HashMap<>();
		outputData.put("messages", messages);
		outputData.put("numberOfMessages", messages.size());
		return outputData;
	}
	
	@RequestMapping("/messages")
	public String showMessages() {
		return "messages";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "accessDenied";
	}

}
