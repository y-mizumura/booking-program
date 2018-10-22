package latte.app.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.model.Location;

@SuppressWarnings("serial")
public class M0302_LocationMngForm implements Serializable{
	
	/**
	 * 場所名称
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="場所名称を入力してください。")
	@Size(groups={Register.class,Update.class}, max=15, message="15文字以下で入力してください。")
	private String name;
	
	/**
	 * 場所区分
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="場所区分を入力してください。")
	private String kubun;
	
	/**
	 * アクセス
	 */
	@Size(groups={Register.class,Update.class}, max=20, message="20文字以下で入力してください。")
	private String access;
	
	/**
	 * 画面の初期化
	 */
	public void init(){
		// 場所情報
		this.name = "";
		this.kubun = "お気に入り";
		this.access = "";
	}
	
	/**
	 * 場所情報を設定
	 * @param location
	 */
	public void setLocationInfo(Location location){
		this.name = location.getName();
		this.kubun = location.getKubun();
		this.access = location.getAccess();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKubun() {
		return kubun;
	}

	public void setKubun(String kubun) {
		this.kubun = kubun;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}
