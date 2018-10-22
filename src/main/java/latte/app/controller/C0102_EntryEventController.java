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

import latte.app.controller.helper.C0102_EntryEventHelper;
import latte.app.form.C0102_EntryEventForm;
import latte.app.validator.type.SpecifiedTime;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@RequestMapping("/C0102")
public class C0102_EntryEventController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "C0102";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private C0102_EntryEventHelper helper;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public C0102_EntryEventForm setUpForm() {
		C0102_EntryEventForm form = new C0102_EntryEventForm();
		return form;
	}
	
	/**
	 * イベント情報を表示（通常モード）
	 * 
	 * @param form
	 * @param eventId
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select/event/{eventId}", method = RequestMethod.GET)
	public String showEventDataApply(
			@ModelAttribute("C0102_EntryEventForm") C0102_EntryEventForm form,
			@PathVariable("eventId") Integer eventId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.C0102_EntryEventForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（リダイレクト）
			helper.initView(eventId, userDetails.getUser().getMember().getMemberId(), "apply", form, model, true);
		} else {
			// Viewの初期化（初期表示）
			helper.initView(eventId, userDetails.getUser().getMember().getMemberId(), "apply", form, model, false);
		}
		
		return "mobile/C0102_EntryEvent";
	}
	
	/**
	 * イベント情報を表示（管理者編集モード）
	 * 
	 * @param form
	 * @param eventId
	 * @param memberId
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select/event/{eventId}/{memberId}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AUTH_EVENT')")
	public String showEventDataAdminEdit(
			@ModelAttribute("C0102_EntryEventForm") C0102_EntryEventForm form,
			@PathVariable("eventId") Integer eventId,
			@PathVariable("memberId") Integer memberId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			Model model) {
		
		// 画面表示時に入力エラーが存在する場合
		if (model.asMap().containsKey("bindingResult")) {
			// Thtmleafに渡す、入力エラーのキー項目
			String key = "org.springframework.validation.BindingResult.C0102_EntryEventForm";
			model.addAttribute(key, model.asMap().get("bindingResult"));
			
			// Viewの初期化（管理者ユーザ／リダイレクト）
			helper.initView(eventId, memberId, "edit", form, model, true);
		} else {
			// Viewの初期化（管理者ユーザ／初期表示）
			helper.initView(eventId, memberId, "edit", form, model, false);
		}
		
		return "mobile/C0102_EntryEvent";
	}
	
	/**
	 * 申し込みボタン押下時（通常モード）
	 * 参加区分 ： 途中参加
	 *  
	 * @param form
	 * @param bindingResult
	 * @param eventId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST, params = {"apply", "entryKubun=途中参加"})
	public String applySpecifiedTime(
			@ModelAttribute("C0102_EntryEventForm") @Validated(SpecifiedTime.class) C0102_EntryEventForm form,
			BindingResult bindingResult,
			@PathVariable("eventId") Integer eventId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {
		
		return apply(form, bindingResult, eventId, userDetails, redirectAttributes);
	}
	
	/**
	 * 編集ボタン押下時（管理者編集モード）
	 * 参加区分 ： 途中参加
	 * 
	 * @param form
	 * @param bindingResult
	 * @param eventId
	 * @param memberId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}/{memberId}", method = RequestMethod.POST, params = {"edit", "entryKubun=途中参加"})
	public String editSpecifiedTime(
			@ModelAttribute("C0102_EntryEventForm") @Validated(SpecifiedTime.class) C0102_EntryEventForm form,
			BindingResult bindingResult,
			@PathVariable("eventId") Integer eventId,
			@PathVariable("memberId") Integer memberId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {
		
		return edit(form, bindingResult, eventId, memberId, userDetails, redirectAttributes);
	}
	
	/**
	 * 申し込みボタン押下時（通常モード）
	 * 
	 * @param form
	 * @param bindingResult
	 * @param eventId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST)
	public String apply(
			@ModelAttribute("C0102_EntryEventForm") @Validated C0102_EntryEventForm form,
			BindingResult bindingResult,
			@PathVariable("eventId") Integer eventId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {

		// メンバーID取得
		Integer memberId = userDetails.getUser().getMember().getMemberId();
		
		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);

			// 画面表示（通常モード）に遷移
			return "redirect:/C0102/select/event/" + eventId;
		}

		try {
			// 申込処理
			helper.confirm(eventId, memberId, form, userDetails);
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("C0102_success_message_apply", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（通常モード）に遷移
			return "redirect:/C0102/select/event/" + eventId;
		}
		
		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}
	
	/**
	 * 編集ボタン押下時（管理者編集モード）
	 * 
	 * @param form
	 * @param bindingResult
	 * @param eventId
	 * @param memberId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}/{memberId}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AUTH_EVENT')")
	public String edit(
			@ModelAttribute("C0102_EntryEventForm") @Validated C0102_EntryEventForm form,
			BindingResult bindingResult,
			@PathVariable("eventId") Integer eventId,
			@PathVariable("memberId") Integer memberId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {
		
		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			redirectAttributes.addFlashAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
			
			// 画面表示（管理者編集モード）に遷移
			return "redirect:/C0102/select/event/" + eventId + "/" + memberId;
		}

		try {
			// 申込処理
			helper.confirm(eventId, memberId, form, userDetails);
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("C0102_success_message_edit", null, Locale.JAPAN));
			
		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// 画面表示（管理者編集モード）に遷移
			return "redirect:/C0102/select/event/" + eventId + "/" + memberId;
		}
		
		return "redirect:/K0103/select/" + eventId;
	}
}
