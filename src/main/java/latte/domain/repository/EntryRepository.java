package latte.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import latte.domain.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Integer>{
	
	@Query("SELECT e from Entry e where e.event.eventId = :eventId")
	public List<Entry> findByEventId(@Param("eventId")Integer eventId);

	@Query("SELECT e from Entry e where e.event.eventId = :eventId and e.member.memberId = :memberId")
	public Entry findByEventIdAndMemberId(@Param("eventId")Integer eventId, @Param("memberId")Integer memberId);

	@Query("SELECT e from Entry e where e.event.eventId = :eventId and (e.entryKubun = '参加' or e.entryKubun = '途中参加') order by e.member.memberId")
	public List<Entry> findSankaByEventId(@Param("eventId")Integer eventId);
	
	@Query("SELECT e from Entry e where e.event.eventId = :eventId and e.entryKubun = '不参加' order by e.member.memberId")
	public List<Entry> findFusankaByEventId(@Param("eventId")Integer eventId);
	
	@Query("SELECT e from Entry e where e.event.eventId = :eventId and e.entryKubun = '考え中' order by e.member.memberId")
	public List<Entry> findKangaetyuByEventId(@Param("eventId")Integer eventId);
	
	@Query("SELECT e from Entry e where e.member.memberId = :memberId and e.event.eventDate >= :date order by e.event.eventDate ASC")
	public List<Entry> findByMemberIdAndEventUntilDate(@Param("memberId")Integer memberId, @Param("date")LocalDate date);
	
	@Transactional
	@Modifying
	@Query("DELETE from Entry e where e.event.eventId = :eventId")
	public void deleteEntryByEventId(@Param("eventId")Integer eventId);
}
