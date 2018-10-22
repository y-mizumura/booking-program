package latte.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import latte.domain.exception.BusinessLogicException;
import latte.domain.model.Member;
import latte.domain.service.MemberService;

@Controller
@PreAuthorize("hasAuthority('AUTH_MEMBER')")
@RequestMapping("M0103")
public class M0103_MemberMngController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0103";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private MemberService memberService;

	/**
	 * 画面表示
	 * 
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="select/{memberId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String show(@PathVariable("memberId") Integer memberId, Model model) {

		Member member = memberService.findByMemberId(memberId);

		model.addAttribute("member", member);

		return "mobile/M0103_MemberMng";
	}
	
	/**
	 * 削除ボタン押下時
	 * 
	 * @param memberId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="submit/{memberId}", method = RequestMethod.POST, params = "delete")
	public String delete(@PathVariable("memberId") Integer memberId, RedirectAttributes redirectAttributes) {

		try {
			// 削除処理
			memberService.deleteMember(memberId);
			
			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0103_success_message_delete", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());
			
			// 画面表示
			return "redirect:/M0103/select/" + memberId;
		}

		// メンバー管理画面にリダイレクト
		return "redirect:/M0101";
	}

}