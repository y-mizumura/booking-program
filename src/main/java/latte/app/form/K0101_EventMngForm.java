package latte.app.form;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import latte.app.validator.ThirtyMinutesUnit;
import latte.app.validator.EndTimeMustBeAfterStartTime;
import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.model.Event;

@SuppressWarnings("serial")
@EndTimeMustBeAfterStartTime(groups={Register.class,Update.class}, message = "終了時刻は開始時間より後にしてください。")
public class K0101_EventMngForm implements Serializable{
	
	/**
	 * タイトル
	 */
	@Size(groups={Register.class,Update.class}, max=20, message="20文字以下で入力してください。")
	private String title;
	
	/**
	 * 場所
	 */
	@NotNull(groups={Register.class,Update.class}, message="場所を選択してください。")
	private Integer locationId;
	
	/**
	 * イベント日
	 */
	@NotNull(groups={Register.class,Update.class}, message="イベント日を選択してください。")
	private Date eventDate;
	
	/**
	 * 開始時刻
	 */
	@NotNull(groups={Register.class,Update.class}, message="開始時刻を選択してください。")
	@ThirtyMinutesUnit(groups={Register.class,Update.class}, message = "30分単位で入力してください。")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	/**
	 * 終了時刻
	 */
	@NotNull(groups={Register.class,Update.class}, message="終了時刻を選択してください。")
	@ThirtyMinutesUnit(groups={Register.class,Update.class}, message = "30分単位で入力してください。")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	/**
	 * 受付状態
	 */
	private String ukeState;
	
	/**
	 * メモ
	 */
	private String memo;
	
	/**
	 * 画面の初期化
	 */
	public void init(){
		this.title = "";
		this.locationId = 0;
		this.eventDate = Date.valueOf(LocalDate.now());
		this.startTime = LocalTime.of(11, 0);
		this.endTime = LocalTime.of(17, 0);
		this.ukeState = "受付中";
	}
	
	/**
	 * イベント情報を設定
	 * @param event
	 */
	public void setEventInfo(Event event){
		this.title = event.getTitle();
		this.locationId = event.getLocation().getLocationId();
		this.eventDate = Date.valueOf(event.getEventDate());
		this.startTime = event.getStartTime();
		this.endTime = event.getEndTime();
		this.ukeState = event.getUkeState();
		this.memo = event.getMemo();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
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

	public String getUkeState() {
		return ukeState;
	}

	public void setUkeState(String ukeState) {
		this.ukeState = ukeState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
