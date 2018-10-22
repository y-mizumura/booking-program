package latte.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import latte.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer>{
	
	/**
	 * イベント日が引数の日付から5日前までのイベントを取得（5日前を含む）
	 * 
	 * @param date
	 * @return
	 */
	@Query("SELECT ev from Event ev where ev.eventDate >= :date order by event_date ASC")
	public List<Event> findActiveEvent(@Param("date")LocalDate date);
	
	/**
	 * 過去のイベント取得
	 * 
	 * @param startDate
	 * @param endDate
	 * @param hiddenDate
	 * @return
	 */
	@Query("SELECT ev from Event ev where ev.eventDate >= :startDate and ev.eventDate < :endDate and ev.eventDate < :hiddenDate order by event_date ASC")
	public List<Event> findInactiveEvent(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("hiddenDate")LocalDate hiddenDate);
	
	/**
	 * 過去のイベント取得（参加）
	 * 
	 * @param startDate
	 * @param endDate
	 * @param hiddenDate
	 * @param memberId
	 * @return
	 */
	@Query("SELECT ev from Event ev left join ev.entries en on en.member.memberId = :memberId "
			+ "where ev.eventDate >= :startDate and ev.eventDate < :endDate and ev.eventDate < :hiddenDate and en.entryId is not null order by event_date ASC")
	public List<Event> findInactiveSankaEvent(
			@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("hiddenDate")LocalDate hiddenDate, @Param("memberId") Integer memberId);
	
}
