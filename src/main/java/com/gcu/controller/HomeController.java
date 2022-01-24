package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * simple welcome controller that returns a view name
 *
 */
@Controller
@RequestMapping("/")
public class HomeController 
{
	/**
	 * A route to home that sets the title and welcome message
	 * @param model (page model)
	 * @return the home page
	 */
	@GetMapping("/")
	public String home(Model model)
	{
		//Simply return a Model w/an attribute named 
		//message and return a view named home using a string
		model.addAttribute("title", "Cloud Computing Testing");
		model.addAttribute("welcomeMessage", "Welcome");
		return "index";
	}
	/** Handles the home page after the user is logged in
	 * @param model (page model)
	 * @return index page
	 */
	@GetMapping("/home")
	public String homePostLogin(Model model)
	{
		//Simply return a Model w/an attribute named 
		//message and return a view named home using a string
		model.addAttribute("title", "Cloud Computing Testing");
		model.addAttribute("welcomeMessage", "Welcome");
		return "indexPostLogin";
	}
}
