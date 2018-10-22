package latte.domain.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.exception.AlreadyRegisteredException;
import latte.domain.exception.ChangeSelfAccountException;
import latte.domain.exception.NoMuchRegistedPasswordException;
import latte.domain.model.User;
import latte.domain.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private MessageSource msg;
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * 【取得】
	 * ユーザ情報　1件
	 * 
	 * @param userId
	 * @return
	 */
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	/**
	 * 【登録】
	 * ユーザ情報　1件
	 * 
	 * @param user
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void regUserInfo(User user) {

		// ユーザIDが既に登録済みの場合
		if (userRepository.findByUserId(user.getUserId()) != null) {
			throw new AlreadyRegisteredException(msg.getMessage("AlreadyRegisteredException", new String[]{"ユーザID"}, Locale.JAPAN));
		}

		// パスワードの変換
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePW = encoder.encode(user.getPassword());
		// パスワード再設定（変換後）
		user.setPassword(encodePW);

		// データの登録
		userRepository.save(user);
	}
	
	/**
	 * 【更新】
	 * ユーザ情報　1件
	 * パスワードに入力がある場合、変換後に設定する。
	 * 
	 * @param user
	 * @param newPW
	 * @param userName
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserInfo(User user, String newPW, String userName) {
		
		// 自分自身をは変更不可
		if (user.getUserId().equals(userName)) {
			throw new ChangeSelfAccountException(msg.getMessage("ChangeSelfAccountException", null, Locale.JAPAN));
		}
		
		// パスワード再設定（変換後）
		if (newPW!=null && !newPW.equals("")) {
			// パスワードの変換
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodePW = encoder.encode(newPW);
			user.setPassword(encodePW);
		}
		
		// データの登録
		userRepository.save(user);
	}

	/**
	 * 【更新】
	 * パスワードの更新
	 * 
	 * @param user
	 * @param currentPW
	 * @param newPW
	 * @return
	 */
	public User updateUserPassword(User user, String currentPW, String newPW) {
		
		// 更新対象の現在のユーザパスワードを取得
		String registedPW = userRepository.findOne(user.getUserId()).getPassword();
		
		// 現在のパスワードが正しいかチェック
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(currentPW, registedPW)) {
			throw new NoMuchRegistedPasswordException(msg.getMessage("NoMuchRegistedPasswordException", null, Locale.JAPAN));
		}

		// パスワードの変換
		String encodePW = encoder.encode(newPW);

		// パスワードの設定
		user.setPassword(encodePW);

		// サーバに登録
		userRepository.save(user);
		return user;
	}
}
