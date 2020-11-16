package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.form.MemberRegistrationForm;
import com.example.demo.service.RegisterMemberService;

@Controller
public class RegistController {

	@Autowired
	private RegisterMemberService registMemberService;

	@GetMapping("/RegistrationForm")
	public String showRegistrationForm(Model model) {

		model.addAttribute(new MemberRegistrationForm());
		return "registration/RegistrationForm";
	}

	@PostMapping("/Register")
	public String registerUser(@ModelAttribute MemberRegistrationForm memberRegistrationForm) {
		User user = new User();

		System.out.println("username:" + memberRegistrationForm.getUserName() + "\n" + "password:"
				+ memberRegistrationForm.getPassword());

		user.setUsername(memberRegistrationForm.getUserName());
		user.setEncodedPassword(memberRegistrationForm.getPassword());

		registMemberService.registerMember(user);
		return "registration/Result";
	}

}
