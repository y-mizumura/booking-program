package latte.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import latte.domain.model.converter.NewLineConverter;
import latte.domain.model.converter.UketukeStateConverter;

@Entity
@Table(name="event")
public class Event extends Model{
	
	/**
	 * イベントID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer eventId;
	
	/**
	 * タイトル
	 */
	private String title;
	
	/**
	 * 場所
	 */
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	/**
	 * 受付状態
	 */
	@Convert(converter = UketukeStateConverter.class)
	private String ukeState;
	
	/**
	 * イベント実施日
	 */
	private LocalDate eventDate;
	
	/**
	 * 開始時刻
	 */
	private LocalTime startTime;
	
	/**
	 * 終了時刻
	 */
	private LocalTime endTime;
	
	/**
	 * メモ
	 */
	@Convert(converter = NewLineConverter.class)
	private String memo;

	/**
	 * エントリー情報　（1：多）
	 * ※JSONでは送信の対象外
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "event")
	private List<Entry> entries;
	
	/**
	 * アクティブイベントか判定
	 */
	public boolean isActiveEvent(){
		if (this.ukeState.equals("受付終了")) {
			return false;
		} else {
			LocalDate displayDate = LocalDate.now().minusDays(5);
			if (this.eventDate.compareTo(displayDate) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 受付中の場合、TRUE
	 * 
	 * @return
	 */
	public boolean isUkeChu() {
		return ukeState.equals("受付中");
	}
	
	/**
	 * 受付終了の場合、TRUE
	 * 
	 * @return
	 */
	public boolean isUkeEnd() {
		return ukeState.equals("受付終了");
	}
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUkeState() {
		return ukeState;
	}

	public void setUkeState(String ukeState) {
		this.ukeState = ukeState;
	}
	
	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
