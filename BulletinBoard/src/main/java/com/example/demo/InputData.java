package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.BulletinBoard;
import com.example.demo.entity.Division;
import com.example.demo.entity.User;
import com.example.demo.repository.BulletinBoardRepository;
import com.example.demo.repository.DivisionRepository;
import com.example.demo.repository.UserRepository;

public class InputData {

	@Autowired
	BulletinBoardRepository b_repos;
	@Autowired
	UserRepository u_repos;
	@Autowired
	DivisionRepository d_repos;
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostConstruct
	public void insertUser() {

		Division div1 = new Division();
		div1.setDivisionId(1);
		div1.setName("通達/連絡");
		d_repos.saveAndFlush(div1);
		div1 = new Division();
		div1.setDivisionId(2);
		div1.setName("会議開催について");
		d_repos.saveAndFlush(div1);

		div1 = new Division();
		div1.setDivisionId(3);
		div1.setName("スケジュール");
		d_repos.saveAndFlush(div1);

		div1 = new Division();
		div1.setDivisionId(4);
		div1.setName("イベント");
		d_repos.saveAndFlush(div1);

		div1 = new Division();
		div1.setDivisionId(5);
		div1.setName("その他");
		d_repos.saveAndFlush(div1);

		// ユーザーテーブル初期データ
		User user1 = new User();
		user1.setUsername("demo");
		user1.setEncodedPassword(passwordEncoder.encode("demo"));
		user1.setActivate(true);
		u_repos.saveAndFlush(user1);

		// 掲示板テーブル初期データ
		BulletinBoard bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その１");
		bbs1.setContent("こんにちわ‼︎");
		bbs1.setCreateUser(user1);
		bbs1.setDivision(div1);
		b_repos.saveAndFlush(bbs1);

		bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その2");
		bbs1.setContent("おはようございます‼︎");
		bbs1.setCreateUser(user1);
		bbs1.setDivision(div1);
		b_repos.saveAndFlush(bbs1);
	}

	private String DateToString(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		return sdf1.format(date);
	}

}
