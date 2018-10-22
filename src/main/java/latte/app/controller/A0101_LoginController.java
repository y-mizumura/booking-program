package latte.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;;

@Controller
public class A0101_LoginController {
	
	/**
	 * 画面表示
	 * 
	 * @return
	 */
	@RequestMapping("loginForm")
	public String show() {
		return "mobile/A0101_LoginForm";
	}
	
}
