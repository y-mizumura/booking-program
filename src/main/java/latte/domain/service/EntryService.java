package latte.domain.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.exception.InvalidEntryTimeException;
import latte.domain.model.Entry;
import latte.domain.model.Event;
import latte.domain.model.Member;
import latte.domain.repository.EntryRepository;
import latte.domain.repository.MemberRepository;

@Service
public class EntryService {
	
	@Autowired
	private MessageSource msg;
	
	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	/**
	 * 【取得】
	 * イベント参加情報を1件取得（イベント参加ID）
	 * 
	 * @param entryId
	 * @return
	 */
	public Entry findByEntryId(Integer entryId) {
		return entryRepository.findOne(entryId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報を取得（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public List<Entry> findByEventId(Integer eventId) {
		return entryRepository.findByEventId(eventId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報を1件取得（イベントID＆メンバーID）
	 * 
	 * @param eventId
	 * @param memberId
	 * @return
	 */
	public Entry findByEventIdAndMemberId(Integer eventId, Integer memberId) {
		return entryRepository.findByEventIdAndMemberId(eventId, memberId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報一覧（参加・途中参加）を取得（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public List<Entry> findSankaByEventId(Integer eventId) {
		return entryRepository.findSankaByEventId(eventId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報一覧（不参加）を取得（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public List<Entry> findFusankaByEventId(Integer eventId) {
		return entryRepository.findFusankaByEventId(eventId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報一覧（考え中）を取得（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public List<Entry> findKangaetyuByEventId(Integer eventId) {
		return entryRepository.findKangaetyuByEventId(eventId);
	}
	
	/**
	 * 【取得】
	 * イベント参加情報一覧（未回答）を取得（イベントID）
	 * 
	 * @param eventId
	 * @return
	 */
	public List<Member> findMikaitoByEventId(Integer eventId) {
		return memberRepository.findMikaitoMemberByEventId(eventId);
	}
	
	/**
	 * 	【取得】
	 *  イベント参加情報を取得（メンバーID）
	 *  （イベント終了5日以内まで）
	 *  
	 * @param memberId
	 * @param date
	 * @return
	 */
	public List<Entry> findByMemberIdAndEventUntilDate(Integer memberId, LocalDate date) {
		return entryRepository.findByMemberIdAndEventUntilDate(memberId, date);
	}
	
	/**
	 * 【登録】
	 * イベント参加情報1件
	 * 
	 * @param entry
	 * @param event
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void regEntry(Entry entry, Event event) {
		
		// 途中参加の場合のチェック処理
		if (entry.getEntryKubun().equals("途中参加")) {
			
			// イベント時間
			LocalTime eventStartTime = event.getStartTime();
			LocalTime eventEndTime   = event.getEndTime();
			
			// 参加時間チェック
			// （１） イベント時間と参加時間が完全一致の場合、「参加」として登録
			if (entry.getEntryStartTime().equals(eventStartTime) && entry.getEntryEndTime().equals(eventEndTime)) {
				entry.setEntryKubun("参加");
				entry.setEntryStartTime(null);
				entry.setEntryEndTime(null);
			} else {
				if (entry.getEntryStartTime().isAfter(eventStartTime) && entry.getEntryEndTime().isBefore(eventEndTime)) {
					// （２） イベント開始時間 < 参加開始・終了時間 < イベント終了時間　→　処理なし
				} else if (entry.getEntryStartTime().equals(eventStartTime) && entry.getEntryEndTime().isBefore(eventEndTime)) {
					// （３） イベント開始時間 = 参加開始・終了時間 < イベント終了時間　→　処理なし
				} else if (entry.getEntryStartTime().isAfter(eventStartTime) && entry.getEntryEndTime().equals(eventEndTime)) {
					// （４） イベント開始時間 < 参加開始・終了時間 = イベント終了時間　→　処理なし
				} else {
					// （５） 上記以外、不正な時間
					throw new InvalidEntryTimeException(msg.getMessage("InvalidEntryTimeException", null, Locale.JAPAN));
				}
			}
		}
		
		// データの登録
		entryRepository.save(entry);
	}
	
	/**
	 * 【更新】
	 * イベント参加情報1件
	 * 
	 * @param entry
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEntry(Entry entry) {
		// データの更新
		entryRepository.save(entry);
	}
	
	/**
	 * 【更新】
	 * イベント参加情報複数
	 * 
	 * @param entryList
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEntryList(List<Entry> entryList) {
		// データの更新
		entryRepository.save(entryList);
	}
	
	/**
	 * 【削除】
	 * イベント参加情報1件
	 * 
	 * @param entryId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteEntry(Integer entryId) {
		entryRepository.delete(entryId);
	}
	
	/**
	 * 【削除】
	 * イベント参加情報
	 * （イベントIDに紐づくすべてのイベント参加情報）
	 * 
	 * @param eventId
	 */
	public void deleteEntryByEventId(Integer eventId) {
		entryRepository.deleteEntryByEventId(eventId);
	}
}
