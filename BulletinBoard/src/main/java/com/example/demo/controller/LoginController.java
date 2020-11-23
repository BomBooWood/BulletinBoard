package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;

@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView showLoginForm(Model model, @ModelAttribute("isRegisterd") String isRegisterd,
			@ModelAttribute("user") User user) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/Login");
		mav.addObject("isRegisterd", isRegisterd);
		mav.addObject("user", user);
		System.out.println("login get controller.");
		System.out.println(isRegisterd);
		return mav;
	}
}
