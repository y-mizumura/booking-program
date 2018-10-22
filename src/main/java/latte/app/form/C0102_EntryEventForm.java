package latte.app.form;

import java.io.Serializable;
import java.time.LocalTime;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import latte.app.validator.ThirtyMinutesUnit;
import latte.app.validator.type.SpecifiedTime;
import latte.app.validator.EndTimeMustBeAfterStartTime;
import latte.domain.model.Entry;
import latte.domain.model.Event;

@SuppressWarnings("serial")
@EndTimeMustBeAfterStartTime(message = "終了時刻は開始時間より後にしてください。", groups={SpecifiedTime.class})
public class C0102_EntryEventForm implements Serializable{
	
	/**
	 * 参加・不参加区分
	 */
	@NotEmpty(message="選択肢より参加区分を選択してください。")
	private String entryKubun;
	
	/**
	 * 開始時刻
	 */
	@ThirtyMinutesUnit(message = "30分単位で入力してください。", groups={SpecifiedTime.class})
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	/**
	 * 終了時刻
	 */
	@ThirtyMinutesUnit(message = "30分単位で入力してください。", groups={SpecifiedTime.class})
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	/**
	 * 画面の初期化
	 */
	public void init(Event event){
		this.startTime = event.getStartTime();
		this.endTime = event.getEndTime();
	}
	
	/**
	 * イベント参加情報を設定
	 * @param event
	 */
	public void setEntryInfo(Entry entry){
		// 画面を初期化
		this.init(entry.getEvent());
		
		// DBの値で更新
		this.entryKubun = entry.getEntryKubun();
		
		// 「途中参加」の場合、開始時間／終了時間をDBから取得
		if (this.entryKubun.equals("途中参加")) {
			this.startTime = entry.getEntryStartTime();
			this.endTime = entry.getEntryEndTime();
		}
	}

	public String getEntryKubun() {
		return entryKubun;
	}

	public void setEntryKubun(String entryKubun) {
		this.entryKubun = entryKubun;
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

}
