package vn.qlns.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import vn.qlns.domain.TrinhDoHV;
import vn.qlns.repository.TrinhDoHVRepository;
import vn.qlns.service.TrinhDoHVQueryService;
import vn.qlns.service.TrinhDoHVService;
import vn.qlns.service.criteria.TrinhDoHVCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.TrinhDoHV}.
 */
@RestController
@RequestMapping("/api")
public class TrinhDoHVResource {

    private final Logger log = LoggerFactory.getLogger(TrinhDoHVResource.class);

    private static final String ENTITY_NAME = "trinhDoHV";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrinhDoHVService trinhDoHVService;

    private final TrinhDoHVRepository trinhDoHVRepository;

    private final TrinhDoHVQueryService trinhDoHVQueryService;

    public TrinhDoHVResource(
        TrinhDoHVService trinhDoHVService,
        TrinhDoHVRepository trinhDoHVRepository,
        TrinhDoHVQueryService trinhDoHVQueryService
    ) {
        this.trinhDoHVService = trinhDoHVService;
        this.trinhDoHVRepository = trinhDoHVRepository;
        this.trinhDoHVQueryService = trinhDoHVQueryService;
    }

    /**
     * {@code POST  /trinh-do-hvs} : Create a new trinhDoHV.
     *
     * @param trinhDoHV the trinhDoHV to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trinhDoHV, or with status {@code 400 (Bad Request)} if the trinhDoHV has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trinh-do-hvs")
    public ResponseEntity<TrinhDoHV> createTrinhDoHV(@Valid @RequestBody TrinhDoHV trinhDoHV) throws URISyntaxException {
        log.debug("REST request to save TrinhDoHV : {}", trinhDoHV);
        if (trinhDoHV.getId() != null) {
            throw new BadRequestAlertException("A new trinhDoHV cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrinhDoHV result = trinhDoHVService.save(trinhDoHV);
        return ResponseEntity
            .created(new URI("/api/trinh-do-hvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trinh-do-hvs/:id} : Updates an existing trinhDoHV.
     *
     * @param id the id of the trinhDoHV to save.
     * @param trinhDoHV the trinhDoHV to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trinhDoHV,
     * or with status {@code 400 (Bad Request)} if the trinhDoHV is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trinhDoHV couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trinh-do-hvs/{id}")
    public ResponseEntity<TrinhDoHV> updateTrinhDoHV(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrinhDoHV trinhDoHV
    ) throws URISyntaxException {
        log.debug("REST request to update TrinhDoHV : {}, {}", id, trinhDoHV);
        if (trinhDoHV.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trinhDoHV.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trinhDoHVRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrinhDoHV result = trinhDoHVService.update(trinhDoHV);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trinhDoHV.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trinh-do-hvs/:id} : Partial updates given fields of an existing trinhDoHV, field will ignore if it is null
     *
     * @param id the id of the trinhDoHV to save.
     * @param trinhDoHV the trinhDoHV to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trinhDoHV,
     * or with status {@code 400 (Bad Request)} if the trinhDoHV is not valid,
     * or with status {@code 404 (Not Found)} if the trinhDoHV is not found,
     * or with status {@code 500 (Internal Server Error)} if the trinhDoHV couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trinh-do-hvs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrinhDoHV> partialUpdateTrinhDoHV(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrinhDoHV trinhDoHV
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrinhDoHV partially : {}, {}", id, trinhDoHV);
        if (trinhDoHV.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trinhDoHV.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trinhDoHVRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrinhDoHV> result = trinhDoHVService.partialUpdate(trinhDoHV);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trinhDoHV.getId().toString())
        );
    }

    /**
     * {@code GET  /trinh-do-hvs} : get all the trinhDoHVS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trinhDoHVS in body.
     */
    @GetMapping("/trinh-do-hvs")
    public ResponseEntity<List<TrinhDoHV>> getAllTrinhDoHVS(
        TrinhDoHVCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TrinhDoHVS by criteria: {}", criteria);
        Page<TrinhDoHV> page = trinhDoHVQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trinh-do-hvs/count} : count all the trinhDoHVS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trinh-do-hvs/count")
    public ResponseEntity<Long> countTrinhDoHVS(TrinhDoHVCriteria criteria) {
        log.debug("REST request to count TrinhDoHVS by criteria: {}", criteria);
        return ResponseEntity.ok().body(trinhDoHVQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trinh-do-hvs/:id} : get the "id" trinhDoHV.
     *
     * @param id the id of the trinhDoHV to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trinhDoHV, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trinh-do-hvs/{id}")
    public ResponseEntity<TrinhDoHV> getTrinhDoHV(@PathVariable Long id) {
        log.debug("REST request to get TrinhDoHV : {}", id);
        Optional<TrinhDoHV> trinhDoHV = trinhDoHVService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trinhDoHV);
    }

    /**
     * {@code DELETE  /trinh-do-hvs/:id} : delete the "id" trinhDoHV.
     *
     * @param id the id of the trinhDoHV to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trinh-do-hvs/{id}")
    public ResponseEntity<Void> deleteTrinhDoHV(@PathVariable Long id) {
        log.debug("REST request to delete TrinhDoHV : {}", id);
        trinhDoHVService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
