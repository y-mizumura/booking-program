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

import latte.app.controller.helper.K0101_EventMngHelper;
import latte.app.form.K0101_EventMngForm;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_EVENT')")
@RequestMapping("/K0101")
public class K0101_EventMngController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "K0101";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private K0101_EventMngHelper helper;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public K0101_EventMngForm setUpForm() {
		K0101_EventMngForm form = new K0101_EventMngForm();
		return form;
	}
	
	/**
	 * 画面表示（新規登録）
	 *  
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showNewMode(@ModelAttribute("K0101_EventMngForm") K0101_EventMngForm form, Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.K0101_EventMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(0, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(0, form, model, false);
		}
		
		return "mobile/K0101_EventMng";
	}
	
	/**
	 * 画面表示（更新）
	 * 
	 * @param form
	 * @param eventId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select/{eventId}", method = RequestMethod.GET)
	public String showUpdateMode(@ModelAttribute("K0101_EventMngForm") K0101_EventMngForm form,	@PathVariable("eventId") Integer eventId, Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.K0101_EventMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(eventId, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(eventId, form, model, false);
		}
		
		return "mobile/K0101_EventMng";
	}
	
	/**
	 * 登録ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST, params = "register")
	public String register(
			@ModelAttribute("K0101_EventMngForm") @Validated(Register.class) K0101_EventMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// リダイレクト
			return "redirect:/K0101";
		}

		try {
			// 登録処理
			helper.register(form, userDetails);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("K0101_success_message_regist", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// リダイレクト
			return "redirect:/K0101";
		}

		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}
	
	/**
	 * 更新ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param eventId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST, params = "update")
	public String update(
			@ModelAttribute("K0101_EventMngForm") @Validated(Update.class) K0101_EventMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			@PathVariable("eventId") Integer eventId,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);

			// リダイレクト
			return "redirect:/K0101/select/" + eventId;
		}

		try {
			// 更新処理
			helper.update(form, userDetails, eventId);

			// 更新成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("K0101_success_message_update", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// リダイレクト
			return "redirect:/K0101/select/" + eventId;
		}

		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}

}
