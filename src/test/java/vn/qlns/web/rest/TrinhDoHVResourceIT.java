package vn.qlns.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.IntegrationTest;
import vn.qlns.domain.NhanVien;
import vn.qlns.domain.TrinhDoHV;
import vn.qlns.repository.TrinhDoHVRepository;
import vn.qlns.service.criteria.TrinhDoHVCriteria;

/**
 * Integration tests for the {@link TrinhDoHVResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrinhDoHVResourceIT {

    private static final String DEFAULT_MA_TDHV = "AAAAAAAAAA";
    private static final String UPDATED_MA_TDHV = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_TDHV = "AAAAAAAAAA";
    private static final String UPDATED_TEN_TDHV = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trinh-do-hvs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrinhDoHVRepository trinhDoHVRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrinhDoHVMockMvc;

    private TrinhDoHV trinhDoHV;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrinhDoHV createEntity(EntityManager em) {
        TrinhDoHV trinhDoHV = new TrinhDoHV().maTDHV(DEFAULT_MA_TDHV).tenTDHV(DEFAULT_TEN_TDHV);
        return trinhDoHV;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrinhDoHV createUpdatedEntity(EntityManager em) {
        TrinhDoHV trinhDoHV = new TrinhDoHV().maTDHV(UPDATED_MA_TDHV).tenTDHV(UPDATED_TEN_TDHV);
        return trinhDoHV;
    }

    @BeforeEach
    public void initTest() {
        trinhDoHV = createEntity(em);
    }

    @Test
    @Transactional
    void createTrinhDoHV() throws Exception {
        int databaseSizeBeforeCreate = trinhDoHVRepository.findAll().size();
        // Create the TrinhDoHV
        restTrinhDoHVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trinhDoHV)))
            .andExpect(status().isCreated());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeCreate + 1);
        TrinhDoHV testTrinhDoHV = trinhDoHVList.get(trinhDoHVList.size() - 1);
        assertThat(testTrinhDoHV.getMaTDHV()).isEqualTo(DEFAULT_MA_TDHV);
        assertThat(testTrinhDoHV.getTenTDHV()).isEqualTo(DEFAULT_TEN_TDHV);
    }

    @Test
    @Transactional
    void createTrinhDoHVWithExistingId() throws Exception {
        // Create the TrinhDoHV with an existing ID
        trinhDoHV.setId(1L);

        int databaseSizeBeforeCreate = trinhDoHVRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrinhDoHVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trinhDoHV)))
            .andExpect(status().isBadRequest());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaTDHVIsRequired() throws Exception {
        int databaseSizeBeforeTest = trinhDoHVRepository.findAll().size();
        // set the field null
        trinhDoHV.setMaTDHV(null);

        // Create the TrinhDoHV, which fails.

        restTrinhDoHVMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trinhDoHV)))
            .andExpect(status().isBadRequest());

        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVS() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trinhDoHV.getId().intValue())))
            .andExpect(jsonPath("$.[*].maTDHV").value(hasItem(DEFAULT_MA_TDHV)))
            .andExpect(jsonPath("$.[*].tenTDHV").value(hasItem(DEFAULT_TEN_TDHV)));
    }

    @Test
    @Transactional
    void getTrinhDoHV() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get the trinhDoHV
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL_ID, trinhDoHV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trinhDoHV.getId().intValue()))
            .andExpect(jsonPath("$.maTDHV").value(DEFAULT_MA_TDHV))
            .andExpect(jsonPath("$.tenTDHV").value(DEFAULT_TEN_TDHV));
    }

    @Test
    @Transactional
    void getTrinhDoHVSByIdFiltering() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        Long id = trinhDoHV.getId();

        defaultTrinhDoHVShouldBeFound("id.equals=" + id);
        defaultTrinhDoHVShouldNotBeFound("id.notEquals=" + id);

        defaultTrinhDoHVShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrinhDoHVShouldNotBeFound("id.greaterThan=" + id);

        defaultTrinhDoHVShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrinhDoHVShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByMaTDHVIsEqualToSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where maTDHV equals to DEFAULT_MA_TDHV
        defaultTrinhDoHVShouldBeFound("maTDHV.equals=" + DEFAULT_MA_TDHV);

        // Get all the trinhDoHVList where maTDHV equals to UPDATED_MA_TDHV
        defaultTrinhDoHVShouldNotBeFound("maTDHV.equals=" + UPDATED_MA_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByMaTDHVIsInShouldWork() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where maTDHV in DEFAULT_MA_TDHV or UPDATED_MA_TDHV
        defaultTrinhDoHVShouldBeFound("maTDHV.in=" + DEFAULT_MA_TDHV + "," + UPDATED_MA_TDHV);

        // Get all the trinhDoHVList where maTDHV equals to UPDATED_MA_TDHV
        defaultTrinhDoHVShouldNotBeFound("maTDHV.in=" + UPDATED_MA_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByMaTDHVIsNullOrNotNull() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where maTDHV is not null
        defaultTrinhDoHVShouldBeFound("maTDHV.specified=true");

        // Get all the trinhDoHVList where maTDHV is null
        defaultTrinhDoHVShouldNotBeFound("maTDHV.specified=false");
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByMaTDHVContainsSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where maTDHV contains DEFAULT_MA_TDHV
        defaultTrinhDoHVShouldBeFound("maTDHV.contains=" + DEFAULT_MA_TDHV);

        // Get all the trinhDoHVList where maTDHV contains UPDATED_MA_TDHV
        defaultTrinhDoHVShouldNotBeFound("maTDHV.contains=" + UPDATED_MA_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByMaTDHVNotContainsSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where maTDHV does not contain DEFAULT_MA_TDHV
        defaultTrinhDoHVShouldNotBeFound("maTDHV.doesNotContain=" + DEFAULT_MA_TDHV);

        // Get all the trinhDoHVList where maTDHV does not contain UPDATED_MA_TDHV
        defaultTrinhDoHVShouldBeFound("maTDHV.doesNotContain=" + UPDATED_MA_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByTenTDHVIsEqualToSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where tenTDHV equals to DEFAULT_TEN_TDHV
        defaultTrinhDoHVShouldBeFound("tenTDHV.equals=" + DEFAULT_TEN_TDHV);

        // Get all the trinhDoHVList where tenTDHV equals to UPDATED_TEN_TDHV
        defaultTrinhDoHVShouldNotBeFound("tenTDHV.equals=" + UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByTenTDHVIsInShouldWork() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where tenTDHV in DEFAULT_TEN_TDHV or UPDATED_TEN_TDHV
        defaultTrinhDoHVShouldBeFound("tenTDHV.in=" + DEFAULT_TEN_TDHV + "," + UPDATED_TEN_TDHV);

        // Get all the trinhDoHVList where tenTDHV equals to UPDATED_TEN_TDHV
        defaultTrinhDoHVShouldNotBeFound("tenTDHV.in=" + UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByTenTDHVIsNullOrNotNull() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where tenTDHV is not null
        defaultTrinhDoHVShouldBeFound("tenTDHV.specified=true");

        // Get all the trinhDoHVList where tenTDHV is null
        defaultTrinhDoHVShouldNotBeFound("tenTDHV.specified=false");
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByTenTDHVContainsSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where tenTDHV contains DEFAULT_TEN_TDHV
        defaultTrinhDoHVShouldBeFound("tenTDHV.contains=" + DEFAULT_TEN_TDHV);

        // Get all the trinhDoHVList where tenTDHV contains UPDATED_TEN_TDHV
        defaultTrinhDoHVShouldNotBeFound("tenTDHV.contains=" + UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByTenTDHVNotContainsSomething() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        // Get all the trinhDoHVList where tenTDHV does not contain DEFAULT_TEN_TDHV
        defaultTrinhDoHVShouldNotBeFound("tenTDHV.doesNotContain=" + DEFAULT_TEN_TDHV);

        // Get all the trinhDoHVList where tenTDHV does not contain UPDATED_TEN_TDHV
        defaultTrinhDoHVShouldBeFound("tenTDHV.doesNotContain=" + UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void getAllTrinhDoHVSByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            trinhDoHVRepository.saveAndFlush(trinhDoHV);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        trinhDoHV.addNhanVien(nhanVien);
        trinhDoHVRepository.saveAndFlush(trinhDoHV);
        Long nhanVienId = nhanVien.getId();

        // Get all the trinhDoHVList where nhanVien equals to nhanVienId
        defaultTrinhDoHVShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the trinhDoHVList where nhanVien equals to (nhanVienId + 1)
        defaultTrinhDoHVShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrinhDoHVShouldBeFound(String filter) throws Exception {
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trinhDoHV.getId().intValue())))
            .andExpect(jsonPath("$.[*].maTDHV").value(hasItem(DEFAULT_MA_TDHV)))
            .andExpect(jsonPath("$.[*].tenTDHV").value(hasItem(DEFAULT_TEN_TDHV)));

        // Check, that the count call also returns 1
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrinhDoHVShouldNotBeFound(String filter) throws Exception {
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrinhDoHVMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTrinhDoHV() throws Exception {
        // Get the trinhDoHV
        restTrinhDoHVMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrinhDoHV() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();

        // Update the trinhDoHV
        TrinhDoHV updatedTrinhDoHV = trinhDoHVRepository.findById(trinhDoHV.getId()).get();
        // Disconnect from session so that the updates on updatedTrinhDoHV are not directly saved in db
        em.detach(updatedTrinhDoHV);
        updatedTrinhDoHV.maTDHV(UPDATED_MA_TDHV).tenTDHV(UPDATED_TEN_TDHV);

        restTrinhDoHVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrinhDoHV.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrinhDoHV))
            )
            .andExpect(status().isOk());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
        TrinhDoHV testTrinhDoHV = trinhDoHVList.get(trinhDoHVList.size() - 1);
        assertThat(testTrinhDoHV.getMaTDHV()).isEqualTo(UPDATED_MA_TDHV);
        assertThat(testTrinhDoHV.getTenTDHV()).isEqualTo(UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void putNonExistingTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trinhDoHV.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trinhDoHV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trinhDoHV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trinhDoHV)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrinhDoHVWithPatch() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();

        // Update the trinhDoHV using partial update
        TrinhDoHV partialUpdatedTrinhDoHV = new TrinhDoHV();
        partialUpdatedTrinhDoHV.setId(trinhDoHV.getId());

        partialUpdatedTrinhDoHV.tenTDHV(UPDATED_TEN_TDHV);

        restTrinhDoHVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrinhDoHV.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrinhDoHV))
            )
            .andExpect(status().isOk());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
        TrinhDoHV testTrinhDoHV = trinhDoHVList.get(trinhDoHVList.size() - 1);
        assertThat(testTrinhDoHV.getMaTDHV()).isEqualTo(DEFAULT_MA_TDHV);
        assertThat(testTrinhDoHV.getTenTDHV()).isEqualTo(UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void fullUpdateTrinhDoHVWithPatch() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();

        // Update the trinhDoHV using partial update
        TrinhDoHV partialUpdatedTrinhDoHV = new TrinhDoHV();
        partialUpdatedTrinhDoHV.setId(trinhDoHV.getId());

        partialUpdatedTrinhDoHV.maTDHV(UPDATED_MA_TDHV).tenTDHV(UPDATED_TEN_TDHV);

        restTrinhDoHVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrinhDoHV.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrinhDoHV))
            )
            .andExpect(status().isOk());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
        TrinhDoHV testTrinhDoHV = trinhDoHVList.get(trinhDoHVList.size() - 1);
        assertThat(testTrinhDoHV.getMaTDHV()).isEqualTo(UPDATED_MA_TDHV);
        assertThat(testTrinhDoHV.getTenTDHV()).isEqualTo(UPDATED_TEN_TDHV);
    }

    @Test
    @Transactional
    void patchNonExistingTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trinhDoHV.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trinhDoHV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trinhDoHV))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrinhDoHV() throws Exception {
        int databaseSizeBeforeUpdate = trinhDoHVRepository.findAll().size();
        trinhDoHV.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrinhDoHVMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trinhDoHV))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrinhDoHV in the database
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrinhDoHV() throws Exception {
        // Initialize the database
        trinhDoHVRepository.saveAndFlush(trinhDoHV);

        int databaseSizeBeforeDelete = trinhDoHVRepository.findAll().size();

        // Delete the trinhDoHV
        restTrinhDoHVMockMvc
            .perform(delete(ENTITY_API_URL_ID, trinhDoHV.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrinhDoHV> trinhDoHVList = trinhDoHVRepository.findAll();
        assertThat(trinhDoHVList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
