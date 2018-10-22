package latte.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import latte.app.controller.helper.M0104_MemberMngHelper;
import latte.app.form.M0104_MemberMngForm;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_MEMBER')")
@RequestMapping("M0104")
public class M0104_MemberMngController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0104";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private M0104_MemberMngHelper helper;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public M0104_MemberMngForm setUpForm() {
		M0104_MemberMngForm form = new M0104_MemberMngForm();
		return form;
	}
	
	/**
	 * 画面表示
	 * 
	 * @param form
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="select/{memberId}", method = RequestMethod.GET)
	public String show(@ModelAttribute("M0104_MemberMngForm") M0104_MemberMngForm form, @PathVariable("memberId") Integer memberId, Model model) {

		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.M0104_MemberMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(memberId, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(memberId, form, model, false);
		}

		return "mobile/M0104_MemberMng";
	}
	
	/**
	 * 登録ボタン押下時
	 * 
	 * @param memberId
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="submit/{memberId}", method = RequestMethod.POST, params = "register")
	public String register(
			@PathVariable("memberId") Integer memberId,
			@ModelAttribute("M0104_MemberMngForm") @Validated(Register.class) M0104_MemberMngForm form,
			BindingResult bindingResult, 
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);

			// 画面表示
			return "redirect:/M0104/select/" + memberId;
		}

		try {
			// 登録処理
			helper.register(form, memberId, userDetails);
			
			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0104_success_message_regist", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());
			
			// 画面表示
			return "redirect:/M0104/select/" + memberId;
		}

		// メンバー照会画面にリダイレクト
		return "redirect:/M0103/select/" + memberId;
	}
	
	/**
	 * 更新ボタン押下時
	 * 
	 * @param memberId
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="submit/{memberId}", method = RequestMethod.POST, params = "update")
	public String update(
			@PathVariable("memberId") Integer memberId,
			@ModelAttribute("M0104_MemberMngForm") @Validated(Update.class) M0104_MemberMngForm form,
			BindingResult bindingResult, 
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// 画面表示
			return "redirect:/M0104/select/" + memberId;
		}

		try {
			// 登録処理
			helper.update(form, memberId, userDetails);
			
			// 更新成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0104_success_message_update", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示
			return "redirect:/M0104/select/" + memberId;
		}

		// メンバー照会画面にリダイレクト
		return "redirect:/M0103/select/" + memberId;
	}

}