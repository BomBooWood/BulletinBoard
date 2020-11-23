package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class RegisterMemberService {
	// @Autowired
	// RegisterMemberMapper registMemberMapper;

	@Autowired
	UserRepository u_repos;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 会員情報をDBに登録
	 */
	public void registerMember(User user) {
		// パスワードをハッシュ化して、insertMemberInfo()に渡すオブジェクトにセット。
		user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));

		u_repos.save(user);
	}

}
