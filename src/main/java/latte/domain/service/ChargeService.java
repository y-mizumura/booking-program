package latte.domain.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import latte.domain.exception.AlreadyRegisteredException;
import latte.domain.exception.NoExistUpdateItemException;
import latte.domain.exception.UseOtherTableException;
import latte.domain.model.Charge;
import latte.domain.repository.ChargeRepository;

@Service
public class ChargeService {

	@Autowired
	private MessageSource msg;
	
	@Autowired
	private ChargeRepository chargeRepository;

	/**
	 * 【取得】
	 * 料金テーブル 1件取得（料金コード）
	 * 
	 * @param chargeCd
	 * @return
	 */
	public Charge findByChargeCd(String chargeCd) {
		return chargeRepository.findOne(chargeCd);
	}

	/**
	 * 【取得】
	 * 料金テーブル 全件取得（コード順）
	 * 
	 * @return
	 */
	public List<Charge> findAllCharge() {
		return chargeRepository.findAll(new Sort(Sort.Direction.ASC, "chargeCd"));
	}

	/**
	 * 【登録】
	 * 料金 1件
	 * 
	 * @param charge
	 */
	public void regCharge(Charge charge) {

		// 既に登録済みのデータかチェック
		if (chargeRepository.findOne(charge.getChargeCd()) != null) {
			throw new AlreadyRegisteredException(msg.getMessage("AlreadyRegisteredException", new String[]{"料金"}, Locale.JAPAN));
		}

		// 登録処理
		chargeRepository.save(charge);
	}

	/**
	 * 【更新】
	 * 料金 1件
	 * 
	 * @param charge
	 * @param updateName
	 * @param updateCharge
	 */
	public void updateCharge(Charge charge, String updateName, Integer updateCharge) {

		// 更新対象の有無を保持
		boolean hasUpdateItem = false;		
		
		// 更新対象をセット（料金名称）
		if (updateName == null || updateName.equals("")) {
			// 処理なし
		} else {
			charge.setName(updateName);
			hasUpdateItem = true;
		}

		// 更新対象をセット（料金）
		if (updateCharge == null || updateCharge.equals("")) {
			// 処理なし
		} else {
			charge.setCharge(updateCharge);
			hasUpdateItem = true;
		}

		// 更新対象が存在しない場合
		if (!hasUpdateItem) {
			throw new NoExistUpdateItemException(msg.getMessage("NoExistUpdateItemException", null, Locale.JAPAN));
		}
		
		// 更新処理（レポジトリ）
		chargeRepository.save(charge);
	}

	/**
	 * 【削除】
	 * 料金 1件
	 * 
	 * @param chargeCd
	 */
	public void deleteCharge(String chargeCd) {
		try {
			// 削除処理
			chargeRepository.delete(chargeCd);
		} catch (DataAccessException e) {
			throw new UseOtherTableException(msg.getMessage("UseOtherTableException", new String[]{"料金"}, Locale.JAPAN));
		}
	}

}
