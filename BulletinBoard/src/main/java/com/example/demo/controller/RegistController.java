package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.form.MemberRegistrationForm;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RegisterMemberService;

@Controller
public class RegistController {

	@Autowired
	private RegisterMemberService registMemberService;
	@Autowired
	private UserRepository u_repos;
	@Autowired
	private MailSender sender;

	@GetMapping("/RegistrationForm")
	public String showRegistrationForm(Model model) {
		System.out.println("registration form get controller.");
		model.addAttribute(new MemberRegistrationForm());
		return "registration/RegistrationForm";
	}

	@PostMapping("/Register")
	public ModelAndView registerUser(@ModelAttribute MemberRegistrationForm memberRegistrationForm) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("username", memberRegistrationForm.getUserName());
		mav.setViewName("registration/Result");

		// パスワードとパスワード（再入力）が異なる場合はエラー
		if (!memberRegistrationForm.getPassword().equals(memberRegistrationForm.getPasswordRe())) {
			mav.setViewName("redirect:/RegistrationForm");
			System.out.println("password error.");
			return mav;
		}

		if (!u_repos.existsByUsername(memberRegistrationForm.getUserName())) {

			User user = new User();
			String uuid = UUID.randomUUID().toString();

			user.setUsername(memberRegistrationForm.getUserName());
			user.setEncodedPassword(memberRegistrationForm.getPassword());
			user.setMailAddress(memberRegistrationForm.getMailAddress());
			user.setUuid(uuid);
			user.setActivate(false);

			System.out.println("username:" + user.getUsername() + "\n" + "activete? : " + user.isActivate());

			// パスワードをハッシュ化して保存する
			registMemberService.registerMember(user);

			// 有効化メールを送信する
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("test@mail.com");
			msg.setTo(memberRegistrationForm.getMailAddress());
			msg.setSubject("アカウント有効化について<BBS List>");
			msg.setText(memberRegistrationForm.getUserName() + "さん！" + "\n" + "以下のリンクにアクセスしてアカウントを認証してください。" + "\n"
					+ "http://localhost:8080/" + "validate" + "?id=" + uuid);
			sender.send(msg);

		}

		return mav;
	}

}
