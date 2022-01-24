package com.gcu.controller;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.RegBusinessService;
import com.gcu.model.UserModel;


/**
 * The Controller that handles all the routes to Register pages
 */
@Controller
@RequestMapping("/register")
public class RegisterController 
{
	@Autowired
	RegBusinessService reg;
	/**
	 * displays the register page and form
	 * @param model (page model)
	 * @return register page
	 */
	@GetMapping("/")
	public String display(Model model) 
	{
		//display method for landing on registration page
	    model.addAttribute("title", "Registration Form");
	    //create new userModel and set to 'userModel attribute'
	    model.addAttribute("userModel", new UserModel());
	    //return register view
	    return "register";
	}
	/**
	 * displays the login page if registration is valid
	 * else it routes back to the registration page with validation errors
	 * @param userModel (user info from form)
	 * @param bindingResult (error logging)
	 * @param model (page model)
	 * @return either register or login page based on if the user info is valid
	 */
	@PostMapping("/doRegister")
	public String doRegister(@Valid UserModel userModel, BindingResult bindingResult, Model model) 
	{
		//doRegister for register view submission
		//validation error checking
		if (bindingResult.hasErrors()) 
		{
			// if invalid information, reload page
			model.addAttribute("title", "Register Form");
			return "register";
		}
		//add attributes for loginform, create new UserModel for 'loginModel' attribute
			model.addAttribute("title", "Login Form");
			model.addAttribute("loginModel", new UserModel());
			//call service to register the model
			reg.register(userModel);
		    //finally, Redirect the user to login
		
		    return "redirect:/login/";
		
	}
}