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
import vn.qlns.domain.KhenThuong;
import vn.qlns.repository.KhenThuongRepository;
import vn.qlns.service.KhenThuongQueryService;
import vn.qlns.service.KhenThuongService;
import vn.qlns.service.criteria.KhenThuongCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.KhenThuong}.
 */
@RestController
@RequestMapping("/api")
public class KhenThuongResource {

    private final Logger log = LoggerFactory.getLogger(KhenThuongResource.class);

    private static final String ENTITY_NAME = "khenThuong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KhenThuongService khenThuongService;

    private final KhenThuongRepository khenThuongRepository;

    private final KhenThuongQueryService khenThuongQueryService;

    public KhenThuongResource(
        KhenThuongService khenThuongService,
        KhenThuongRepository khenThuongRepository,
        KhenThuongQueryService khenThuongQueryService
    ) {
        this.khenThuongService = khenThuongService;
        this.khenThuongRepository = khenThuongRepository;
        this.khenThuongQueryService = khenThuongQueryService;
    }

    /**
     * {@code POST  /khen-thuongs} : Create a new khenThuong.
     *
     * @param khenThuong the khenThuong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new khenThuong, or with status {@code 400 (Bad Request)} if the khenThuong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/khen-thuongs")
    public ResponseEntity<KhenThuong> createKhenThuong(@Valid @RequestBody KhenThuong khenThuong) throws URISyntaxException {
        log.debug("REST request to save KhenThuong : {}", khenThuong);
        if (khenThuong.getId() != null) {
            throw new BadRequestAlertException("A new khenThuong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KhenThuong result = khenThuongService.save(khenThuong);
        return ResponseEntity
            .created(new URI("/api/khen-thuongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /khen-thuongs/:id} : Updates an existing khenThuong.
     *
     * @param id the id of the khenThuong to save.
     * @param khenThuong the khenThuong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khenThuong,
     * or with status {@code 400 (Bad Request)} if the khenThuong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the khenThuong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/khen-thuongs/{id}")
    public ResponseEntity<KhenThuong> updateKhenThuong(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KhenThuong khenThuong
    ) throws URISyntaxException {
        log.debug("REST request to update KhenThuong : {}, {}", id, khenThuong);
        if (khenThuong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khenThuong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khenThuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KhenThuong result = khenThuongService.update(khenThuong);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khenThuong.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /khen-thuongs/:id} : Partial updates given fields of an existing khenThuong, field will ignore if it is null
     *
     * @param id the id of the khenThuong to save.
     * @param khenThuong the khenThuong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khenThuong,
     * or with status {@code 400 (Bad Request)} if the khenThuong is not valid,
     * or with status {@code 404 (Not Found)} if the khenThuong is not found,
     * or with status {@code 500 (Internal Server Error)} if the khenThuong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/khen-thuongs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KhenThuong> partialUpdateKhenThuong(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KhenThuong khenThuong
    ) throws URISyntaxException {
        log.debug("REST request to partial update KhenThuong partially : {}, {}", id, khenThuong);
        if (khenThuong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khenThuong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khenThuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KhenThuong> result = khenThuongService.partialUpdate(khenThuong);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khenThuong.getId().toString())
        );
    }

    /**
     * {@code GET  /khen-thuongs} : get all the khenThuongs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of khenThuongs in body.
     */
    @GetMapping("/khen-thuongs")
    public ResponseEntity<List<KhenThuong>> getAllKhenThuongs(
        KhenThuongCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get KhenThuongs by criteria: {}", criteria);
        Page<KhenThuong> page = khenThuongQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /khen-thuongs/count} : count all the khenThuongs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/khen-thuongs/count")
    public ResponseEntity<Long> countKhenThuongs(KhenThuongCriteria criteria) {
        log.debug("REST request to count KhenThuongs by criteria: {}", criteria);
        return ResponseEntity.ok().body(khenThuongQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /khen-thuongs/:id} : get the "id" khenThuong.
     *
     * @param id the id of the khenThuong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the khenThuong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/khen-thuongs/{id}")
    public ResponseEntity<KhenThuong> getKhenThuong(@PathVariable Long id) {
        log.debug("REST request to get KhenThuong : {}", id);
        Optional<KhenThuong> khenThuong = khenThuongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(khenThuong);
    }

    /**
     * {@code DELETE  /khen-thuongs/:id} : delete the "id" khenThuong.
     *
     * @param id the id of the khenThuong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/khen-thuongs/{id}")
    public ResponseEntity<Void> deleteKhenThuong(@PathVariable Long id) {
        log.debug("REST request to delete KhenThuong : {}", id);
        khenThuongService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
