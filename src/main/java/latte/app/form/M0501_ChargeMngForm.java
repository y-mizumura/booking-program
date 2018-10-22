package latte.app.form;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import latte.app.validator.type.Register;
import latte.app.validator.type.Update;

@SuppressWarnings("serial")
public class M0501_ChargeMngForm implements Serializable{
	
	/**
	 * 登録対象の料金コード
	 */
	@NotEmpty(groups=Register.class, message="コードを入力してください。")
	@Size(groups=Register.class, max=3, message="3文字以下で入力してください。")
	@Pattern(groups=Register.class, regexp="[a-zA-Z0-9]*", message="半角英数字のみ使用可能です。")
	private String chargeCd;
	
	/**
	 * 登録対象の料金名称
	 */
	@NotEmpty(groups=Register.class, message="料金名称を入力してください。")
	@Size(groups=Register.class, max=10, message="10文字以下で入力してください。")
	private String name;
	
	/**
	 * 登録対象の料金
	 */
	@NotNull(groups=Register.class, message="料金を入力してください。")
	@Max(groups=Register.class, value=10000, message="0~10000で入力してください。")
	@Min(groups=Register.class, value=0, message="0~10000で入力してください。")
	private Integer charge;
	
	/**
	 * 更新対象の料金コード
	 */
	private String updateSelect;

	/**
	 * 更新対象の料金名称
	 */
	@Size(groups=Update.class, max=10, message="10文字以下で入力してください。")
	private String updateName;
	
	/**
	 * 更新対象の料金
	 */
	@Max(groups=Update.class, value=10000, message="0~10000で入力してください。")
	@Min(groups=Update.class, value=0, message="0~10000で入力してください。")
	private Integer updateCharge;
	
	/**
	 *  削除対象の料金コード
	 */
	private String deleteSelect;
	
	/**
	 * 画面項目をすべて初期化
	 */
	public void init(){
		// 登録関連
		this.chargeCd = "";
		this.name = "";
		this.charge = null;
		
		// 更新関連
		this.updateSelect = "";
		this.updateName = "";
		this.updateCharge = null;
		
		// 削除関連
		this.deleteSelect = "";
	}
	
	public String getChargeCd() {
		return chargeCd;
	}

	public void setChargeCd(String chargeCd) {
		this.chargeCd = chargeCd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Integer getUpdateCharge() {
		return updateCharge;
	}

	public void setUpdateCharge(Integer updateCharge) {
		this.updateCharge = updateCharge;
	}

	public String getUpdateSelect() {
		return updateSelect;
	}

	public void setUpdateSelect(String updateSelect) {
		this.updateSelect = updateSelect;
	}
	
	public String getDeleteSelect() {
		return deleteSelect;
	}

	public void setDeleteSelect(String deleteSelect) {
		this.deleteSelect = deleteSelect;
	}

}
