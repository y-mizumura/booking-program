package latte.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import latte.domain.model.Member;
import latte.domain.service.LoginUserDetails;
import latte.domain.service.MemberService;

@Controller
@RequestMapping("C0902")
public class C0902_ChangeIconController {

	/**
	 * 機能ID
	 */
	public static final String FUNCTION_ID = "C0902";
	
	@Autowired
	private MessageSource ms;
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 画面表示
	 * 
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String show(
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			Model model) {
		
		return "mobile/C0902_ChangeIcon";
	}
	
	/**
	 * 新しいアイコンをアップロード
	 * 
	 * @param multipartFile
	 * @param userDetails
	 * @param model
	 * @return
	 */
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam("multipartFile") MultipartFile multipartFile,
			@AuthenticationPrincipal LoginUserDetails userDetails, 
			Model model){
		
		try {
			// ログインユーザのメンバー情報取得
			Member member = userDetails.getUser().getMember();
			
			// クライアントから送信時にすべてJPEG形式に変換
			String extension = ".jpeg";
			
			// 新しいファイル名
			String newFileName = member.getMemberId() + extension;
			
			// アップロードファイルを保存
			String imageName = ms.getMessage("user_icon_dir", null, Locale.JAPAN) + newFileName;
			File uploadFile = new File(imageName);
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);
			uploadFileStream.close();
			
			// DBを書き換える
			member.setIconName(newFileName);
			member.setSysUpdateItems(FUNCTION_ID, userDetails.getUsername());
			memberService.regMemberInfo(member);
			
			// 登録成功メッセージを表示
			model.addAttribute("success_message", ms.getMessage("C0902_success_message", null, Locale.JAPAN));
			
		} catch (Exception e) {
			model.addAttribute("top_error", ms.getMessage("top_error", null, Locale.JAPAN));
		}
		
		return "mobile/C0902_ChangeIcon";
	}
	
}