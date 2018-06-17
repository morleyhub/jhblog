package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ActivityParticipationService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.ActivityParticipationDTO;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ActivityParticipation.
 */
@RestController
@RequestMapping("/api")
public class ActivityParticipationResource {

    private final Logger log = LoggerFactory.getLogger(ActivityParticipationResource.class);

    private static final String ENTITY_NAME = "activityParticipation";

    private final ActivityParticipationService activityParticipationService;

    public ActivityParticipationResource(ActivityParticipationService activityParticipationService) {
        this.activityParticipationService = activityParticipationService;
    }

    /**
     * POST  /activity-participations : Create a new activityParticipation.
     *
     * @param activityParticipationDTO the activityParticipationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityParticipationDTO, or with status 400 (Bad Request) if the activityParticipation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activity-participations")
    @Timed
    public ResponseEntity<ActivityParticipationDTO> createActivityParticipation(@Valid @RequestBody ActivityParticipationDTO activityParticipationDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityParticipation : {}", activityParticipationDTO);
        if (activityParticipationDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityParticipation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityParticipationDTO result = activityParticipationService.save(activityParticipationDTO);
        return ResponseEntity.created(new URI("/api/activity-participations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activity-participations : Updates an existing activityParticipation.
     *
     * @param activityParticipationDTO the activityParticipationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityParticipationDTO,
     * or with status 400 (Bad Request) if the activityParticipationDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityParticipationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activity-participations")
    @Timed
    public ResponseEntity<ActivityParticipationDTO> updateActivityParticipation(@Valid @RequestBody ActivityParticipationDTO activityParticipationDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityParticipation : {}", activityParticipationDTO);
        if (activityParticipationDTO.getId() == null) {
            return createActivityParticipation(activityParticipationDTO);
        }
        ActivityParticipationDTO result = activityParticipationService.save(activityParticipationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityParticipationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activity-participations : get all the activityParticipations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activityParticipations in body
     */
    @GetMapping("/activity-participations")
    @Timed
    public List<ActivityParticipationDTO> getAllActivityParticipations() {
        log.debug("REST request to get all ActivityParticipations");
        return activityParticipationService.findAll();
        }

    /**
     * GET  /activity-participations/:id : get the "id" activityParticipation.
     *
     * @param id the id of the activityParticipationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityParticipationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activity-participations/{id}")
    @Timed
    public ResponseEntity<ActivityParticipationDTO> getActivityParticipation(@PathVariable Long id) {
        log.debug("REST request to get ActivityParticipation : {}", id);
        ActivityParticipationDTO activityParticipationDTO = activityParticipationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activityParticipationDTO));
    }
    


    /**
     * DELETE  /activity-participations/:id : delete the "id" activityParticipation.
     *
     * @param id the id of the activityParticipationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activity-participations/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivityParticipation(@PathVariable Long id) {
        log.debug("REST request to delete ActivityParticipation : {}", id);
        activityParticipationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
  
  
    @GetMapping("/activity-participations/getByActivityId")
    @Timed
    public List<ActivityParticipationDTO> getActivityParticipationByActivityId(Long activityId) {
        log.debug("REST request to get ActivityParticipation : {}", activityId);
        if (null == activityId) {
            throw new BadRequestAlertException("activityId can not be null", ENTITY_NAME, "activityIdNULL");
        }
        return activityParticipationService.findByActivity(activityId);
    }
    
    
    @GetMapping("/activity-participations/getByWechatUserId")
    @Timed
    public List<ActivityParticipationDTO> getAttendedActivities(String wechatUserId) {
        log.debug("REST request to get ActivityParticipation by  WechatUserId: {}", wechatUserId);
        if (StringUtils.isEmpty(wechatUserId)) {
            throw new BadRequestAlertException("wechatuserid can not be null", ENTITY_NAME, "weChatUserIdNULL");
        }
        return activityParticipationService.findByWechatUserId(wechatUserId);
    }
    
}
