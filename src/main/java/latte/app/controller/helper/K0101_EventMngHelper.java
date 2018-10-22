package latte.app.controller.helper;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.K0101_EventMngController;
import latte.app.form.K0101_EventMngForm;
import latte.domain.model.Location;
import latte.domain.model.Event;
import latte.domain.service.LocationService;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.EventService;

@Component
public class K0101_EventMngHelper {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * Viewの初期化
	 * 
	 * @param eventId
	 * @param form
	 * @param model
	 * @param isRedirect
	 */
	public void initView(Integer eventId, K0101_EventMngForm form, Model model, boolean isRedirect) {
		
		// イベントオブジェクト生成
		Event event = eventService.findByEventId(eventId);

		// 更新の場合、フォームにイベント情報を設定
		if (event != null) {
			form.setEventInfo(event);
		} else {
			form.init();
		}

		List<Location> location_list = locationService.findAll();
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2).collect(Collectors.toList());
		
		// モデルに追加
		model.addAttribute("eventId", eventId);
		model.addAttribute("location_list", location_list);
		model.addAttribute("timeList", timeList);
		if (isRedirect) {
			// 入力エラーでリダイレクトの場合、formを設定しない
		} else {
			model.addAttribute("K0101_EventMngForm", form);
		}
	}
	
	/**
	 * 登録処理
	 * 
	 * @param form
	 * @param userDetails
	 */
	public void register(K0101_EventMngForm form, LoginUserDetails userDetails) {
		
		// 場所情報オブジェクト作成
		Location location = locationService.findByLocationId(form.getLocationId());
		
		// イベントオブジェクト作成
		Event event = new Event();
		event.setTitle(form.getTitle());
		event.setLocation(location);
		event.setEventDate(form.getEventDate().toLocalDate());
		event.setStartTime(form.getStartTime());
		event.setEndTime(form.getEndTime());
		event.setUkeState("受付中");
		event.setMemo(form.getMemo());
		event.setSysCreateItems(K0101_EventMngController.FUNCTION_ID, userDetails.getUsername());
		
		// 登録処理
		eventService.regEvent(event);
	}

	/**
	 * 更新処理
	 * 
	 * @param form
	 * @param userDetails
	 * @param eventId
	 */
	public void update(K0101_EventMngForm form, LoginUserDetails userDetails, Integer eventId) {
		
		// 場所情報オブジェクト作成
		Location location = locationService.findByLocationId(form.getLocationId());
		
		// イベントオブジェクト作成
		Event event = eventService.findByEventId(eventId);
		event.setTitle(form.getTitle());
		event.setLocation(location);
		event.setEventDate(form.getEventDate().toLocalDate());
		event.setStartTime(form.getStartTime());
		event.setEndTime(form.getEndTime());
		event.setMemo(form.getMemo());
		event.setSysUpdateItems(K0101_EventMngController.FUNCTION_ID, userDetails.getUsername());

		// 更新処理
		eventService.updateEvent(event, K0101_EventMngController.FUNCTION_ID, userDetails.getUsername());
	}
	
}
