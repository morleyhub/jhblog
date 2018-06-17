package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhblogApp;

import io.github.jhipster.application.domain.Activity;
import io.github.jhipster.application.repository.ActivityRepository;
import io.github.jhipster.application.service.ActivityService;
import io.github.jhipster.application.service.dto.ActivityDTO;
import io.github.jhipster.application.service.mapper.ActivityMapper;
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
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhblogApp.class)
public class ActivityResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_SIGN_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SIGN_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SIGN_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SIGN_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVITY_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITY_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVITY_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITY_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResource activityResource = new ActivityResource(activityService);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
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
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .title(DEFAULT_TITLE)
            .descrption(DEFAULT_DESCRPTION)
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .signStartTime(DEFAULT_SIGN_START_TIME)
            .signEndTime(DEFAULT_SIGN_END_TIME)
            .activityStartTime(DEFAULT_ACTIVITY_START_TIME)
            .activityEndTime(DEFAULT_ACTIVITY_END_TIME);
        return activity;
    }

    @Before
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testActivity.getDescrption()).isEqualTo(DEFAULT_DESCRPTION);
        assertThat(testActivity.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testActivity.getSignStartTime()).isEqualTo(DEFAULT_SIGN_START_TIME);
        assertThat(testActivity.getSignEndTime()).isEqualTo(DEFAULT_SIGN_END_TIME);
        assertThat(testActivity.getActivityStartTime()).isEqualTo(DEFAULT_ACTIVITY_START_TIME);
        assertThat(testActivity.getActivityEndTime()).isEqualTo(DEFAULT_ACTIVITY_END_TIME);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].descrption").value(hasItem(DEFAULT_DESCRPTION.toString())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].signStartTime").value(hasItem(DEFAULT_SIGN_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].signEndTime").value(hasItem(DEFAULT_SIGN_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].activityStartTime").value(hasItem(DEFAULT_ACTIVITY_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].activityEndTime").value(hasItem(DEFAULT_ACTIVITY_END_TIME.toString())));
    }

    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.descrption").value(DEFAULT_DESCRPTION.toString()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.signStartTime").value(DEFAULT_SIGN_START_TIME.toString()))
            .andExpect(jsonPath("$.signEndTime").value(DEFAULT_SIGN_END_TIME.toString()))
            .andExpect(jsonPath("$.activityStartTime").value(DEFAULT_ACTIVITY_START_TIME.toString()))
            .andExpect(jsonPath("$.activityEndTime").value(DEFAULT_ACTIVITY_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .title(UPDATED_TITLE)
            .descrption(UPDATED_DESCRPTION)
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .signStartTime(UPDATED_SIGN_START_TIME)
            .signEndTime(UPDATED_SIGN_END_TIME)
            .activityStartTime(UPDATED_ACTIVITY_START_TIME)
            .activityEndTime(UPDATED_ACTIVITY_END_TIME);
        ActivityDTO activityDTO = activityMapper.toDto(updatedActivity);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testActivity.getDescrption()).isEqualTo(UPDATED_DESCRPTION);
        assertThat(testActivity.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testActivity.getSignStartTime()).isEqualTo(UPDATED_SIGN_START_TIME);
        assertThat(testActivity.getSignEndTime()).isEqualTo(UPDATED_SIGN_END_TIME);
        assertThat(testActivity.getActivityStartTime()).isEqualTo(UPDATED_ACTIVITY_START_TIME);
        assertThat(testActivity.getActivityEndTime()).isEqualTo(UPDATED_ACTIVITY_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityDTO.class);
        ActivityDTO activityDTO1 = new ActivityDTO();
        activityDTO1.setId(1L);
        ActivityDTO activityDTO2 = new ActivityDTO();
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO2.setId(activityDTO1.getId());
        assertThat(activityDTO1).isEqualTo(activityDTO2);
        activityDTO2.setId(2L);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO1.setId(null);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityMapper.fromId(null)).isNull();
    }
}
