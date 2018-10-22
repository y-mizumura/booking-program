package latte.domain.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import latte.domain.model.converter.MemberStateConverter;
import latte.domain.model.converter.PositionConverter;
import latte.domain.model.converter.SexConverter;
import latte.util.Util;

@Entity
@Table(name="member")
public class Member extends Model{
	
	/**
	 * メンバーID
	 */
	@Id
	private Integer memberId;
	
	/**
	 * ユーザ情報
	 */
	@OneToOne(mappedBy = "member")
	private User user;
	
	/**
	 * 名前
	 */
	private String name;
	
	/**
	 * 状態
	 */
	@Convert(converter = MemberStateConverter.class)
	private String state;
	
	/**
	 * 性別
	 */
	@Convert(converter = SexConverter.class)
	private String sex;
	
	/**
	 * ポジション
	 */
	@Convert(converter = PositionConverter.class)
	private String position;
	
	/**
	 * 料金コード
	 */
	@ManyToOne
	@JoinColumn(name = "charge_cd")
	private Charge charge;

	/**
	 * アイコン名
	 */
	private String iconName;
	
	/**
	 * エントリー情報　（1：多）
	 * ※JSONでは送信の対象外
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<Entry> entries;
	
	/**
	 * Base64イメージを返却
	 * 
	 * @return
	 */
	public String getBase64Image(){
		String fileName = "upload_files/user_icon/" + this.iconName;
		return Util.loadBinaryImage(fileName);
	}
	
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

}
