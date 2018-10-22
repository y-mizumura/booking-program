package latte.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import latte.app.form.S0001_SearchForm;
import latte.domain.model.Member;
import latte.domain.service.MemberService;

@Controller
@PreAuthorize("hasAuthority('AUTH_MEMBER')")
@RequestMapping("/M0101")
public class M0101_MemberMngController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0101";

	@Autowired
	private MemberService memberService;
	
	/**
	 * 検索フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public S0001_SearchForm setUpForm() {
		S0001_SearchForm form = new S0001_SearchForm();
		return form;
	}
	
	/**
	 * 画面表示
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String show(@ModelAttribute("S0001_SearchForm") S0001_SearchForm form, Model model) {
		
		// 検索フォーム追加
		model.addAttribute("S0001_SearchForm", form);
		
		// 一覧追加
		List<Member> memberList = memberService.findAll();
		model.addAttribute("memberList", memberList);
		
		return "mobile/M0101_MemberMng";
	}
	
	/**
	 * 検索ボタン押下時の処理
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="search", method = RequestMethod.GET)
	public String search(@ModelAttribute("S0001_SearchForm") S0001_SearchForm form, Model model) {
		
		// 検索フォーム追加
		model.addAttribute("S0001_SearchForm", form);
		
		// 一覧追加（keywordで名前を検索）
		List<Member> memberList = memberService.findByName(form.getKeyword());
		model.addAttribute("memberList", memberList);
				
		return "mobile/M0101_MemberMng";
	}

}
