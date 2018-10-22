package latte.app.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.model.User;

@SuppressWarnings("serial")
public class M0104_MemberMngForm implements Serializable{
	
	/**
	 * ユーザID
	 */
	@NotEmpty(groups={Register.class}, message="ユーザIDを入力してください。")
	private String userId;
	
	/**
	 * パスワード
	 */
	@NotEmpty(groups={Register.class}, message="パスワードを入力してください。")
	private String password;
	
	/**
	 * 役割
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="役割を入力してください。")
	private String role;
	
	/**
	 * 画面の初期化
	 */
	public void init(){
		this.userId = "";
		this.password = "";
		this.role = "";
	}
	
	/**
	 * ユーザ情報を設定
	 * @param member
	 */
	public void setUserInfo(User user){
		this.userId = user.getUserId();
		this.password = "";
		this.role = user.getRole();
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
