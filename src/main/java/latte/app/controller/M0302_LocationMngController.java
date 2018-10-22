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

import latte.app.controller.helper.M0302_LocationMngHelper;
import latte.app.form.M0302_LocationMngForm;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_LOCATION')")
@RequestMapping("/M0302")
public class M0302_LocationMngController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0302";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private M0302_LocationMngHelper helper;

	@ModelAttribute
	public M0302_LocationMngForm setUpForm() {
		M0302_LocationMngForm form = new M0302_LocationMngForm();
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
	public String showNewMode(@ModelAttribute("M0302_LocationMngForm") M0302_LocationMngForm form, Model model) {

		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.M0302_LocationMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(0, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(0, form, model, false);
		}

		return "mobile/M0302_LocationMng";
	}

	/**
	 * 画面表示（更新）
	 * 
	 * @param form
	 * @param locationId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select/{locationId}", method = RequestMethod.GET)
	public String showUpdateMode(@ModelAttribute("M0302_LocationMngForm") M0302_LocationMngForm form, @PathVariable("locationId") Integer locationId, Model model) {

		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.M0302_LocationMngForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(locationId, form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(locationId, form, model, false);
		}
		
		return "mobile/M0302_LocationMng";
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
	@RequestMapping(value = "submit/{locationId}", method = RequestMethod.POST, params = "register")
	public String register(
			@ModelAttribute("M0302_LocationMngForm") @Validated(Register.class) M0302_LocationMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// 画面表示（新規）に遷移
			return "redirect:/M0302";
		}

		try {
			// 登録処理
			helper.register(form, userDetails);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0302_success_message_regist", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（新規）に遷移
			return "redirect:/M0302";
		}

		// 場所一覧画面にリダイレクト
		return "redirect:/M0301";
	}

	/**
	 * 更新ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param locationId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{locationId}", method = RequestMethod.POST, params = "update")
	public String update(@ModelAttribute("M0302_LocationMngForm") @Validated(Update.class) M0302_LocationMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			@PathVariable("locationId") Integer locationId,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// 画面表示（更新）に遷移
			return "redirect:/M0302/select/" + locationId;
		}

		try {
			// 更新処理
			helper.update(form, userDetails, locationId);

			// 更新成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0302_success_message_update", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（更新）に遷移
			return "redirect:/M0302/select/" + locationId;
		}

		// 場所一覧画面にリダイレクト
		return "redirect:/M0301";
	}

	/**
	 * 削除ボタン押下時
	 * 
	 * @param form
	 * @param locationId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{locationId}", method = RequestMethod.POST, params = "delete")
	public String delete(@ModelAttribute("M0302_LocationMngForm") M0302_LocationMngForm form,
			@PathVariable("locationId") Integer locationId,
			RedirectAttributes redirectAttributes) {

		try {
			// 削除処理
			helper.delete(locationId);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("M0302_success_message_delete", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（更新）に遷移
			return "redirect:/M0302/select/" + locationId;
		}

		// 場所一覧画面にリダイレクト
		return "redirect:/M0301";
	}

}
