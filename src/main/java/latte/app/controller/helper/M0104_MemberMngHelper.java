package latte.app.controller.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.M0104_MemberMngController;
import latte.app.form.M0104_MemberMngForm;
import latte.domain.model.Member;
import latte.domain.model.User;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.MemberService;
import latte.domain.service.UserService;

@Component
public class M0104_MemberMngHelper {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Viewの初期化
	 * 
	 * @param memberId
	 * @param form
	 * @param model
	 * @param isRedirect
	 */
	public void initView(Integer memberId, M0104_MemberMngForm form, Model model, boolean isRedirect) {
		
		// メンバー取得
		Member member = memberService.findByMemberId(memberId);
		User user = member.getUser();
		
		if (user!=null) {
			// 更新
			form.setUserInfo(user);
		} else {
			// 新規登録
			form.init();
		}
		
		List<String> roleList = new ArrayList<String>();
		roleList.add("ADMIN");
		roleList.add("E_ADMIN");
		roleList.add("USER");
		
		// モデルに追加
		model.addAttribute("role_list", roleList);
		model.addAttribute("memberId",memberId);
		model.addAttribute("member", member);
		if (isRedirect) {
			// 入力エラーでリダイレクトの場合、formを設定しない
		} else {
			model.addAttribute("M0104_MemberMngForm", form);
		}
	}
	
	/**
	 * 登録処理
	 * 
	 * @param form
	 * @param memberId
	 * @param userDetails
	 */
	public void register(M0104_MemberMngForm form, Integer memberId, LoginUserDetails userDetails) {
		
		// メンバー情報オブジェクト作成
		Member member = memberService.findByMemberId(memberId);
		
		User user = new User();
		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setRole(form.getRole());
		user.setState("E");
		user.setMember(member);
		user.setSysCreateItems(M0104_MemberMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		userService.regUserInfo(user);
	}
	
	/**
	 * 更新処理
	 * 
	 * @param form
	 * @param memberId
	 * @param userDetails
	 */
	public void update(M0104_MemberMngForm form, Integer memberId, LoginUserDetails userDetails) {
		
		// メンバー情報オブジェクト作成
		Member member = memberService.findByMemberId(memberId);
		
		User user = member.getUser();
		user.setRole(form.getRole());
		user.setSysUpdateItems(M0104_MemberMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		userService.updateUserInfo(user, form.getPassword(), userDetails.getUsername());
	}
	
}
