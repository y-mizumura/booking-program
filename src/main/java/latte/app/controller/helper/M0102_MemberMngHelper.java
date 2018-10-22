package latte.app.controller.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.M0102_MemberMngController;
import latte.app.form.M0102_MemberMngForm;
import latte.domain.model.Charge;
import latte.domain.model.Member;
import latte.domain.service.ChargeService;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.MemberService;

@Component
public class M0102_MemberMngHelper {
	
	@Autowired
	private ChargeService chargeService;
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * Viewの初期化
	 * 
	 * @param memberId
	 * @param form
	 * @param model
	 * @param isRedirect
	 */
	public void initView(Integer memberId, M0102_MemberMngForm form, Model model, boolean isRedirect) {
		
		// メンバーオブジェクト生成
		Member member = memberService.findByMemberId(memberId);

		// 更新の場合、フォームにメンバー情報を設定
		if (member != null) {
			form.setMemberInfo(member);
		} else {
			form.init();
		}

		List<Charge> chargeList = chargeService.findAllCharge();
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("charge_list", chargeList);
		if (isRedirect) {
			// 入力エラーでリダイレクトの場合、formを設定しない
		} else {
			model.addAttribute("M0102_MemberMngForm", form);
		}
	}
	
	/**
	 * 登録処理
	 * 
	 * @param form
	 * @param userDetails
	 */
	public Integer register(M0102_MemberMngForm form, LoginUserDetails userDetails) {
		
		// 料金オブジェクト作成
		Charge charge = chargeService.findByChargeCd(form.getChargeCd());
		
		// メンバー情報オブジェクト作成
		Member member = new Member();
		member.setName(form.getName());
		member.setState("在籍");
		member.setSex(form.getSex());
		member.setPosition(form.getPosition());
		member.setCharge(charge);
		member.setIconName("default.jpeg");
		member.setSysCreateItems(M0102_MemberMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		return memberService.regMemberInfo(member);
	}

	/**
	 * 更新処理
	 * 
	 * @param form
	 * @param userDetails
	 * @param memberId
	 */
	public void update(M0102_MemberMngForm form, LoginUserDetails userDetails, Integer memberId) {
		
		// 料金オブジェクト作成
		Charge charge = chargeService.findByChargeCd(form.getChargeCd());
		
		// メンバー情報オブジェクト作成
		Member member = memberService.findByMemberId(memberId);
		member.setMemberId(memberId);
		member.setName(form.getName());
		member.setSex(form.getSex());
		member.setPosition(form.getPosition());
		member.setCharge(charge);
		member.setSysUpdateItems(M0102_MemberMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		memberService.regMemberInfo(member);
	}
	
}
