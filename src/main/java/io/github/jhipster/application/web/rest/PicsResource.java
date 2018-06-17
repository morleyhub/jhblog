package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.PicsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.PicsDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing Pics.
 */
@RestController
@RequestMapping("/api")
public class PicsResource {

    private final Logger log = LoggerFactory.getLogger(PicsResource.class);

    private static final String ENTITY_NAME = "pics";

    private final PicsService picsService;

    public PicsResource(PicsService picsService) {
        this.picsService = picsService;
    }

    /**
     * POST  /pics : Create a new pics.
     *
     * @param picsDTO the picsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new picsDTO, or with status 400 (Bad Request) if the pics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pics")
    @Timed
    public ResponseEntity<PicsDTO> createPics(@Valid @RequestBody PicsDTO picsDTO) throws URISyntaxException {
        log.debug("REST request to save Pics : {}", picsDTO);
        if (picsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PicsDTO result = picsService.save(picsDTO);
        return ResponseEntity.created(new URI("/api/pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pics : Updates an existing pics.
     *
     * @param picsDTO the picsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated picsDTO,
     * or with status 400 (Bad Request) if the picsDTO is not valid,
     * or with status 500 (Internal Server Error) if the picsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pics")
    @Timed
    public ResponseEntity<PicsDTO> updatePics(@Valid @RequestBody PicsDTO picsDTO) throws URISyntaxException {
        log.debug("REST request to update Pics : {}", picsDTO);
        if (picsDTO.getId() == null) {
            return createPics(picsDTO);
        }
        PicsDTO result = picsService.save(picsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, picsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pics : get all the pics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pics in body
     */
    @GetMapping("/pics")
    @Timed
    public List<PicsDTO> getAllPics() {
        log.debug("REST request to get all Pics");
        return picsService.findAll();
        }

    /**
     * GET  /pics/:id : get the "id" pics.
     *
     * @param id the id of the picsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the picsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pics/{id}")
    @Timed
    public ResponseEntity<PicsDTO> getPics(@PathVariable Long id) {
        log.debug("REST request to get Pics : {}", id);
        PicsDTO picsDTO = picsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(picsDTO));
    }

    /**
     * DELETE  /pics/:id : delete the "id" pics.
     *
     * @param id the id of the picsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pics/{id}")
    @Timed
    public ResponseEntity<Void> deletePics(@PathVariable Long id) {
        log.debug("REST request to delete Pics : {}", id);
        picsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
