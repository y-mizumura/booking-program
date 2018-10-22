package latte.app.controller.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import latte.app.controller.K0102_EventMngController;
import latte.domain.model.Event;
import latte.domain.service.EntryService;
import latte.domain.service.EventService;
import latte.domain.service.LoginUserDetails;

@Component
public class K0102_EventMngHelper {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EntryService entryService;
	
	/**
	 * 参加受付状態を更新
	 * 
	 * @param eventId
	 * @param userDetails
	 */
	public void updateUkeState(Integer eventId, LoginUserDetails userDetails){
		// イベントオブジェクト取得
		Event event = eventService.findByEventId(eventId);

		// 受付状態の切り替え
		if (event.isUkeChu()) {
			event.setUkeState("受付終了");
		} else {
			event.setUkeState("受付中");
		}
		event.setSysUpdateItems(K0102_EventMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		eventService.regEvent(event);
	}
	
	/**
	 * 削除処理
	 * （イベント参加情報もすべて削除）
	 * 
	 * @param eventId
	 */
	public void delete (Integer eventId) {
		// イベント参加情報の削除
		entryService.deleteEntryByEventId(eventId);
		
		// イベントの削除
		eventService.deleteEvent(eventId);
	}
}
