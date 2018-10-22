package latte.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import latte.app.controller.helper.C0901_ChangePWHelper;
import latte.app.form.C0901_ChangePWForm;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@RequestMapping("C0901")
public class C0901_ChangePWController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "C0901";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private C0901_ChangePWHelper helper;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public C0901_ChangePWForm setUpForm() {
		C0901_ChangePWForm form = new C0901_ChangePWForm();
		return form;
	}

	/**
	 * 画面表示
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String show(@ModelAttribute("C0901_ChangePWForm") C0901_ChangePWForm form, Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.C0901_ChangePWForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
		} else {
			model.addAttribute("C0901_ChangePWForm", form);
		}
		
		return "mobile/C0901_ChangePW";
	}

	/**
	 * パスワード更新
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="submit", method = RequestMethod.POST, params = "update")
	public String update(
			@ModelAttribute("C0901_ChangePWForm") @Validated C0901_ChangePWForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// リダイレクト
			return "redirect:/C0901";
		}

		try {
			// PWの更新処理
			helper.updatePW(form, userDetails);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("C0901_success_message", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());
			
			// リダイレクト
			return "redirect:/C0901";
		}

		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}
}
