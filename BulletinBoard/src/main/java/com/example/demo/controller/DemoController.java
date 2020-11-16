package com.example.demo.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.example.demo.entity.BulletinBoard;
import com.example.demo.entity.Division;
import com.example.demo.repository.BulletinBoardRepository;
import com.example.demo.repository.DivisionRepository;
import com.example.demo.repository.UserRepository;

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
	public ModelAndView list(Principal principal) {
		ModelAndView mav = new ModelAndView();
		List<BulletinBoard> list = bbsrepos.findAll();
		mav.setViewName("bbs/list");
		mav.addObject("data", list);
		mav.addObject("username", principal.getName());
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
		bbs.setCreateDate(DateToString(new Date()));
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("lists", d_repos.findAll());
			mav.setViewName("bbs/new");
			System.out.println(result);
			return mav;
		}
		bbsrepos.saveAndFlush(bbs);/**
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

	private String DateToString(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		return sdf1.format(date);
	}

}
