package latte.app.controller.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import latte.app.controller.M0302_LocationMngController;
import latte.app.form.M0302_LocationMngForm;
import latte.domain.model.Location;
import latte.domain.service.LocationService;
import latte.domain.service.LoginUserDetails;

@Component
public class M0302_LocationMngHelper {
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * Viewの初期化
	 * 
	 * @param locationId
	 * @param form
	 * @param model
	 */
	public void initView(Integer locationId, M0302_LocationMngForm form, Model model, boolean isRedirect) {
		
		// 場所オブジェクト生成
		Location location = locationService.findByLocationId(locationId);

		// 更新の場合、フォームに場所情報を設定
		if (location != null) {
			form.setLocationInfo(location);
		} else {
			form.init();
		}

		List<String> kubunList = new ArrayList<String>();
		kubunList.add("お気に入り");
		kubunList.add("都営");
		kubunList.add("公営");
		kubunList.add("私営");
		kubunList.add("その他");

		// モデルに追加
		model.addAttribute("locationId", locationId);
		model.addAttribute("kubun_list", kubunList);
		if (isRedirect) {
			// 入力エラーでリダイレクトの場合、formを設定しない
		} else {
			model.addAttribute("M0302_LocationMngForm", form);
		}
	}
	
	/**
	 * 登録処理
	 * 
	 * @param form
	 * @param userDetails
	 */
	public void register(M0302_LocationMngForm form, LoginUserDetails userDetails) {
		
		// 場所情報オブジェクト作成
		Location location = new Location();
		location.setName(form.getName());
		location.setKubun(form.getKubun());
		location.setAccess(form.getAccess());
		location.setSysCreateItems(M0302_LocationMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		locationService.regLocation(location);
	}

	/**
	 * 更新処理
	 * 
	 * @param form
	 * @param userDetails
	 * @param locationId
	 */
	public void update(M0302_LocationMngForm form, LoginUserDetails userDetails, Integer locationId) {
		
		// 場所情報オブジェクトをDBから取得
		Location location = locationService.findByLocationId(locationId);
		location.setName(form.getName());
		location.setKubun(form.getKubun());
		location.setAccess(form.getAccess());
		location.setSysUpdateItems(M0302_LocationMngController.FUNCTION_ID, userDetails.getUsername());

		// 登録・更新処理
		locationService.regLocation(location);
	}
	
	/**
	 * 削除処理
	 * 
	 * @param locationId
	 */
	public void delete(Integer locationId){
		locationService.deleteLocation(locationId);
	}
}
