package vn.qlns.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
import vn.qlns.domain.NguoiThan;
import vn.qlns.repository.NguoiThanRepository;
import vn.qlns.service.NguoiThanQueryService;
import vn.qlns.service.NguoiThanService;
import vn.qlns.service.criteria.NguoiThanCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.NguoiThan}.
 */
@RestController
@RequestMapping("/api")
public class NguoiThanResource {

    private final Logger log = LoggerFactory.getLogger(NguoiThanResource.class);

    private static final String ENTITY_NAME = "nguoiThan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NguoiThanService nguoiThanService;

    private final NguoiThanRepository nguoiThanRepository;

    private final NguoiThanQueryService nguoiThanQueryService;

    public NguoiThanResource(
        NguoiThanService nguoiThanService,
        NguoiThanRepository nguoiThanRepository,
        NguoiThanQueryService nguoiThanQueryService
    ) {
        this.nguoiThanService = nguoiThanService;
        this.nguoiThanRepository = nguoiThanRepository;
        this.nguoiThanQueryService = nguoiThanQueryService;
    }

    /**
     * {@code POST  /nguoi-thans} : Create a new nguoiThan.
     *
     * @param nguoiThan the nguoiThan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nguoiThan, or with status {@code 400 (Bad Request)} if the nguoiThan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nguoi-thans")
    public ResponseEntity<NguoiThan> createNguoiThan(@Valid @RequestBody NguoiThan nguoiThan) throws URISyntaxException {
        log.debug("REST request to save NguoiThan : {}", nguoiThan);
        if (nguoiThan.getId() != null) {
            throw new BadRequestAlertException("A new nguoiThan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NguoiThan result = nguoiThanService.save(nguoiThan);
        return ResponseEntity
            .created(new URI("/api/nguoi-thans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nguoi-thans/:id} : Updates an existing nguoiThan.
     *
     * @param id the id of the nguoiThan to save.
     * @param nguoiThan the nguoiThan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nguoiThan,
     * or with status {@code 400 (Bad Request)} if the nguoiThan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nguoiThan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nguoi-thans/{id}")
    public ResponseEntity<NguoiThan> updateNguoiThan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NguoiThan nguoiThan
    ) throws URISyntaxException {
        log.debug("REST request to update NguoiThan : {}, {}", id, nguoiThan);
        if (nguoiThan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nguoiThan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nguoiThanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NguoiThan result = nguoiThanService.update(nguoiThan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nguoiThan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nguoi-thans/:id} : Partial updates given fields of an existing nguoiThan, field will ignore if it is null
     *
     * @param id the id of the nguoiThan to save.
     * @param nguoiThan the nguoiThan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nguoiThan,
     * or with status {@code 400 (Bad Request)} if the nguoiThan is not valid,
     * or with status {@code 404 (Not Found)} if the nguoiThan is not found,
     * or with status {@code 500 (Internal Server Error)} if the nguoiThan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nguoi-thans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NguoiThan> partialUpdateNguoiThan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NguoiThan nguoiThan
    ) throws URISyntaxException {
        log.debug("REST request to partial update NguoiThan partially : {}, {}", id, nguoiThan);
        if (nguoiThan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nguoiThan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nguoiThanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NguoiThan> result = nguoiThanService.partialUpdate(nguoiThan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nguoiThan.getId().toString())
        );
    }

    /**
     * {@code GET  /nguoi-thans} : get all the nguoiThans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nguoiThans in body.
     */
    @GetMapping("/nguoi-thans")
    public ResponseEntity<List<NguoiThan>> getAllNguoiThans(
        NguoiThanCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NguoiThans by criteria: {}", criteria);
        Page<NguoiThan> page = nguoiThanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nguoi-thans/count} : count all the nguoiThans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nguoi-thans/count")
    public ResponseEntity<Long> countNguoiThans(NguoiThanCriteria criteria) {
        log.debug("REST request to count NguoiThans by criteria: {}", criteria);
        return ResponseEntity.ok().body(nguoiThanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nguoi-thans/:id} : get the "id" nguoiThan.
     *
     * @param id the id of the nguoiThan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nguoiThan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nguoi-thans/{id}")
    public ResponseEntity<NguoiThan> getNguoiThan(@PathVariable Long id) {
        log.debug("REST request to get NguoiThan : {}", id);
        Optional<NguoiThan> nguoiThan = nguoiThanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nguoiThan);
    }

    /**
     * {@code DELETE  /nguoi-thans/:id} : delete the "id" nguoiThan.
     *
     * @param id the id of the nguoiThan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nguoi-thans/{id}")
    public ResponseEntity<Void> deleteNguoiThan(@PathVariable Long id) {
        log.debug("REST request to delete NguoiThan : {}", id);
        nguoiThanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
