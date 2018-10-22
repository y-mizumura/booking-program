package latte.domain.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DB共通Entity
 */
@MappedSuperclass
public class Model {
	/**
	 *  作成機能（システム共通）
	 */
	@JsonIgnore
	private String sysCreateFunc;
	
	/**
	 *  作成者（システム共通）
	 */
	@JsonIgnore
	private String sysCreateUser;
	
	/**
	 *  作成日時（システム共通）
	 */
	@JsonIgnore
	private LocalDateTime sysCreateDatetime;
	
	/**
	 *  更新機能（システム共通）
	 */
	@JsonIgnore
	private String sysUpdateFunc;
	
	/**
	 *  更新者（システム共通）
	 */
	@JsonIgnore
	private String sysUpdateUser;
	
	/**
	 *  更新日時（システム共通）
	 */
	@JsonIgnore
	private LocalDateTime sysUpdateDatetime;
	
	/**
	 * 作成関連項目　設定
	 * @param sysCreateFunc
	 * @param sysCreateUser
	 * @param sysCreateDatetime
	 */
	public void setSysCreateItems(String function, String user) {
		
		// 現在の日時取得
		LocalDateTime date = LocalDateTime.now();
		
		// 作成関連項目の設定
		setSysCreateItems(function, user, date);
		
		// 更新関連項目を呼び出す
		setSysUpdateItems(function, user, date);
	}
	
	/**
	 * 更新関連項目　設定
	 * @param function
	 * @param user
	 */
	public void setSysUpdateItems(String function, String user) {

		// 現在の日時取得
		LocalDateTime date = LocalDateTime.now();
		
		// 更新関連項目を呼び出す
		setSysUpdateItems(function, user, date);
	}
	
	/**
	 * 作成関連項目　設定
	 * @param function
	 * @param user
	 * @param date
	 */
	private void setSysCreateItems(String function, String user, LocalDateTime date) {
		this.sysCreateFunc = function;
		this.sysCreateUser = user;
		this.sysCreateDatetime = date;
	}
	
	/**
	 * 更新関連項目　設定
	 * @param sysUpdateFunc
	 * @param sysUpdateUser
	 * @param sysUpdateDatetime
	 */
	private void setSysUpdateItems(String function, String user, LocalDateTime date) {
		this.sysUpdateFunc = function;
		this.sysUpdateUser = user;
		this.sysUpdateDatetime = date;
	}
	
	public String getSysCreateFunc() {
		return sysCreateFunc;
	}

	public void setSysCreateFunc(String sysCreateFunc) {
		this.sysCreateFunc = sysCreateFunc;
	}

	public String getSysCreateUser() {
		return sysCreateUser;
	}

	public void setSysCreateUser(String sysCreateUser) {
		this.sysCreateUser = sysCreateUser;
	}

	public LocalDateTime getSysCreateDatetime() {
		return sysCreateDatetime;
	}

	public void setSysCreateDatetime(LocalDateTime sysCreateDatetime) {
		this.sysCreateDatetime = sysCreateDatetime;
	}

	public String getSysUpdateFunc() {
		return sysUpdateFunc;
	}

	public void setSysUpdateFunc(String sysUpdateFunc) {
		this.sysUpdateFunc = sysUpdateFunc;
	}

	public String getSysUpdateUser() {
		return sysUpdateUser;
	}

	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}

	public LocalDateTime getSysUpdateDatetime() {
		return sysUpdateDatetime;
	}

	public void setSysUpdateDatetime(LocalDateTime sysUpdateDatetime) {
		this.sysUpdateDatetime = sysUpdateDatetime;
	}

}
