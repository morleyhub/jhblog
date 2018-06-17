package io.github.jhipster.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.jhipster.application.domain.ClockinSummary;
import io.github.jhipster.application.service.dto.ClockinSummaryDTO;


/**
 * Spring Data JPA repository for the ClockinSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockinSummaryRepository extends JpaRepository<ClockinSummary, Long> {

	@Query("select clockinSummary from ClockinSummary clockinSummary "
			+ "where clockinSummary.wechatUserId =:wechatUserId")
	List<ClockinSummary> findByWechatUserId(@Param("wechatUserId") String wechatUserId);

}
