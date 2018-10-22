package latte.domain.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import latte.domain.model.Charge;

public interface ChargeRepository extends JpaRepository<Charge, String>{
	Charge findByChargeCd(String chargeCd);
	
	/**
	 * 料金テーブル　全件取得
	 */
	public List<Charge> findAll(Sort sort);
	
}
