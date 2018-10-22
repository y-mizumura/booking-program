package latte.app.controller.helper;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.K0103_EventMngController;
import latte.domain.model.Entry;
import latte.domain.model.Event;
import latte.domain.model.Member;
import latte.domain.service.EntryService;
import latte.domain.service.EventService;
import latte.domain.service.LoginUserDetails;

@Component
public class K0103_EventMngHelper {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EntryService entryService;
	
	/**
	 * Viewの初期化
	 * 
	 * @param eventId
	 * @param model
	 */
	public void initView(Integer eventId, Model model) {
		// イベントオブジェクト生成
		Event event = eventService.findByEventId(eventId);

		// エントリーリスト取得
		List<Entry> sEntryList = entryService.findSankaByEventId(eventId);
		List<Entry> fEntryList = entryService.findFusankaByEventId(eventId);
		List<Entry> hEntryList = entryService.findKangaetyuByEventId(eventId);
		List<Member> mMemberList = entryService.findMikaitoByEventId(eventId);
		
		// モデルに追加
		model.addAttribute("event", event);
		model.addAttribute("sEntryList", sEntryList);
		model.addAttribute("fEntryList", fEntryList);
		model.addAttribute("hEntryList", hEntryList);
		model.addAttribute("mMemberList", mMemberList);
	}
	
	/**
	 * 参加費計算
	 * 
	 * @param eventId
	 * @param userDetails
	 */
	public void calcEntryFee(Integer eventId, LoginUserDetails userDetails) {
		
		// エントリーリスト取得
		List<Entry> entryList = entryService.findByEventId(eventId);
		
		for (Entry entry : entryList) {
			int entryFee = 0;
			if (entry.getEntryKubun().equals("途中参加") || entry.getEntryKubun().equals("参加") ) {
				// 参加者の1時間当たりの参加費を取得
				int parHourFee = entry.getMember().getCharge().getCharge();
				
				// 参加者の参加時間を取得
				LocalTime startTime = entry.getEntryStartTime();
				LocalTime endTime = entry.getEntryEndTime();
				
				 // 参加費を計算
				int hours = endTime.getHour() - startTime.getHour();
				int minutes = endTime.getMinute() - startTime.getMinute();
				double entryTime = hours + (minutes >= 30 ? 0.5 : 0);
				entryFee = (int) (entryTime * parHourFee);
			}
			
			// イベント参加オブジェクトを更新
			entry.setEntryFee(entryFee);
			entry.setSeisanzumiFlg(0);
			entry.setSysUpdateItems(K0103_EventMngController.FUNCTION_ID, userDetails.getUsername());
		}
		entryService.updateEntryList(entryList);
	}
	
	/**
	 * 参加費手動更新
	 * 
	 * @param entryId
	 * @param entryFee
	 * @param userDetails
	 * @return
	 */
	public Entry updateEntryFee(int entryId, int entryFee, LoginUserDetails userDetails) {
		
		// イベント参加情報取得
		Entry entry = entryService.findByEntryId(entryId);

		// イベント参加オブジェクトを更新
		// 清算済みフラグは、【未精算】に更新
		entry.setSeisanzumiFlg(0);
		entry.setEntryFee(entryFee);
		entry.setSysUpdateItems(K0103_EventMngController.FUNCTION_ID, userDetails.getUsername());
		entryService.updateEntry(entry);
		
		return entry;
	}
	
	/**
	 * 清算済みフラグ更新
	 * 
	 * @param entryId
	 * @param userDetails
	 * @return
	 */
	public int updateSeisanzumiFlg(int entryId, LoginUserDetails userDetails) {
		
		// イベント参加情報取得
		Entry entry = entryService.findByEntryId(entryId);

		int newSeisanzumiFlg = (entry.getSeisanzumiFlg()==1 ? 0 : 1);
		
		// イベント参加オブジェクトを更新
		entry.setSeisanzumiFlg(newSeisanzumiFlg);
		entry.setSysUpdateItems(K0103_EventMngController.FUNCTION_ID, userDetails.getUsername());
		entryService.updateEntry(entry);
		
		return newSeisanzumiFlg;
	}
	
}
