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
import vn.qlns.domain.ChuyenMon;
import vn.qlns.repository.ChuyenMonRepository;
import vn.qlns.service.ChuyenMonQueryService;
import vn.qlns.service.ChuyenMonService;
import vn.qlns.service.criteria.ChuyenMonCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.ChuyenMon}.
 */
@RestController
@RequestMapping("/api")
public class ChuyenMonResource {

    private final Logger log = LoggerFactory.getLogger(ChuyenMonResource.class);

    private static final String ENTITY_NAME = "chuyenMon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChuyenMonService chuyenMonService;

    private final ChuyenMonRepository chuyenMonRepository;

    private final ChuyenMonQueryService chuyenMonQueryService;

    public ChuyenMonResource(
        ChuyenMonService chuyenMonService,
        ChuyenMonRepository chuyenMonRepository,
        ChuyenMonQueryService chuyenMonQueryService
    ) {
        this.chuyenMonService = chuyenMonService;
        this.chuyenMonRepository = chuyenMonRepository;
        this.chuyenMonQueryService = chuyenMonQueryService;
    }

    /**
     * {@code POST  /chuyen-mons} : Create a new chuyenMon.
     *
     * @param chuyenMon the chuyenMon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chuyenMon, or with status {@code 400 (Bad Request)} if the chuyenMon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chuyen-mons")
    public ResponseEntity<ChuyenMon> createChuyenMon(@Valid @RequestBody ChuyenMon chuyenMon) throws URISyntaxException {
        log.debug("REST request to save ChuyenMon : {}", chuyenMon);
        if (chuyenMon.getId() != null) {
            throw new BadRequestAlertException("A new chuyenMon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChuyenMon result = chuyenMonService.save(chuyenMon);
        return ResponseEntity
            .created(new URI("/api/chuyen-mons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chuyen-mons/:id} : Updates an existing chuyenMon.
     *
     * @param id the id of the chuyenMon to save.
     * @param chuyenMon the chuyenMon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenMon,
     * or with status {@code 400 (Bad Request)} if the chuyenMon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chuyenMon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chuyen-mons/{id}")
    public ResponseEntity<ChuyenMon> updateChuyenMon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChuyenMon chuyenMon
    ) throws URISyntaxException {
        log.debug("REST request to update ChuyenMon : {}, {}", id, chuyenMon);
        if (chuyenMon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenMon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenMonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChuyenMon result = chuyenMonService.update(chuyenMon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chuyenMon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chuyen-mons/:id} : Partial updates given fields of an existing chuyenMon, field will ignore if it is null
     *
     * @param id the id of the chuyenMon to save.
     * @param chuyenMon the chuyenMon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenMon,
     * or with status {@code 400 (Bad Request)} if the chuyenMon is not valid,
     * or with status {@code 404 (Not Found)} if the chuyenMon is not found,
     * or with status {@code 500 (Internal Server Error)} if the chuyenMon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chuyen-mons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChuyenMon> partialUpdateChuyenMon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChuyenMon chuyenMon
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChuyenMon partially : {}, {}", id, chuyenMon);
        if (chuyenMon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenMon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenMonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChuyenMon> result = chuyenMonService.partialUpdate(chuyenMon);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chuyenMon.getId().toString())
        );
    }

    /**
     * {@code GET  /chuyen-mons} : get all the chuyenMons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chuyenMons in body.
     */
    @GetMapping("/chuyen-mons")
    public ResponseEntity<List<ChuyenMon>> getAllChuyenMons(
        ChuyenMonCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ChuyenMons by criteria: {}", criteria);
        Page<ChuyenMon> page = chuyenMonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chuyen-mons/count} : count all the chuyenMons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/chuyen-mons/count")
    public ResponseEntity<Long> countChuyenMons(ChuyenMonCriteria criteria) {
        log.debug("REST request to count ChuyenMons by criteria: {}", criteria);
        return ResponseEntity.ok().body(chuyenMonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chuyen-mons/:id} : get the "id" chuyenMon.
     *
     * @param id the id of the chuyenMon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chuyenMon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chuyen-mons/{id}")
    public ResponseEntity<ChuyenMon> getChuyenMon(@PathVariable Long id) {
        log.debug("REST request to get ChuyenMon : {}", id);
        Optional<ChuyenMon> chuyenMon = chuyenMonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chuyenMon);
    }

    /**
     * {@code DELETE  /chuyen-mons/:id} : delete the "id" chuyenMon.
     *
     * @param id the id of the chuyenMon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chuyen-mons/{id}")
    public ResponseEntity<Void> deleteChuyenMon(@PathVariable Long id) {
        log.debug("REST request to delete ChuyenMon : {}", id);
        chuyenMonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
