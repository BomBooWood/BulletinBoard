package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class ActivateUserController {

	@Autowired
	UserRepository u_repo;

	@GetMapping("/validate")
	public ModelAndView validate(RedirectAttributes redirectAttributes, ModelAndView mav,
			@RequestParam("id") String uuid) {

		// ログイン画面のメッセージで使えそう ex) 認証が完了しましたor認証に失敗しました
		String isRegisterd = "false";

		boolean isExist = u_repo.existsByUuid(uuid);
		User user = u_repo.findByUuid(uuid);
		if (isExist) {
			isRegisterd = "true";
			user.setActivate(true);
			u_repo.saveAndFlush(user);
			System.out.println(user.getUsername());
			System.out.println("validate controller");
		}
		redirectAttributes.addFlashAttribute("isRegisterd", isRegisterd);
		redirectAttributes.addFlashAttribute("user", isRegisterd);
		mav.setViewName("redirect:/login");
		return mav;
	}

}
