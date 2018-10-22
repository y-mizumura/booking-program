package latte.app.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import latte.app.validator.type.Delete;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.app.controller.helper.M0501_ChargeMngHelper;
import latte.app.form.M0501_ChargeMngForm;
import latte.domain.exception.BusinessLogicException;
import latte.domain.model.Charge;
import latte.domain.service.ChargeService;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_CHARGE')")
@RequestMapping("M0501")
public class M0501_ChargeMngController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0501";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private M0501_ChargeMngHelper helper;

	@Autowired
	private ChargeService chargeService;

	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public M0501_ChargeMngForm setUpForm() {
		M0501_ChargeMngForm form = new M0501_ChargeMngForm();
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
	public String show(@ModelAttribute("M0501_ChargeMngForm") M0501_ChargeMngForm form, Model model) {
		List<Charge> chargeList = chargeService.findAllCharge();
		model.addAttribute("charge_list", chargeList);
		model.addAttribute("M0501_ChargeMngForm", form);

		return "mobile/M0501_ChargeMng";
	}

	/**
	 * 登録ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = "register")
	public String register(
			@ModelAttribute("M0501_ChargeMngForm") @Validated(Register.class) M0501_ChargeMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			Model model) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			model.addAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "register");

			return show(form, model);
		}

		try {
			// 登録処理
			helper.register(form, userDetails);

			// 登録成功メッセージを表示
			model.addAttribute("success_message", ms.getMessage("M0501_success_message_regist", null, Locale.JAPAN));

			// 画面の初期化
			form.init();

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			model.addAttribute("top_error", e.getMessage());
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "register");

			return show(form, model);
		}

		// 料金画面を再表示
		return show(form, model);
	}

	/**
	 * 更新ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = "update")
	public String update(
			@ModelAttribute("M0501_ChargeMngForm") @Validated(Update.class) M0501_ChargeMngForm form,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			Model model) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			model.addAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "update");

			return show(form, model);
		}

		try {
			// 更新処理
			helper.update(form, userDetails);

			// 更新成功メッセージを表示
			model.addAttribute("success_message", ms.getMessage("M0501_success_message_update", null, Locale.JAPAN));

			// 画面の初期化
			form.init();

		} catch (BusinessLogicException e) {
			// 更新失敗エラーメッセージを追加
			model.addAttribute("top_error", e.getMessage());
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "update");

			return show(form, model);
		}

		// 料金画面を再表示
		return show(form, model);
	}

	/**
	 * 削除ボタン押下時
	 * 
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = "delete")
	public String delete(
			@ModelAttribute("M0501_ChargeMngForm") @Validated(Delete.class) M0501_ChargeMngForm form,
			BindingResult bindingResult,
			Model model) {

		// 入力エラーがあるか確認
		if (bindingResult.hasErrors()) {
			// エラーメッセージを追加
			model.addAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "delete");

			return show(form, model);
		}

		try {
			// 削除処理
			helper.delete(form);

			// 削除成功メッセージを表示
			model.addAttribute("success_message", ms.getMessage("M0501_success_message_delete", null, Locale.JAPAN));

			// 画面の初期化
			form.init();

		} catch (BusinessLogicException e) {
			// 更新失敗エラーメッセージを追加
			model.addAttribute("top_error", e.getMessage());
			// エラー対象処理をモデルに追加
			model.addAttribute("error_func", "delete");

			return show(form, model);
		}

		return show(form, model);
	}
}
