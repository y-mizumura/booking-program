package latte.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import latte.app.form.C0101_EntryEventForm;
import latte.domain.model.EventEntry;
import latte.domain.model.Member;
import latte.domain.model.User;
import latte.domain.service.EventService;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.UserService;

@Controller
@RequestMapping("/C0101")
public class C0101_EntryEventController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "C0101";
	
	@Autowired
	private MessageSource ms;
	
	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;
	
	/**
	 * フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public C0101_EntryEventForm setUpForm() {
		C0101_EntryEventForm form = new C0101_EntryEventForm();
		return form;
	}

	/**
	 * 画面表示
	 * 
	 * @param form
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String show(@ModelAttribute("C0101_EntryEventForm") C0101_EntryEventForm form, 
			@AuthenticationPrincipal LoginUserDetails userDetails, Model model) {
		
		// 表示可能な日付（イベント終了から5日以内）
		LocalDate currentDate = LocalDate.now();
		LocalDate displayDate = LocalDate.now().minusDays(5);
		
		// メンバー取得
		Member member = userDetails.getUser().getMember();
		
		// アクティブなイベントリスト（イベント終了から5日以内）
		List<EventEntry> eventEntryList = eventService.findActiveEventEntry(member, displayDate);
		
		// 過去のイベント用　検索条件（年月リスト）
		List<String> seachTargetList = new ArrayList<>();
		
		int systemStartYear  = Integer.parseInt(ms.getMessage("system_start_year", null, Locale.JAPAN));		
		int systemStartMonth = Integer.parseInt(ms.getMessage("system_start_month", null, Locale.JAPAN));
		
		// 「年」のループ
		for (int nen=systemStartYear; nen<currentDate.getYear()+1; nen++) {
			// 「月」のループ
			for (int tsuki=1; tsuki<13; tsuki++) {
				// システム開始年の場合、システム開始月までスキップ
				if (nen==systemStartYear && tsuki<systemStartMonth) {
					continue;
				}
				// 現在年月を超えた場合、ループ終了
				if (nen>=currentDate.getYear() && tsuki>currentDate.getMonthValue()) {
					break;
				}
				seachTargetList.add(ms.getMessage("C0101_seach_target_list", new Integer[]{nen, tsuki}, Locale.JAPAN));
			}
		}
		
		// 初期値＝「＜選択してください＞」を追加
		String initial_selected = ms.getMessage("C0101_seach_target_list_initial", null, Locale.JAPAN);
		seachTargetList.add(initial_selected);
		form.init(initial_selected);
		
		// モデルに追加
		model.addAttribute("eventEntryList", eventEntryList);
		model.addAttribute("seachTargetList", seachTargetList);
		model.addAttribute("C0101_EntryEventForm", form);
		return "mobile/C0101_EntryEvent";
	}
	
	/**
	 * Ajax
	 * 過去のイベント一覧を表示
	 * 
	 * @param searchTarget
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(String searchTarget, String userId, Model model) {
		
		// ユーザ取得
		User user = userService.findByUserId(userId);
		
		// メンバー取得
		Member member = user.getMember();
		
		// 検索対象年月取得
		Integer targetYear = Integer.parseInt(searchTarget.substring(0,4));
		Integer targetMonth = Integer.parseInt(searchTarget.substring(5,searchTarget.length()-1));	
		LocalDate targetYearMonth = LocalDate.of(targetYear, targetMonth, 1);
		
		// 非表示日取得（この日より前を表示）
		LocalDate hiddenDate = LocalDate.now().minusDays(5);
		
		// 表示するリストを作成
		List<EventEntry> searchList = eventService.findInactiveEventEntry(member, targetYearMonth, hiddenDate);
		model.addAttribute("searchList", searchList);
		
		return "mobile/C0101_EntryEvent::C0101_searchResult";
	}
}
