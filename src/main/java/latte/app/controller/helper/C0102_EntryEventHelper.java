package latte.app.controller.helper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.C0102_EntryEventController;
import latte.app.form.C0102_EntryEventForm;
import latte.domain.model.Member;
import latte.domain.model.Entry;
import latte.domain.model.Event;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.MemberService;
import latte.domain.service.EntryService;
import latte.domain.service.EventService;

@Component
public class C0102_EntryEventHelper {
	
	@Autowired
	private EntryService entryService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private MemberService memberService;
		
	/**
	 * Viewの初期化
	 * 
	 * @param eventId
	 * @param memberId
	 * @param mode
	 * @param form
	 * @param model
	 * @param isRedirect
	 */
	public void initView(Integer eventId, Integer memberId, String mode, C0102_EntryEventForm form, Model model, boolean isRedirect) {
		
		// イベントオブジェクト生成
		Event event = eventService.findByEventId(eventId);
		
		// 参加情報の取得
		Entry entry = entryService.findByEventIdAndMemberId(eventId, memberId);

		// 参加情報がnullの場合（新規申し込みの場合）
		if (entry == null) {
			form.init(event);
			model.addAttribute("isEntry", false);
		} else {
			form.setEntryInfo(entry);
			model.addAttribute("isEntry", true);
		}
		
		// リスト作成
		List<String> entryKubunList = new ArrayList<String>();
		entryKubunList.add("参加");
		entryKubunList.add("途中参加");
		entryKubunList.add("不参加");
		entryKubunList.add("考え中");
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2).collect(Collectors.toList());
		
		// エントリーリスト取得（通常モード限定）
		if (mode.equals("apply")) {
			List<Entry> sEntryList = entryService.findSankaByEventId(eventId);
			List<Entry> fEntryList = entryService.findFusankaByEventId(eventId);
			List<Entry> hEntryList = entryService.findKangaetyuByEventId(eventId);
			model.addAttribute("sEntryList", sEntryList);
			model.addAttribute("fEntryList", fEntryList);
			model.addAttribute("hEntryList", hEntryList);
		}
		
		// 編集対象のメンバー取得
		Member member = memberService.findByMemberId(memberId);
		
		// モデルに追加
		model.addAttribute("mode", mode);
		model.addAttribute("member", member);
		model.addAttribute("event", event);
		model.addAttribute("entry", entry);
		model.addAttribute("entryKubunList", entryKubunList);
		model.addAttribute("timeList", timeList);
		if (isRedirect) {
			// 入力エラーでリダイレクトの場合、formを設定しない
		} else {
			model.addAttribute("C0102_EntryEventForm", form);
		}
	}
	
	/**
	 * 確定処理
	 * 
	 * @param eventId
	 * @param memberId
	 * @param form
	 * @param userDetails
	 */
	public void confirm(Integer eventId, Integer memberId, C0102_EntryEventForm form, LoginUserDetails userDetails) {
		
		// イベントオブジェクト取得
		Event event = eventService.findByEventId(eventId);
		
		// メンバーオブジェクト取得
		Member member = memberService.findByMemberId(memberId);
		
		// イベント参加オブジェクト作成
		Entry entry = entryService.findByEventIdAndMemberId(eventId, memberId);
		if (entry == null) {
			// 新規登録の場合
			entry = new Entry();
			entry.setSysCreateItems(C0102_EntryEventController.FUNCTION_ID, userDetails.getUsername());
		} else {
			// 更新の場合 参加費および清算済みフラグを初期化
			entry.setEntryFee(null);
			entry.setSeisanzumiFlg(null);
			entry.setSysUpdateItems(C0102_EntryEventController.FUNCTION_ID, userDetails.getUsername());
		}
		
		// 参加時間を取得
		String entryKubun = form.getEntryKubun();
		LocalTime startTime = null;
		LocalTime endTime = null;
		if (entryKubun.equals("参加") ||  entryKubun.equals("途中参加")) {
			startTime = form.getStartTime();
			endTime = form.getEndTime();
		}
		
		// entryオブジェクト作成
		entry.setEvent(event);
		entry.setMember(member);
		entry.setEntryKubun(entryKubun);
		entry.setEntryStartTime(startTime);
		entry.setEntryEndTime(endTime);
		
		// 登録・更新処理
		entryService.regEntry(entry, event);
	}
	
}
