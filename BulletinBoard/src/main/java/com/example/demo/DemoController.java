package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

	@Autowired
	BulletinBoardRepository bbsrepos;
	@Autowired
	UserRepository userrepos;
	@Autowired
	DivisionRepository d_repos;

	/* 一覧画面への遷移 */
	@GetMapping
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List<BulletinBoard> list = bbsrepos.findAll();
		mav.setViewName("bbs/list");
		mav.addObject("data", list);
		return mav;
	}

	/* 新規画面への遷移 */
	@GetMapping("/add")
	ModelAndView add() {
		ModelAndView mav = new ModelAndView();
		BulletinBoard bbs = new BulletinBoard();
		mav.addObject("formModel", bbs);
		mav.setViewName("bbs/new");
		// 分類テーブルの読み込み
		mav.addObject("lists", d_repos.findAll());
		return mav;
	}

	/* 編集画面への遷移 */
	@GetMapping("/edit")
	ModelAndView edit(@RequestParam(name = "id") int id) {
		ModelAndView mav = new ModelAndView();
		BulletinBoard bbs = bbsrepos.findById(id);
		mav.addObject("formModel", bbs);
		mav.setViewName("bbs/new");
		// 分類テーブルの読み込み
		mav.addObject("lists", d_repos.findAll());
		return mav;
	}

	/* 詳細画面への遷移 */
	@GetMapping("/bbs/show/{id}")
	ModelAndView show(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		BulletinBoard bbs = bbsrepos.findById(id);
		mav.addObject("data", bbs);
		mav.setViewName("bbs/show");
		// 分類テーブルの読み込み
		Division div = bbs.getDivision();
		mav.addObject("div", div);
		return mav;
	}

	/* 作成・更新処理 */
	@PostMapping("/create")
	@Transactional(readOnly = false)
	public ModelAndView save(@ModelAttribute("formModel") @Validated BulletinBoard bbs, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("lists", d_repos.findAll());
			mav.setViewName("bbs/new");
			return mav;
		}
		bbs.setCreateDate(DateToString(new Date()));
		bbsrepos.saveAndFlush(bbs);
		/**
		 * Todo. 作成者の設定（ログインユーザーを自動入力すること）
		 */
		return new ModelAndView("redirect:/");
	}

	/* 削除処理 */
	@PostMapping("/delete")
	@Transactional(readOnly = false)
	public ModelAndView delete(@RequestParam(name = "id") int id) {
		bbsrepos.deleteById(id);
		return new ModelAndView("redirect:/");
	}

	/* 初期データ作成 */
	// Todo. 初期データ作成クラスを作れないか？
	@PostConstruct
	public void init() {
		// 分類テーブル初期データ
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
		user1.setEncodedPassword("7506c69384e000e6abb1de01165788de9f450cc69b0be847636d37c6278cefa69a042b40092a2d64");
		userrepos.saveAndFlush(user1);

		// 掲示板テーブル初期データ
		BulletinBoard bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その１");
		bbs1.setContent("こんにちわ‼︎");
		bbs1.setCreateUser(user1);
		bbs1.setDivision(div1);
		bbsrepos.saveAndFlush(bbs1);

		bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その2");
		bbs1.setContent("おはようございます‼︎");
		bbs1.setCreateUser(user1);
		bbs1.setDivision(div1);
		bbsrepos.saveAndFlush(bbs1);

	}

	private String DateToString(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		return sdf1.format(date);
	}

}
