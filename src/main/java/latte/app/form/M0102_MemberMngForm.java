package latte.app.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import latte.app.validator.type.Register;
import latte.app.validator.type.Update;
import latte.domain.model.Member;

@SuppressWarnings("serial")
public class M0102_MemberMngForm implements Serializable{
	
	/**
	 * 名前
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="名前を入力してください。")
	@Size(groups={Register.class,Update.class}, max=10, message="10文字以下で入力してください。")
	private String name;
	
	/**
	 * 性別
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="性別を入力してください。")
	private String sex;
	
	/**
	 * ポジション
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="ポジションを入力してください。")
	private String position;
	
	/**
	 * 料金コード
	 */
	@NotEmpty(groups={Register.class,Update.class}, message="料金を入力してください。")
	private String chargeCd;
	
	/**
	 * 画面の初期化
	 */
	public void init(){
		// メンバー
		this.name = "";
		this.sex = "男性";
		this.position = "前衛";
		this.chargeCd = "";
	}
	
	/**
	 * メンバー情報を設定
	 * @param member
	 */
	public void setMemberInfo(Member member){
		this.name = member.getName();
		this.sex = member.getSex();
		this.position = member.getPosition();
		this.chargeCd = member.getCharge().getChargeCd();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getChargeCd() {
		return chargeCd;
	}

	public void setChargeCd(String chargeCd) {
		this.chargeCd = chargeCd;
	}
	
}
