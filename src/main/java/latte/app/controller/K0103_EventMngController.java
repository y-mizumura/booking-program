package latte.app.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import latte.app.controller.helper.K0103_EventMngHelper;
import latte.domain.exception.BusinessLogicException;
import latte.domain.model.Entry;
import latte.domain.service.LoginUserDetails;

@Controller
@PreAuthorize("hasAuthority('AUTH_EVENT')")
@RequestMapping("/K0103")
public class K0103_EventMngController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "K0103";

	@Autowired
	private MessageSource ms;
	
	@Autowired
	private K0103_EventMngHelper helper;
	
	/**
	 * 参加メンバー画面　表示
	 * 
	 * @param eventId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "select/{eventId}", method = {RequestMethod.GET,RequestMethod.POST})
	public String show(@PathVariable("eventId") Integer eventId, Model model) {
		
		// Viewの初期化
		helper.initView(eventId, model);
		
		return "mobile/K0103_EventMng";
	}
	
	/**
	 * 参加費を計算
	 * 
	 * @param eventId
	 * @param userDetails
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "submit/{eventId}", method = RequestMethod.GET)
	public String calcFntryFee(
			@PathVariable("eventId") Integer eventId,
			@AuthenticationPrincipal LoginUserDetails userDetails,
			RedirectAttributes redirectAttributes) {
		
		try {
			// 参加費を計算
			helper.calcEntryFee(eventId, userDetails);

			// 登録成功メッセージを表示
			redirectAttributes.addFlashAttribute("success_message", ms.getMessage("K0103_success_message_calc", null, Locale.JAPAN));

		} catch (BusinessLogicException e) {
			// エラーメッセージをモデルに追加
			redirectAttributes.addFlashAttribute("top_error", e.getMessage());

			return "redirect:/K0103/select/" + eventId;
		}
				
		return "redirect:/K0103/select/" + eventId;
	}
	
	/**
	 * Ajax
	 * 参加費を更新
	 * 
	 * @param entryId
	 * @param entryFee
	 * @param userDetails
	 * @return
	 */
	@RequestMapping(value="changeEntryFee", method=RequestMethod.GET)
	@ResponseBody
	public String updateEntryFee(String entryId, String entryFee, @AuthenticationPrincipal LoginUserDetails userDetails) {
		
		String resultMessage = "";
		try {
			// 参加費を更新
			Entry entry = helper.updateEntryFee(Integer.parseInt(entryId), Integer.parseInt(entryFee), userDetails);
			resultMessage = ms.getMessage("K0103_success_message_update", new String[]{entry.getMember().getName(), entryFee}, Locale.JAPAN);
		} catch (BusinessLogicException e) {
			resultMessage = ms.getMessage("K0103_error_message_update", null, Locale.JAPAN);
		}
		
		Gson gson = new Gson();
		String result = gson.toJson(resultMessage);
		
		return result;
		
	}
	
	/**
	 * Ajax
	 * 清算済みフラグを更新
	 * 
	 * @param entryId
	 * @param userDetails
	 * @return
	 */
	@RequestMapping(value="changeFlg", method=RequestMethod.GET)
	@ResponseBody
	public String updateSeisanzumiFlg(String entryId, @AuthenticationPrincipal LoginUserDetails userDetails) {
		
		// 初期化
		int newSeisanzumiFlg = -1;
		
		try {
			// 清算済みフラグを更新
			newSeisanzumiFlg = helper.updateSeisanzumiFlg(Integer.parseInt(entryId), userDetails);

		} catch (BusinessLogicException e) {}
		
		Gson gson = new Gson();
		String result = gson.toJson(newSeisanzumiFlg);
		
		return result;
		
	}
}
