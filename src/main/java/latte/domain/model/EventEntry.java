package latte.domain.model;

public class EventEntry {
	
	/**
	 * イベント
	 */
	private Event event;
	
	/**
	 * イベント参加
	 */
	private Entry entry;
	
	public EventEntry(Event event, Entry entry) {
		this.event = event;
		this.entry = entry;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
}
