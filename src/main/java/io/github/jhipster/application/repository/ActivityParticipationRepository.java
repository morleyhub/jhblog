package io.github.jhipster.application.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.jhipster.application.domain.ActivityParticipation;
import io.github.jhipster.application.service.dto.ActivityParticipationDTO;


/**
 * Spring Data JPA repository for the ActivityParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipation, Long> {
	
	@Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
			+ "where activityParticipation.activity.id =:activityId",nativeQuery = false)
	List<ActivityParticipation> findByActivityId(@Param("activityId") Long activityId);

	@Query("select activityParticipation from ActivityParticipation activityParticipation "
			+ "where activityParticipation.wechatUserId =:wechatUserId")
	List<ActivityParticipation> findByWechatUserId(@Param("wechatUserId") String wechatUserId);
}
