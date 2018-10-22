package latte.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.model.Entry;
import latte.domain.model.Event;
import latte.domain.model.EventEntry;
import latte.domain.model.Member;
import latte.domain.repository.EntryRepository;
import latte.domain.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EntryRepository entryRepository;
	
	/**
	 * 【取得】
	 * イベント情報を取得＜アクティブ＞
	 * 現在日付の5日前より後のイベントのみ（5日前を含む）
	 * 引数のメンバーのイベント参加情報をセットにして返却
	 * 
	 * @param member
	 * @param displayDate
	 * @return
	 */
	public List<EventEntry> findActiveEventEntry(Member member, LocalDate displayDate) {
		List<Event> eventList = eventRepository.findActiveEvent(displayDate);
		List<EventEntry> eventEntryList = new ArrayList<EventEntry>();
		Entry en;
		EventEntry ee;
		for(Event ev : eventList){
			en = entryRepository.findByEventIdAndMemberId(ev.getEventId(), member.getMemberId());
			ee = new EventEntry(ev, en);
			eventEntryList.add(ee);
		}
		return eventEntryList;
	}
	
	/**
	 * 【取得】
	 * イベント情報を取得＜非アクティブ＞
	 * 現在日付の5日前より前のみ（5日前は含まない）
	 * ＋イベント実施年月で検索
	 * 引数のメンバーのイベント参加情報をセットにして返却
	 * 
	 * @param member
	 * @param targetYearMonth
	 * @param hiddenDate
	 * @return
	 */
	public List<EventEntry> findInactiveEventEntry(Member member, LocalDate targetYearMonth, LocalDate hiddenDate) {
		List<Event> eventList = eventRepository.findInactiveEvent(targetYearMonth, targetYearMonth.plusMonths(1), hiddenDate);
		List<EventEntry> eventEntryList = new ArrayList<EventEntry>();
		Entry en;
		EventEntry ee;
		for(Event ev : eventList){
			en = entryRepository.findByEventIdAndMemberId(ev.getEventId(), member.getMemberId());
			ee = new EventEntry(ev, en);
			eventEntryList.add(ee);
		}
		return eventEntryList;
	}
	
	/**
	 * 【取得】
	 * イベント情報を1件（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public Event findByEventId(Integer eventId) {
		return eventRepository.findOne(eventId);
	}
	
	/**
	 * 【登録】　【更新】
	 * イベント情報1件
	 * 
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void regEvent(Event event) {
		// タイトルの設定
		if (event.getTitle()==null || event.getTitle().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d(E)");
			String date = event.getEventDate().format(formatter);
			String time = event.getStartTime().toString();
			event.setTitle(date + " " + time + "～");
		}
				
		// データの登録
		eventRepository.save(event);
	}
	
	/**
	 * 【更新】
	 * イベント情報1件
	 * イベント途中参加者が存在する場合
	 * 途中参加の参加時間がイベント時間外にならないように調整
	 * 
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEvent(Event event, String functionID, String userName) {
		// タイトルの設定
		if (event.getTitle()==null || event.getTitle().equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d(E)");
			String date = event.getEventDate().format(formatter);
			String time = event.getStartTime().toString();
			event.setTitle(date + " " + time + "～");
		}
		
		// イベント参加者が存在する場合
		// 参加または途中参加の参加時間がイベント時間外にならないように調整
		List<Entry> sankaList = entryRepository.findSankaByEventId(event.getEventId());
		if (!sankaList.isEmpty()) {
			boolean changFlg = false;
			for (Entry entry :sankaList) {
				
				// （1） 「イベント参加開始時間」が「イベント開始時間」の前になる場合、
				//    「イベント参加開始時間」を「イベント開始時間」に設定
				//    参加費・清算済みフラグをNullで更新
				if (entry.getEntryStartTime().isBefore(event.getStartTime())) {
					changFlg = true;
					entry.setEntryStartTime(event.getStartTime());
					entry.setEntryFee(null);
					entry.setSeisanzumiFlg(null);
				}
				
				// （2） 「イベント参加終了時間」が「イベント終了時間」の後になる場合、
				//    「イベント参加終了時間」を「イベント終了時間」に設定
				//    参加費・清算済みフラグをNullで更新
				if (entry.getEntryEndTime().isAfter(event.getEndTime())) {
					changFlg = true;
					entry.setEntryEndTime(event.getEndTime());
					entry.setEntryFee(null);
					entry.setSeisanzumiFlg(null);
				}
				
				// （3） イベント参加時間に変更がある場合、整合性をチェック
				if (changFlg) {
					// イベント参加開始時間 と イベント参加終了時間の差が、+1時間以上あること
					long diff = ChronoUnit.HOURS.between(entry.getEntryStartTime(), entry.getEntryEndTime());
					if (diff < 1) {
						// 1時間未満の場合、「考え中」として更新
						entry.setEntryKubun("考え中");
						entry.setEntryStartTime(null);
						entry.setEntryEndTime(null);
						entry.setEntryFee(null);
						entry.setSeisanzumiFlg(null);
					}
					// DB更新
					entry.setSysUpdateItems(functionID, userName);
					entryRepository.save(entry);
				}
				changFlg = false;
			}
		}
				
		// データの登録
		eventRepository.save(event);
	}
	
	/**
	 * 【削除】
	 * イベント情報1件
	 * 
	 * @param eventId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEvent(Integer eventId) {
		eventRepository.delete(eventId);
	}
}
