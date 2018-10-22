package latte.domain.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.exception.UseOtherTableException;
import latte.domain.model.Location;
import latte.domain.repository.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private MessageSource msg;
	
	/**
	 * 【取得】
	 * 場所情報を全件
	 * 
	 * @return
	 */
	public List<Location> findAll() {
		return locationRepository.findAll(new Sort(Sort.Direction.ASC, "locationId"));
	}
	
	/**
	 * 【取得】
	 * 場所情報を1件（場所ID）
	 * 
	 * @param locationId
	 * @return
	 */
	public Location findByLocationId(Integer locationId) {
		return locationRepository.findOne(locationId);
	}
	
	/**
	 * 【取得】
	 * 場所情報検索（場所区分検索）
	 * 
	 * @param kubun
	 * @return
	 */
	public List<Location> findByKubun(String kubun) {
		return locationRepository.findByKubun(kubun);
	}
	
	/**
	 * 【取得】
	 * 場所情報検索（あいまい検索）
	 * 
	 * @param name
	 * @return
	 */
	public List<Location> findByName(String name) {
		return locationRepository.findByName(name);
	}
	
	/**
	 * 【登録】　【更新】
	 * 場所情報　1件
	 * 
	 * @param location
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void regLocation(Location location) {
		locationRepository.save(location);
	}
	
	/**
	 * 【削除】
	 * 場所情報　1件
	 * 
	 * @param locationId
	 */
	public void deleteLocation(Integer locationId) {
		try {
			// 削除処理
			locationRepository.delete(locationId);
		} catch (DataAccessException e) {
			throw new UseOtherTableException(msg.getMessage("UseOtherTableException", new String[]{"場所"}, Locale.JAPAN));
		}
		
	}
}
