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

import latte.app.controller.helper.M0102_MemberMngHelper;
import latte.app.form.M0102_MemberMngForm;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_MEMBER')")
@RequestMapping("M0102")
public class M0102_MemberMngController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0102";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private M0102_MemberMngHelper helper;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public M0102_MemberMngForm setUpForm() {
		M0102_MemberMngForm form = new M0102_MemberMngForm();
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
	public String showNewMode(@ModelAttribute("M0102_MemberMngForm") M0102_MemberMngForm form, Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.M0102_MemberMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(0, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(0, form, model, false);
		}
		
		return "mobile/M0102_MemberMng";
	}

	/**
	 * 画面表示（更新）
	 * 
	 * @param form
	 * @param memberId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="select/{memberId}", method = RequestMethod.GET)
	public String showUpdateMode(@ModelAttribute("M0102_MemberMngForm") M0102_MemberMngForm form, @PathVariable("memberId") Integer memberId, Model model) {

		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.M0102_MemberMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(memberId, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(memberId, form, model, false);
		}

		return "mobile/M0102_MemberMng";
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
	@RequestMapping(value="submit/{memberId}", method = RequestMethod.POST, params = "register")
	public String register(
			@ModelAttribute("M0102_MemberMngForm") @Validated(Register.class) M0102_MemberMngForm form,
			BindingResult bindingResult, 
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			RedirectAttributes redirectAttributes) {

		// 初期化
		Integer memberId = 0;
		
		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);

			// 画面表示（新規）に遷移
			return "redirect:/M0102";
		}

		try {
			// 登録処理
			memberId = helper.register(form, userDetails);
			
			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0102_success_message_regist", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（新規）に遷移
			return "redirect:/M0102";
		}

		// メンバー照会画面を表示
		return "redirect:/M0103/select/" + memberId;
	}

	/**
	 * 更新ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param memberId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="submit/{memberId}", method = RequestMethod.POST, params = "update")
	public String update(
			@ModelAttribute("M0102_MemberMngForm") @Validated(Update.class) M0102_MemberMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			@PathVariable("memberId") Integer memberId,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);

			// 画面表示（更新）に遷移
			return "redirect:/M0102/select/" + memberId;
		}

		try {
			// 更新処理
			helper.update(form, userDetails, memberId);

			// 更新成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0102_success_message_update", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（更新）に遷移
			return "redirect:/M0102/select/" + memberId;
		}

		// メンバー照会画面を表示
		return "redirect:/M0103/select/" + memberId;
	}

}
