package latte.domain.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import latte.domain.model.converter.LocationKubunConverter;
import latte.domain.model.converter.NewLineConverter;

@Entity
@Table(name="location")
public class Location extends Model {
	
	/**
	 * 場所ID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer locationId;
	
	/**
	 * 場所名称
	 */
	private String name;

	/**
	 * 場所区分
	 */
	@Convert(converter = LocationKubunConverter.class)
	private String kubun;
	
	/**
	 * アクセス
	 */
	@Convert(converter = NewLineConverter.class)
	private String access;	
	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getKubun() {
		return kubun;
	}

	public void setKubun(String kubun) {
		this.kubun = kubun;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}
