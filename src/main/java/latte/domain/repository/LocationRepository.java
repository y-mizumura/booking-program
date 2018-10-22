package latte.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import latte.domain.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{
	
	@Query("SELECT l from Location l where l.kubun = :kubun")
	public List<Location> findByKubun(@Param("kubun")String kubun);
	
	@Query("SELECT l from Location l where l.name like %:name%")
	public List<Location> findByName(@Param("name")String name);
	
}
