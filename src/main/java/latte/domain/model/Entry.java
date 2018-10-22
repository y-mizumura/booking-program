package latte.domain.model;

import java.time.LocalTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import latte.domain.model.converter.EntryKubunConverter;

@Entity
@Table(name="entry")
public class Entry extends Model{
	
	/**
	 * イベント参加ID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer entryId;
	
	/**
	 * イベント
	 */
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	/**
	 * メンバー
	 */
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	/**
	 * 参加・不参加区分
	 */
	@Convert(converter = EntryKubunConverter.class)
	private String entryKubun;
	
	/**
	 * 参加時刻（開始）
	 */
	private LocalTime entryStartTime;
	
	/**
	 * 参加時刻（終了）
	 */
	private LocalTime entryEndTime;
	
	/**
	 * 参加費
	 */
	private Integer entryFee;
	
	/**
	 * 清算済みフラグ
	 */
	private Integer seisanzumiFlg;
	
	/**
	 * 参加・不参加区分が「参加」かどうか
	 * @return
	 */
	public boolean isEntryKubunS(){
		return this.entryKubun.equals("参加");
	}
	
	/**
	 * 参加・不参加区分が「途中参加」かどうか
	 * @return
	 */
	public boolean isEntryKubunT(){
		return this.entryKubun.equals("途中参加");
	}
	
	/**
	 * 参加・不参加区分が「参加」または「途中参加」かどうか
	 * @return
	 */
	public boolean isEntryKubunST(){
		return this.entryKubun.equals("参加") || this.entryKubun.equals("途中参加");
	}
	
	/**
	 * 参加・不参加区分が「不参加」かどうか
	 * @return
	 */
	public boolean isEntryKubunF(){
		return this.entryKubun.equals("不参加");
	}
	
	/**
	 * 参加・不参加区分が「考え中」かどうか
	 * @return
	 */
	public boolean isEntryKubunK(){
		return this.entryKubun.equals("考え中");
	}
	
	/**
	 * 参加費を表示可能かどうか
	 * @return
	 */
	public boolean isDispEntryFee(){
		return this.isEntryKubunST() &&  this.entryFee!=null;
	}
	
	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getEntryKubun() {
		return entryKubun;
	}

	public void setEntryKubun(String entryKubun) {
		this.entryKubun = entryKubun;
	}

	public LocalTime getEntryStartTime() {
		return entryStartTime;
	}

	public void setEntryStartTime(LocalTime entryStartTime) {
		this.entryStartTime = entryStartTime;
	}

	public LocalTime getEntryEndTime() {
		return entryEndTime;
	}

	public void setEntryEndTime(LocalTime entryEndTime) {
		this.entryEndTime = entryEndTime;
	}

	public Integer getEntryFee() {
		return entryFee;
	}

	public void setEntryFee(Integer entryFee) {
		this.entryFee = entryFee;
	}

	public Integer getSeisanzumiFlg() {
		return seisanzumiFlg;
	}

	public void setSeisanzumiFlg(Integer seisanzumiFlg) {
		this.seisanzumiFlg = seisanzumiFlg;
	}

}
