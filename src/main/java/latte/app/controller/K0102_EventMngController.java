package latte.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import latte.app.controller.helper.K0102_EventMngHelper;
import latte.domain.exception.BusinessLogicException;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_EVENT')")
@RequestMapping("/K0102")
public class K0102_EventMngController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "K0102";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private K0102_EventMngHelper helper;
	
	/**
	 * 受付状態の更新
	 * ・【受付中】の場合、　【受付終了】に更新
	 * ・【受付終了】の場合、【受付中】に更新
	 * 
	 * @param eventId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST, params = "changeUkeState")
	public String changeUkeState(@PathVariable("eventId") Integer eventId, @AuthenticationPrincipal LoginUserDetails userDetails, RedirectAttributes redirectAttributes) {
		
		try {
			// 受付状態の変更処理
			helper.updateUkeState(eventId, userDetails);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("K0102_success_message_change_uke_state", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// イベント一覧画面にリダイレクト
			return "redirect:/C0101";
		}
		
		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}
	
	/**
	 * イベント情報の削除
	 * （イベント参加情報もすべて削除）
	 * 
	 * @param eventId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.POST, params = "delete")
	public String delete(@PathVariable("eventId") Integer eventId, RedirectAttributes redirectAttributes) {
		
		try {
			// 削除処理
			helper.delete(eventId);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("K0102_success_message_delete", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			// イベント一覧画面にリダイレクト
			return "redirect:/C0101";
		}

		// イベント一覧画面にリダイレクト
		return "redirect:/C0101";
	}
	
}
