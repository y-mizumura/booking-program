package latte.app.controller.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import latte.app.controller.C0901_ChangePWController;
import latte.app.form.C0901_ChangePWForm;
import latte.domain.model.User;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.UserService;

@Component
public class C0901_ChangePWHelper {

	@Autowired
	private UserService userService;
	
	/**
	 * パスワードの更新
	 * 
	 * @param form
	 * @param userDetails
	 */
	public void updatePW(C0901_ChangePWForm form, LoginUserDetails userDetails) {
		
		// フォームより入力値を取得
		String currentPW = form.getCurrentPW();
		String newPW = form.getNewPW1();
		
		// ログインユーザ情報取得
		User user = userDetails.getUser();
		
		// システム共通項目設定
		user.setSysUpdateItems(C0901_ChangePWController.FUNCTION_ID, userDetails.getUsername());

		// パスワードの更新
		userService.updateUserPassword(user, currentPW, newPW);
	}

}
