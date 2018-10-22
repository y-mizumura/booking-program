package latte.domain.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.exception.NoExistDeleteTargetException;
import latte.domain.exception.NoExistMasterDataException;
import latte.domain.exception.UseOtherTableException;
import latte.domain.model.Member;
import latte.domain.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MessageSource msg;
	
	@Autowired
	private MemberRepository memberRepository;
	
	/**
	 * 【取得】
	 * メンバー情報　1件
	 * 
	 * @param memberId
	 * @return
	 */
	public Member findByMemberId(Integer memberId) {
		return memberRepository.findByMemberId(memberId);
	}
	
	/**
	 * 【取得】
	 * メンバー情報全件
	 * 
	 * @return
	 */
	public List<Member> findAll() {
		return memberRepository.findAll();
	}
	
	/**
	 * 【取得】
	 * メンバー検索
	 * キーワード：名前（あいまい検索）
	 * 
	 * @param name
	 * @return
	 */
	public List<Member> findByName(String name) {
		return memberRepository.findByName(name);
	}
	
	/**
	 * 【登録】　【更新】
	 * メンバー情報　1件
	 * 
	 * @param member
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer regMemberInfo(Member member) {
		
		// 料金コードが存在しない場合
		if (member.getCharge() == null) {
			throw new NoExistMasterDataException(msg.getMessage("NoExistMasterDataException", null, Locale.JAPAN));
		}
		
		// 新規登録
		if (member.getMemberId() == null || member.getMemberId().equals("")) {
			// メンバー情報の登録
			Integer memberId = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Integer date = Integer.parseInt(sdf.format(new Date()));
			Integer param1 = Integer.parseInt(date + "00");
			Integer param2 = Integer.parseInt(date + "99");

			// メンバーIDの設定
			Member m = memberRepository.findByMemberIdBetweenMax(param1, param2);
			if (m == null) {
				memberId = Integer.parseInt(date + "01");
			} else {
				memberId = m.getMemberId() + 1;
			}
			member.setMemberId(memberId);
		}

		// データの登録
		return memberRepository.save(member).getMemberId();
	}
	
	/**
	 * 【削除】
	 * メンバー情報 1件
	 * 
	 * @param memberId
	 */
	public void deleteMember(Integer memberId){
		// 削除対象取得
		Member member = memberRepository.findByMemberId(memberId);
		
		// 削除対象が存在しない場合
		if (member == null) {
			throw new NoExistDeleteTargetException(msg.getMessage("NoExistDeleteTargetException", null, Locale.JAPAN));
		}
		
		// 削除処理
		try {
			memberRepository.delete(memberId);
		} catch (DataAccessException e) {
			throw new UseOtherTableException(msg.getMessage("UseOtherTableException", new String[]{"メンバー"}, Locale.JAPAN));
		}
	}

}
