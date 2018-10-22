package latte.domain.model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User extends Model{
	
	/**
	 * ユーザID
	 */
	@Id
	private String userId;
	
	/**
	 * パスワード
	 */
	private String password;
	
	/**
	 * 役割
	 */
	private String role;
	
	/**
	 * 状態
	 */
	private String state;
	
	/**
	 * メンバー
	 */
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
