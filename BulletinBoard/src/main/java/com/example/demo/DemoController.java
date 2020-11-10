package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
		return mav;
	}

	/* 編集画面への遷移 */
	@GetMapping("/edit")
	ModelAndView edit(@RequestParam(name = "id") int id) {
		ModelAndView mav = new ModelAndView();
		BulletinBoard bbs = bbsrepos.findById(id);
		mav.addObject("formModel", bbs);
		mav.setViewName("bbs/new");
		return mav;
	}

	/* 詳細画面への遷移 */
	@GetMapping("/bbs/show/{id}")
	ModelAndView show(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		BulletinBoard bbs = bbsrepos.findById(id);
		mav.addObject("data", bbs);
		mav.setViewName("bbs/show");
		return mav;
	}

	/* 更新処理 */
	@PostMapping("/create")
	@Transactional(readOnly = false)
	public ModelAndView save(@ModelAttribute("formModel") BulletinBoard bbs) {
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
		User user1 = new User();
		user1.setUsername("demo");
		user1.setEncodedPassword("7506c69384e000e6abb1de01165788de9f450cc69b0be847636d37c6278cefa69a042b40092a2d64");
		userrepos.saveAndFlush(user1);

		BulletinBoard bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その１");
		bbs1.setContent("こんにちわ‼︎");
		bbs1.setCreateUser(user1.getUsername());
		bbsrepos.saveAndFlush(bbs1);

		bbs1 = new BulletinBoard();
		bbs1.setCreateDate(DateToString(new Date()));
		bbs1.setTitle("挨拶その2");
		bbs1.setContent("おはようございます‼︎");
		bbs1.setCreateUser(user1.getUsername());
		bbsrepos.saveAndFlush(bbs1);
	}

	private String DateToString(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		System.out.print(sdf1.format(date));
		return sdf1.format(date);
	}

}
