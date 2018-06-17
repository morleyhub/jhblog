package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhblogApp;

import io.github.jhipster.application.domain.Pics;
import io.github.jhipster.application.repository.PicsRepository;
import io.github.jhipster.application.service.PicsService;
import io.github.jhipster.application.service.dto.PicsDTO;
import io.github.jhipster.application.service.mapper.PicsMapper;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PicsResource REST controller.
 *
 * @see PicsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhblogApp.class)
public class PicsResourceIntTest {

    private static final String DEFAULT_OSS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OSS_PATH = "BBBBBBBBBB";

    @Autowired
    private PicsRepository picsRepository;

    @Autowired
    private PicsMapper picsMapper;

    @Autowired
    private PicsService picsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPicsMockMvc;

    private Pics pics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PicsResource picsResource = new PicsResource(picsService);
        this.restPicsMockMvc = MockMvcBuilders.standaloneSetup(picsResource)
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
    public static Pics createEntity(EntityManager em) {
        Pics pics = new Pics()
            .ossPath(DEFAULT_OSS_PATH);
        return pics;
    }

    @Before
    public void initTest() {
        pics = createEntity(em);
    }

    @Test
    @Transactional
    public void createPics() throws Exception {
        int databaseSizeBeforeCreate = picsRepository.findAll().size();

        // Create the Pics
        PicsDTO picsDTO = picsMapper.toDto(pics);
        restPicsMockMvc.perform(post("/api/pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(picsDTO)))
            .andExpect(status().isCreated());

        // Validate the Pics in the database
        List<Pics> picsList = picsRepository.findAll();
        assertThat(picsList).hasSize(databaseSizeBeforeCreate + 1);
        Pics testPics = picsList.get(picsList.size() - 1);
        assertThat(testPics.getOssPath()).isEqualTo(DEFAULT_OSS_PATH);
    }

    @Test
    @Transactional
    public void createPicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = picsRepository.findAll().size();

        // Create the Pics with an existing ID
        pics.setId(1L);
        PicsDTO picsDTO = picsMapper.toDto(pics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPicsMockMvc.perform(post("/api/pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(picsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pics in the database
        List<Pics> picsList = picsRepository.findAll();
        assertThat(picsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPics() throws Exception {
        // Initialize the database
        picsRepository.saveAndFlush(pics);

        // Get all the picsList
        restPicsMockMvc.perform(get("/api/pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pics.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())));
    }

    @Test
    @Transactional
    public void getPics() throws Exception {
        // Initialize the database
        picsRepository.saveAndFlush(pics);

        // Get the pics
        restPicsMockMvc.perform(get("/api/pics/{id}", pics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pics.getId().intValue()))
            .andExpect(jsonPath("$.ossPath").value(DEFAULT_OSS_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPics() throws Exception {
        // Get the pics
        restPicsMockMvc.perform(get("/api/pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePics() throws Exception {
        // Initialize the database
        picsRepository.saveAndFlush(pics);
        int databaseSizeBeforeUpdate = picsRepository.findAll().size();

        // Update the pics
        Pics updatedPics = picsRepository.findOne(pics.getId());
        // Disconnect from session so that the updates on updatedPics are not directly saved in db
        em.detach(updatedPics);
        updatedPics
            .ossPath(UPDATED_OSS_PATH);
        PicsDTO picsDTO = picsMapper.toDto(updatedPics);

        restPicsMockMvc.perform(put("/api/pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(picsDTO)))
            .andExpect(status().isOk());

        // Validate the Pics in the database
        List<Pics> picsList = picsRepository.findAll();
        assertThat(picsList).hasSize(databaseSizeBeforeUpdate);
        Pics testPics = picsList.get(picsList.size() - 1);
        assertThat(testPics.getOssPath()).isEqualTo(UPDATED_OSS_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingPics() throws Exception {
        int databaseSizeBeforeUpdate = picsRepository.findAll().size();

        // Create the Pics
        PicsDTO picsDTO = picsMapper.toDto(pics);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPicsMockMvc.perform(put("/api/pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(picsDTO)))
            .andExpect(status().isCreated());

        // Validate the Pics in the database
        List<Pics> picsList = picsRepository.findAll();
        assertThat(picsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePics() throws Exception {
        // Initialize the database
        picsRepository.saveAndFlush(pics);
        int databaseSizeBeforeDelete = picsRepository.findAll().size();

        // Get the pics
        restPicsMockMvc.perform(delete("/api/pics/{id}", pics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pics> picsList = picsRepository.findAll();
        assertThat(picsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pics.class);
        Pics pics1 = new Pics();
        pics1.setId(1L);
        Pics pics2 = new Pics();
        pics2.setId(pics1.getId());
        assertThat(pics1).isEqualTo(pics2);
        pics2.setId(2L);
        assertThat(pics1).isNotEqualTo(pics2);
        pics1.setId(null);
        assertThat(pics1).isNotEqualTo(pics2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PicsDTO.class);
        PicsDTO picsDTO1 = new PicsDTO();
        picsDTO1.setId(1L);
        PicsDTO picsDTO2 = new PicsDTO();
        assertThat(picsDTO1).isNotEqualTo(picsDTO2);
        picsDTO2.setId(picsDTO1.getId());
        assertThat(picsDTO1).isEqualTo(picsDTO2);
        picsDTO2.setId(2L);
        assertThat(picsDTO1).isNotEqualTo(picsDTO2);
        picsDTO1.setId(null);
        assertThat(picsDTO1).isNotEqualTo(picsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(picsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(picsMapper.fromId(null)).isNull();
    }
}
