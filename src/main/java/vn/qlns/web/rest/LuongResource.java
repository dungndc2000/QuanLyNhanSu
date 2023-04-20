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
import vn.qlns.domain.Luong;
import vn.qlns.repository.LuongRepository;
import vn.qlns.service.LuongQueryService;
import vn.qlns.service.LuongService;
import vn.qlns.service.criteria.LuongCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.Luong}.
 */
@RestController
@RequestMapping("/api")
public class LuongResource {

    private final Logger log = LoggerFactory.getLogger(LuongResource.class);

    private static final String ENTITY_NAME = "luong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LuongService luongService;

    private final LuongRepository luongRepository;

    private final LuongQueryService luongQueryService;

    public LuongResource(LuongService luongService, LuongRepository luongRepository, LuongQueryService luongQueryService) {
        this.luongService = luongService;
        this.luongRepository = luongRepository;
        this.luongQueryService = luongQueryService;
    }

    /**
     * {@code POST  /luongs} : Create a new luong.
     *
     * @param luong the luong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new luong, or with status {@code 400 (Bad Request)} if the luong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/luongs")
    public ResponseEntity<Luong> createLuong(@Valid @RequestBody Luong luong) throws URISyntaxException {
        log.debug("REST request to save Luong : {}", luong);
        if (luong.getId() != null) {
            throw new BadRequestAlertException("A new luong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Luong result = luongService.save(luong);
        return ResponseEntity
            .created(new URI("/api/luongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /luongs/:id} : Updates an existing luong.
     *
     * @param id the id of the luong to save.
     * @param luong the luong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated luong,
     * or with status {@code 400 (Bad Request)} if the luong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the luong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/luongs/{id}")
    public ResponseEntity<Luong> updateLuong(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Luong luong)
        throws URISyntaxException {
        log.debug("REST request to update Luong : {}, {}", id, luong);
        if (luong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, luong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!luongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Luong result = luongService.update(luong);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, luong.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /luongs/:id} : Partial updates given fields of an existing luong, field will ignore if it is null
     *
     * @param id the id of the luong to save.
     * @param luong the luong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated luong,
     * or with status {@code 400 (Bad Request)} if the luong is not valid,
     * or with status {@code 404 (Not Found)} if the luong is not found,
     * or with status {@code 500 (Internal Server Error)} if the luong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/luongs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Luong> partialUpdateLuong(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Luong luong
    ) throws URISyntaxException {
        log.debug("REST request to partial update Luong partially : {}, {}", id, luong);
        if (luong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, luong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!luongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Luong> result = luongService.partialUpdate(luong);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, luong.getId().toString())
        );
    }

    /**
     * {@code GET  /luongs} : get all the luongs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of luongs in body.
     */
    @GetMapping("/luongs")
    public ResponseEntity<List<Luong>> getAllLuongs(
        LuongCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Luongs by criteria: {}", criteria);
        Page<Luong> page = luongQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /luongs/count} : count all the luongs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/luongs/count")
    public ResponseEntity<Long> countLuongs(LuongCriteria criteria) {
        log.debug("REST request to count Luongs by criteria: {}", criteria);
        return ResponseEntity.ok().body(luongQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /luongs/:id} : get the "id" luong.
     *
     * @param id the id of the luong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the luong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/luongs/{id}")
    public ResponseEntity<Luong> getLuong(@PathVariable Long id) {
        log.debug("REST request to get Luong : {}", id);
        Optional<Luong> luong = luongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(luong);
    }

    /**
     * {@code DELETE  /luongs/:id} : delete the "id" luong.
     *
     * @param id the id of the luong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/luongs/{id}")
    public ResponseEntity<Void> deleteLuong(@PathVariable Long id) {
        log.debug("REST request to delete Luong : {}", id);
        luongService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
