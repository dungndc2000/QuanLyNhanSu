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
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.NhanVienRepository;
import vn.qlns.service.NhanVienQueryService;
import vn.qlns.service.NhanVienService;
import vn.qlns.service.criteria.NhanVienCriteria;
import vn.qlns.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.qlns.domain.NhanVien}.
 */
@RestController
@RequestMapping("/api")
public class NhanVienResource {

    private final Logger log = LoggerFactory.getLogger(NhanVienResource.class);

    private static final String ENTITY_NAME = "nhanVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhanVienService nhanVienService;

    private final NhanVienRepository nhanVienRepository;

    private final NhanVienQueryService nhanVienQueryService;

    public NhanVienResource(
        NhanVienService nhanVienService,
        NhanVienRepository nhanVienRepository,
        NhanVienQueryService nhanVienQueryService
    ) {
        this.nhanVienService = nhanVienService;
        this.nhanVienRepository = nhanVienRepository;
        this.nhanVienQueryService = nhanVienQueryService;
    }

    /**
     * {@code POST  /nhan-viens} : Create a new nhanVien.
     *
     * @param nhanVien the nhanVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhanVien, or with status {@code 400 (Bad Request)} if the nhanVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhan-viens")
    public ResponseEntity<NhanVien> createNhanVien(@Valid @RequestBody NhanVien nhanVien) throws URISyntaxException {
        log.debug("REST request to save NhanVien : {}", nhanVien);
        if (nhanVien.getId() != null) {
            throw new BadRequestAlertException("A new nhanVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhanVien result = nhanVienService.save(nhanVien);
        return ResponseEntity
            .created(new URI("/api/nhan-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhan-viens/:id} : Updates an existing nhanVien.
     *
     * @param id the id of the nhanVien to save.
     * @param nhanVien the nhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVien,
     * or with status {@code 400 (Bad Request)} if the nhanVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVien> updateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NhanVien nhanVien
    ) throws URISyntaxException {
        log.debug("REST request to update NhanVien : {}, {}", id, nhanVien);
        if (nhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhanVien result = nhanVienService.update(nhanVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanVien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhan-viens/:id} : Partial updates given fields of an existing nhanVien, field will ignore if it is null
     *
     * @param id the id of the nhanVien to save.
     * @param nhanVien the nhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVien,
     * or with status {@code 400 (Bad Request)} if the nhanVien is not valid,
     * or with status {@code 404 (Not Found)} if the nhanVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhan-viens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NhanVien> partialUpdateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NhanVien nhanVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhanVien partially : {}, {}", id, nhanVien);
        if (nhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhanVien> result = nhanVienService.partialUpdate(nhanVien);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanVien.getId().toString())
        );
    }

    /**
     * {@code GET  /nhan-viens} : get all the nhanViens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhanViens in body.
     */
    @GetMapping("/nhan-viens")
    public ResponseEntity<List<NhanVien>> getAllNhanViens(
        NhanVienCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NhanViens by criteria: {}", criteria);
        Page<NhanVien> page = nhanVienQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nhan-viens/count} : count all the nhanViens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nhan-viens/count")
    public ResponseEntity<Long> countNhanViens(NhanVienCriteria criteria) {
        log.debug("REST request to count NhanViens by criteria: {}", criteria);
        return ResponseEntity.ok().body(nhanVienQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nhan-viens/:id} : get the "id" nhanVien.
     *
     * @param id the id of the nhanVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhanVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVien> getNhanVien(@PathVariable Long id) {
        log.debug("REST request to get NhanVien : {}", id);
        Optional<NhanVien> nhanVien = nhanVienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhanVien);
    }

    /**
     * {@code DELETE  /nhan-viens/:id} : delete the "id" nhanVien.
     *
     * @param id the id of the nhanVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhan-viens/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Long id) {
        log.debug("REST request to delete NhanVien : {}", id);
        nhanVienService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/getNvBymaNV")
    public ResponseEntity<List<NhanVien>> getNhanVienByMaNV(@RequestParam String maNV) {
        log.debug("REST request to get NhanVien : {}", maNV);
        List<NhanVien> nhanVien = nhanVienRepository.getNhanViensByMaNV(maNV);
        return ResponseEntity.ok().body(nhanVien);
    }
}
