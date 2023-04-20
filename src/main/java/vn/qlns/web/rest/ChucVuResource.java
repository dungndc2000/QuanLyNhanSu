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
import vn.qlns.domain.ChucVu;
import vn.qlns.repository.ChucVuRepository;
import vn.qlns.service.ChucVuQueryService;
import vn.qlns.service.ChucVuService;
import vn.qlns.service.criteria.ChucVuCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.ChucVu}.
 */
@RestController
@RequestMapping("/api")
public class ChucVuResource {

    private final Logger log = LoggerFactory.getLogger(ChucVuResource.class);

    private static final String ENTITY_NAME = "chucVu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChucVuService chucVuService;

    private final ChucVuRepository chucVuRepository;

    private final ChucVuQueryService chucVuQueryService;

    public ChucVuResource(ChucVuService chucVuService, ChucVuRepository chucVuRepository, ChucVuQueryService chucVuQueryService) {
        this.chucVuService = chucVuService;
        this.chucVuRepository = chucVuRepository;
        this.chucVuQueryService = chucVuQueryService;
    }

    /**
     * {@code POST  /chuc-vus} : Create a new chucVu.
     *
     * @param chucVu the chucVu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chucVu, or with status {@code 400 (Bad Request)} if the chucVu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chuc-vus")
    public ResponseEntity<ChucVu> createChucVu(@Valid @RequestBody ChucVu chucVu) throws URISyntaxException {
        log.debug("REST request to save ChucVu : {}", chucVu);
        if (chucVu.getId() != null) {
            throw new BadRequestAlertException("A new chucVu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChucVu result = chucVuService.save(chucVu);
        return ResponseEntity
            .created(new URI("/api/chuc-vus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chuc-vus/:id} : Updates an existing chucVu.
     *
     * @param id the id of the chucVu to save.
     * @param chucVu the chucVu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chucVu,
     * or with status {@code 400 (Bad Request)} if the chucVu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chucVu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chuc-vus/{id}")
    public ResponseEntity<ChucVu> updateChucVu(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChucVu chucVu
    ) throws URISyntaxException {
        log.debug("REST request to update ChucVu : {}, {}", id, chucVu);
        if (chucVu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chucVu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chucVuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChucVu result = chucVuService.update(chucVu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chucVu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chuc-vus/:id} : Partial updates given fields of an existing chucVu, field will ignore if it is null
     *
     * @param id the id of the chucVu to save.
     * @param chucVu the chucVu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chucVu,
     * or with status {@code 400 (Bad Request)} if the chucVu is not valid,
     * or with status {@code 404 (Not Found)} if the chucVu is not found,
     * or with status {@code 500 (Internal Server Error)} if the chucVu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chuc-vus/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChucVu> partialUpdateChucVu(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChucVu chucVu
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChucVu partially : {}, {}", id, chucVu);
        if (chucVu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chucVu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chucVuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChucVu> result = chucVuService.partialUpdate(chucVu);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chucVu.getId().toString())
        );
    }

    /**
     * {@code GET  /chuc-vus} : get all the chucVus.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chucVus in body.
     */
    @GetMapping("/chuc-vus")
    public ResponseEntity<List<ChucVu>> getAllChucVus(
        ChucVuCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ChucVus by criteria: {}", criteria);
        Page<ChucVu> page = chucVuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chuc-vus/count} : count all the chucVus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/chuc-vus/count")
    public ResponseEntity<Long> countChucVus(ChucVuCriteria criteria) {
        log.debug("REST request to count ChucVus by criteria: {}", criteria);
        return ResponseEntity.ok().body(chucVuQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chuc-vus/:id} : get the "id" chucVu.
     *
     * @param id the id of the chucVu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chucVu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chuc-vus/{id}")
    public ResponseEntity<ChucVu> getChucVu(@PathVariable Long id) {
        log.debug("REST request to get ChucVu : {}", id);
        Optional<ChucVu> chucVu = chucVuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chucVu);
    }

    /**
     * {@code DELETE  /chuc-vus/:id} : delete the "id" chucVu.
     *
     * @param id the id of the chucVu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chuc-vus/{id}")
    public ResponseEntity<Void> deleteChucVu(@PathVariable Long id) {
        log.debug("REST request to delete ChucVu : {}", id);
        chucVuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
