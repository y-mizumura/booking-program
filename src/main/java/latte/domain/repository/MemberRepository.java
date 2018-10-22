package latte.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import latte.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	Member findByMemberId(Integer memberId);
	
	/**
	 * 本日日付で登録されているメンバーのうち、
	 * メンバーIDが最大のデータを取得
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	@Query("SELECT MAX(m) from Member m where m.memberId > :min and m.memberId < :max")
	public Member findByMemberIdBetweenMax(@Param("min")Integer min, @Param("max")Integer max);
	
	/**
	 * あいまい検索（名前）の結果
	 * 
	 * @param name
	 * @return
	 */
	@Query("SELECT m from Member m where m.name like %:name%")
	public List<Member> findByName(@Param("name")String name);
	
	/**
	 * イベントに対して未回答のメンバーリストを返却
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("SELECT m from Member m left join m.entries e on e.event.eventId = :eventId where e = NULL")
	public List<Member> findMikaitoMemberByEventId(@Param("eventId")Integer eventId);
	
}
