package latte.app.controller.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import latte.app.controller.M0501_ChargeMngController;
import latte.app.form.M0501_ChargeMngForm;
import latte.domain.model.Charge;
import latte.domain.service.ChargeService;
import latte.domain.service.LoginUserDetails;

@Component
public class M0501_ChargeMngHelper {
	
	@Autowired
	private ChargeService chargeService;
	
	/**
	 * 登録処理
	 * 
	 * @param form
	 * @param userDetails
	 */
	public void register(M0501_ChargeMngForm form, LoginUserDetails userDetails) {
		
		// 料金オブジェクト作成
		Charge charge = new Charge();
		charge.setChargeCd(form.getChargeCd());
		charge.setName(form.getName());
		charge.setCharge(form.getCharge());
		charge.setSysCreateItems(M0501_ChargeMngController.FUNCTION_ID, userDetails.getUsername());
		
		// 登録処理
		chargeService.regCharge(charge);
	}
	
	/**
	 * 更新処理
	 * 
	 * @param form
	 * @param userDetails
	 */
	public void update(M0501_ChargeMngForm form, LoginUserDetails userDetails) {
		
		// 料金オブジェクト取得
		Charge charge = chargeService.findByChargeCd(form.getUpdateSelect());
		charge.setSysUpdateItems(M0501_ChargeMngController.FUNCTION_ID, userDetails.getUsername());
		
		// 更新処理
		chargeService.updateCharge(charge, form.getUpdateName(), form.getUpdateCharge());
	}
	
	/**
	 * 削除処理
	 * 
	 * @param form
	 */
	public void delete(M0501_ChargeMngForm form){
		chargeService.deleteCharge(form.getDeleteSelect());
	}

}
