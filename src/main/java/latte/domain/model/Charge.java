package latte.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="charge")
public class Charge extends Model{
	
	/**
	 * 参加費コード
	 */
	@Id
	private String chargeCd;
	
	/**
	 * 参加費名称
	 */
	private String name;
	
	/**
	 * 金額
	 */
	private int charge;
	
	public String toString(){
		return this.name + "【" + this.charge + "円】";
	}
	
	public String getChargeCd() {
		return chargeCd;
	}

	public void setChargeCd(String chargeCd) {
		this.chargeCd = chargeCd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}
	
}
