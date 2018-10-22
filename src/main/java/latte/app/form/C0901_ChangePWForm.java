package latte.app.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import latte.app.validator.ConfirmPasswordMustBeSame;
import latte.app.validator.NewPasswordMustBeDifferent;

@SuppressWarnings("serial")
@NewPasswordMustBeDifferent(message="現在のパスワードと新しいパスワードは、違うものを入力してください。")
@ConfirmPasswordMustBeSame(message="新しいパスワードと同じものを入力してください。")
public class C0901_ChangePWForm implements Serializable {
	
	/**
	 * 現在のパスワード
	 */
	@NotEmpty(message="入力してください。")
	private String currentPW;
	
	/**
	 * 新しいパスワード
	 */
	@Size(min=4, max=10, message="4文字以上、10文字以下で入力してください。")
	@Pattern(regexp="[a-zA-Z0-9]*", message="半角英数字のみ使用可能です。")
	private String newPW1;

	/**
	 * 新しいパスワード（確認用）
	 */
	@Size(min=4, max=10, message="4文字以上、10文字以下で入力してください。")
	@Pattern(regexp="[a-zA-Z0-9]*", message="半角英数字のみ使用可能です。")
	private String newPW2;

	public String getCurrentPW() {
		return currentPW;
	}

	public void setCurrentPW(String currentPW) {
		this.currentPW = currentPW;
	}

	public String getNewPW1() {
		return newPW1;
	}

	public void setNewPW1(String newPW1) {
		this.newPW1 = newPW1;
	}

	public String getNewPW2() {
		return newPW2;
	}

	public void setNewPW2(String newPW2) {
		this.newPW2 = newPW2;
	}
	
}