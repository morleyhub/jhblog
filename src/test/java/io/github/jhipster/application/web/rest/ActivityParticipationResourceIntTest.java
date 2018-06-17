package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhblogApp;

import io.github.jhipster.application.domain.ActivityParticipation;
import io.github.jhipster.application.repository.ActivityParticipationRepository;
import io.github.jhipster.application.service.ActivityParticipationService;
import io.github.jhipster.application.service.dto.ActivityParticipationDTO;
import io.github.jhipster.application.service.mapper.ActivityParticipationMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivityParticipationResource REST controller.
 *
 * @see ActivityParticipationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhblogApp.class)
public class ActivityParticipationResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_PARTICIPATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PARTICIPATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ActivityParticipationRepository activityParticipationRepository;

    @Autowired
    private ActivityParticipationMapper activityParticipationMapper;

    @Autowired
    private ActivityParticipationService activityParticipationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityParticipationMockMvc;

    private ActivityParticipation activityParticipation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityParticipationResource activityParticipationResource = new ActivityParticipationResource(activityParticipationService);
        this.restActivityParticipationMockMvc = MockMvcBuilders.standaloneSetup(activityParticipationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityParticipation createEntity(EntityManager em) {
        ActivityParticipation activityParticipation = new ActivityParticipation()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .participationTime(DEFAULT_PARTICIPATION_TIME);
        return activityParticipation;
    }

    @Before
    public void initTest() {
        activityParticipation = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityParticipation() throws Exception {
        int databaseSizeBeforeCreate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);
        restActivityParticipationMockMvc.perform(post("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityParticipation testActivityParticipation = activityParticipationList.get(activityParticipationList.size() - 1);
        assertThat(testActivityParticipation.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testActivityParticipation.getParticipationTime()).isEqualTo(DEFAULT_PARTICIPATION_TIME);
    }

    @Test
    @Transactional
    public void createActivityParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation with an existing ID
        activityParticipation.setId(1L);
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityParticipationMockMvc.perform(post("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivityParticipations() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        // Get all the activityParticipationList
        restActivityParticipationMockMvc.perform(get("/api/activity-participations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityParticipation.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].participationTime").value(hasItem(DEFAULT_PARTICIPATION_TIME.toString())));
    }

    @Test
    @Transactional
    public void getActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(get("/api/activity-participations/{id}", activityParticipation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityParticipation.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.participationTime").value(DEFAULT_PARTICIPATION_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityParticipation() throws Exception {
        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(get("/api/activity-participations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);
        int databaseSizeBeforeUpdate = activityParticipationRepository.findAll().size();

        // Update the activityParticipation
        ActivityParticipation updatedActivityParticipation = activityParticipationRepository.findOne(activityParticipation.getId());
        // Disconnect from session so that the updates on updatedActivityParticipation are not directly saved in db
        em.detach(updatedActivityParticipation);
        updatedActivityParticipation
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .participationTime(UPDATED_PARTICIPATION_TIME);
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(updatedActivityParticipation);

        restActivityParticipationMockMvc.perform(put("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeUpdate);
        ActivityParticipation testActivityParticipation = activityParticipationList.get(activityParticipationList.size() - 1);
        assertThat(testActivityParticipation.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testActivityParticipation.getParticipationTime()).isEqualTo(UPDATED_PARTICIPATION_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityParticipation() throws Exception {
        int databaseSizeBeforeUpdate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityParticipationMockMvc.perform(put("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);
        int databaseSizeBeforeDelete = activityParticipationRepository.findAll().size();

        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(delete("/api/activity-participations/{id}", activityParticipation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityParticipation.class);
        ActivityParticipation activityParticipation1 = new ActivityParticipation();
        activityParticipation1.setId(1L);
        ActivityParticipation activityParticipation2 = new ActivityParticipation();
        activityParticipation2.setId(activityParticipation1.getId());
        assertThat(activityParticipation1).isEqualTo(activityParticipation2);
        activityParticipation2.setId(2L);
        assertThat(activityParticipation1).isNotEqualTo(activityParticipation2);
        activityParticipation1.setId(null);
        assertThat(activityParticipation1).isNotEqualTo(activityParticipation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityParticipationDTO.class);
        ActivityParticipationDTO activityParticipationDTO1 = new ActivityParticipationDTO();
        activityParticipationDTO1.setId(1L);
        ActivityParticipationDTO activityParticipationDTO2 = new ActivityParticipationDTO();
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
        activityParticipationDTO2.setId(activityParticipationDTO1.getId());
        assertThat(activityParticipationDTO1).isEqualTo(activityParticipationDTO2);
        activityParticipationDTO2.setId(2L);
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
        activityParticipationDTO1.setId(null);
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityParticipationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityParticipationMapper.fromId(null)).isNull();
    }
}
