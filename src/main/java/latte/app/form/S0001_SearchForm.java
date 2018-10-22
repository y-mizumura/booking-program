package latte.app.form;

import java.io.Serializable;

@SuppressWarnings("serial")
public class S0001_SearchForm implements Serializable {
	
	/**
	 * 検索ワード
	 */
	private String keyword;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
