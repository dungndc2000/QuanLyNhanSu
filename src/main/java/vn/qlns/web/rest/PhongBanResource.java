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
import vn.qlns.domain.PhongBan;
import vn.qlns.repository.PhongBanRepository;
import vn.qlns.service.PhongBanQueryService;
import vn.qlns.service.PhongBanService;
import vn.qlns.service.criteria.PhongBanCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.PhongBan}.
 */
@RestController
@RequestMapping("/api")
public class PhongBanResource {

    private final Logger log = LoggerFactory.getLogger(PhongBanResource.class);

    private static final String ENTITY_NAME = "phongBan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongBanService phongBanService;

    private final PhongBanRepository phongBanRepository;

    private final PhongBanQueryService phongBanQueryService;

    public PhongBanResource(
        PhongBanService phongBanService,
        PhongBanRepository phongBanRepository,
        PhongBanQueryService phongBanQueryService
    ) {
        this.phongBanService = phongBanService;
        this.phongBanRepository = phongBanRepository;
        this.phongBanQueryService = phongBanQueryService;
    }

    /**
     * {@code POST  /phong-bans} : Create a new phongBan.
     *
     * @param phongBan the phongBan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongBan, or with status {@code 400 (Bad Request)} if the phongBan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phong-bans")
    public ResponseEntity<PhongBan> createPhongBan(@Valid @RequestBody PhongBan phongBan) throws URISyntaxException {
        log.debug("REST request to save PhongBan : {}", phongBan);
        if (phongBan.getId() != null) {
            throw new BadRequestAlertException("A new phongBan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhongBan result = phongBanService.save(phongBan);
        return ResponseEntity
            .created(new URI("/api/phong-bans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phong-bans/:id} : Updates an existing phongBan.
     *
     * @param id the id of the phongBan to save.
     * @param phongBan the phongBan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongBan,
     * or with status {@code 400 (Bad Request)} if the phongBan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongBan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phong-bans/{id}")
    public ResponseEntity<PhongBan> updatePhongBan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PhongBan phongBan
    ) throws URISyntaxException {
        log.debug("REST request to update PhongBan : {}, {}", id, phongBan);
        if (phongBan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongBan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongBanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhongBan result = phongBanService.update(phongBan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongBan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phong-bans/:id} : Partial updates given fields of an existing phongBan, field will ignore if it is null
     *
     * @param id the id of the phongBan to save.
     * @param phongBan the phongBan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongBan,
     * or with status {@code 400 (Bad Request)} if the phongBan is not valid,
     * or with status {@code 404 (Not Found)} if the phongBan is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongBan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phong-bans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhongBan> partialUpdatePhongBan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PhongBan phongBan
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhongBan partially : {}, {}", id, phongBan);
        if (phongBan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongBan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongBanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhongBan> result = phongBanService.partialUpdate(phongBan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongBan.getId().toString())
        );
    }

    /**
     * {@code GET  /phong-bans} : get all the phongBans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongBans in body.
     */
    @GetMapping("/phong-bans")
    public ResponseEntity<List<PhongBan>> getAllPhongBans(
        PhongBanCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PhongBans by criteria: {}", criteria);
        Page<PhongBan> page = phongBanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phong-bans/count} : count all the phongBans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/phong-bans/count")
    public ResponseEntity<Long> countPhongBans(PhongBanCriteria criteria) {
        log.debug("REST request to count PhongBans by criteria: {}", criteria);
        return ResponseEntity.ok().body(phongBanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /phong-bans/:id} : get the "id" phongBan.
     *
     * @param id the id of the phongBan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongBan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phong-bans/{id}")
    public ResponseEntity<PhongBan> getPhongBan(@PathVariable Long id) {
        log.debug("REST request to get PhongBan : {}", id);
        Optional<PhongBan> phongBan = phongBanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phongBan);
    }

    /**
     * {@code DELETE  /phong-bans/:id} : delete the "id" phongBan.
     *
     * @param id the id of the phongBan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phong-bans/{id}")
    public ResponseEntity<Void> deletePhongBan(@PathVariable Long id) {
        log.debug("REST request to delete PhongBan : {}", id);
        phongBanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
