package latte.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import latte.app.form.S0001_SearchForm;
import latte.domain.model.Location;
import latte.domain.service.LocationService;

@Controller
@PreAuthorize("hasAuthority('AUTH_LOCATION')")
@RequestMapping("/M0301")
public class M0301_LocationMngController {
	
	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "M0301";
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * 検索フォームの初期化
	 * 
	 * @return
	 */
	@ModelAttribute
	public S0001_SearchForm setUpForm() {
		S0001_SearchForm form = new S0001_SearchForm();
		return form;
	}
	
	/**
	 * 画面表示
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String show(@ModelAttribute("S0001_SearchForm") S0001_SearchForm form, Model model) {
		
		// 検索フォーム追加
		model.addAttribute("S0001_SearchForm", form);
		
		// 一覧追加
		List<Location> locationAList = locationService.findByKubun("お気に入り");
		List<Location> locationTList = locationService.findByKubun("都営");
		List<Location> locationKList = locationService.findByKubun("公営");
		List<Location> locationSList = locationService.findByKubun("私営");
		List<Location> locationZList = locationService.findByKubun("その他");
		model.addAttribute("locationAList", locationAList);
		model.addAttribute("locationTList", locationTList);
		model.addAttribute("locationKList", locationKList);
		model.addAttribute("locationSList", locationSList);
		model.addAttribute("locationZList", locationZList);
		
		return "mobile/M0301_LocationMng";
	}
	
	/**
	 * 検索ボタン押下時
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value="search", method = RequestMethod.GET)
	public String search(@ModelAttribute("S0001_SearchForm") S0001_SearchForm form, Model model) {
		
		// 検索フォーム追加
		model.addAttribute("S0001_SearchForm", form);
		
		// 一覧追加（keywordで施設名を検索）
		List<Location> locationList = locationService.findByName(form.getKeyword());
		model.addAttribute("locationList", locationList);
				
		return "mobile/M0301_LocationMng";
	}
}
