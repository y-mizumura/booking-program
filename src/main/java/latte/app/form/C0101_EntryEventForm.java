package latte.app.form;

import java.io.Serializable;

@SuppressWarnings("serial")
public class C0101_EntryEventForm implements Serializable{

	/**
	 * 検索条件（年月）
	 */
	private String searchTarget;
	
	public void init(String currentNenGetsu){
		this.searchTarget = currentNenGetsu;
	}

	public String getSearchTarget() {
		return searchTarget;
	}

	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}
}
