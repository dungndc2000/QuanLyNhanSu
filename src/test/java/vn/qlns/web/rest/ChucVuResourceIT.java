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
import vn.qlns.domain.ChucVu;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.ChucVuRepository;
import vn.qlns.service.criteria.ChucVuCriteria;

/**
 * Integration tests for the {@link ChucVuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChucVuResourceIT {

    private static final String DEFAULT_MA_CV = "AAAAAAAAAA";
    private static final String UPDATED_MA_CV = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_CHUC_VU = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHUC_VU = "BBBBBBBBBB";

    private static final String DEFAULT_PHU_CAP = "AAAAAAAAAA";
    private static final String UPDATED_PHU_CAP = "BBBBBBBBBB";

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chuc-vus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChucVuMockMvc;

    private ChucVu chucVu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChucVu createEntity(EntityManager em) {
        ChucVu chucVu = new ChucVu().maCV(DEFAULT_MA_CV).tenChucVu(DEFAULT_TEN_CHUC_VU).phuCap(DEFAULT_PHU_CAP).ghiChu(DEFAULT_GHI_CHU);
        return chucVu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChucVu createUpdatedEntity(EntityManager em) {
        ChucVu chucVu = new ChucVu().maCV(UPDATED_MA_CV).tenChucVu(UPDATED_TEN_CHUC_VU).phuCap(UPDATED_PHU_CAP).ghiChu(UPDATED_GHI_CHU);
        return chucVu;
    }

    @BeforeEach
    public void initTest() {
        chucVu = createEntity(em);
    }

    @Test
    @Transactional
    void createChucVu() throws Exception {
        int databaseSizeBeforeCreate = chucVuRepository.findAll().size();
        // Create the ChucVu
        restChucVuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chucVu)))
            .andExpect(status().isCreated());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeCreate + 1);
        ChucVu testChucVu = chucVuList.get(chucVuList.size() - 1);
        assertThat(testChucVu.getMaCV()).isEqualTo(DEFAULT_MA_CV);
        assertThat(testChucVu.getTenChucVu()).isEqualTo(DEFAULT_TEN_CHUC_VU);
        assertThat(testChucVu.getPhuCap()).isEqualTo(DEFAULT_PHU_CAP);
        assertThat(testChucVu.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void createChucVuWithExistingId() throws Exception {
        // Create the ChucVu with an existing ID
        chucVu.setId(1L);

        int databaseSizeBeforeCreate = chucVuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChucVuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chucVu)))
            .andExpect(status().isBadRequest());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaCVIsRequired() throws Exception {
        int databaseSizeBeforeTest = chucVuRepository.findAll().size();
        // set the field null
        chucVu.setMaCV(null);

        // Create the ChucVu, which fails.

        restChucVuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chucVu)))
            .andExpect(status().isBadRequest());

        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChucVus() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chucVu.getId().intValue())))
            .andExpect(jsonPath("$.[*].maCV").value(hasItem(DEFAULT_MA_CV)))
            .andExpect(jsonPath("$.[*].tenChucVu").value(hasItem(DEFAULT_TEN_CHUC_VU)))
            .andExpect(jsonPath("$.[*].phuCap").value(hasItem(DEFAULT_PHU_CAP)))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU)));
    }

    @Test
    @Transactional
    void getChucVu() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get the chucVu
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL_ID, chucVu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chucVu.getId().intValue()))
            .andExpect(jsonPath("$.maCV").value(DEFAULT_MA_CV))
            .andExpect(jsonPath("$.tenChucVu").value(DEFAULT_TEN_CHUC_VU))
            .andExpect(jsonPath("$.phuCap").value(DEFAULT_PHU_CAP))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU));
    }

    @Test
    @Transactional
    void getChucVusByIdFiltering() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        Long id = chucVu.getId();

        defaultChucVuShouldBeFound("id.equals=" + id);
        defaultChucVuShouldNotBeFound("id.notEquals=" + id);

        defaultChucVuShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChucVuShouldNotBeFound("id.greaterThan=" + id);

        defaultChucVuShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChucVuShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllChucVusByMaCVIsEqualToSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where maCV equals to DEFAULT_MA_CV
        defaultChucVuShouldBeFound("maCV.equals=" + DEFAULT_MA_CV);

        // Get all the chucVuList where maCV equals to UPDATED_MA_CV
        defaultChucVuShouldNotBeFound("maCV.equals=" + UPDATED_MA_CV);
    }

    @Test
    @Transactional
    void getAllChucVusByMaCVIsInShouldWork() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where maCV in DEFAULT_MA_CV or UPDATED_MA_CV
        defaultChucVuShouldBeFound("maCV.in=" + DEFAULT_MA_CV + "," + UPDATED_MA_CV);

        // Get all the chucVuList where maCV equals to UPDATED_MA_CV
        defaultChucVuShouldNotBeFound("maCV.in=" + UPDATED_MA_CV);
    }

    @Test
    @Transactional
    void getAllChucVusByMaCVIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where maCV is not null
        defaultChucVuShouldBeFound("maCV.specified=true");

        // Get all the chucVuList where maCV is null
        defaultChucVuShouldNotBeFound("maCV.specified=false");
    }

    @Test
    @Transactional
    void getAllChucVusByMaCVContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where maCV contains DEFAULT_MA_CV
        defaultChucVuShouldBeFound("maCV.contains=" + DEFAULT_MA_CV);

        // Get all the chucVuList where maCV contains UPDATED_MA_CV
        defaultChucVuShouldNotBeFound("maCV.contains=" + UPDATED_MA_CV);
    }

    @Test
    @Transactional
    void getAllChucVusByMaCVNotContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where maCV does not contain DEFAULT_MA_CV
        defaultChucVuShouldNotBeFound("maCV.doesNotContain=" + DEFAULT_MA_CV);

        // Get all the chucVuList where maCV does not contain UPDATED_MA_CV
        defaultChucVuShouldBeFound("maCV.doesNotContain=" + UPDATED_MA_CV);
    }

    @Test
    @Transactional
    void getAllChucVusByTenChucVuIsEqualToSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where tenChucVu equals to DEFAULT_TEN_CHUC_VU
        defaultChucVuShouldBeFound("tenChucVu.equals=" + DEFAULT_TEN_CHUC_VU);

        // Get all the chucVuList where tenChucVu equals to UPDATED_TEN_CHUC_VU
        defaultChucVuShouldNotBeFound("tenChucVu.equals=" + UPDATED_TEN_CHUC_VU);
    }

    @Test
    @Transactional
    void getAllChucVusByTenChucVuIsInShouldWork() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where tenChucVu in DEFAULT_TEN_CHUC_VU or UPDATED_TEN_CHUC_VU
        defaultChucVuShouldBeFound("tenChucVu.in=" + DEFAULT_TEN_CHUC_VU + "," + UPDATED_TEN_CHUC_VU);

        // Get all the chucVuList where tenChucVu equals to UPDATED_TEN_CHUC_VU
        defaultChucVuShouldNotBeFound("tenChucVu.in=" + UPDATED_TEN_CHUC_VU);
    }

    @Test
    @Transactional
    void getAllChucVusByTenChucVuIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where tenChucVu is not null
        defaultChucVuShouldBeFound("tenChucVu.specified=true");

        // Get all the chucVuList where tenChucVu is null
        defaultChucVuShouldNotBeFound("tenChucVu.specified=false");
    }

    @Test
    @Transactional
    void getAllChucVusByTenChucVuContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where tenChucVu contains DEFAULT_TEN_CHUC_VU
        defaultChucVuShouldBeFound("tenChucVu.contains=" + DEFAULT_TEN_CHUC_VU);

        // Get all the chucVuList where tenChucVu contains UPDATED_TEN_CHUC_VU
        defaultChucVuShouldNotBeFound("tenChucVu.contains=" + UPDATED_TEN_CHUC_VU);
    }

    @Test
    @Transactional
    void getAllChucVusByTenChucVuNotContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where tenChucVu does not contain DEFAULT_TEN_CHUC_VU
        defaultChucVuShouldNotBeFound("tenChucVu.doesNotContain=" + DEFAULT_TEN_CHUC_VU);

        // Get all the chucVuList where tenChucVu does not contain UPDATED_TEN_CHUC_VU
        defaultChucVuShouldBeFound("tenChucVu.doesNotContain=" + UPDATED_TEN_CHUC_VU);
    }

    @Test
    @Transactional
    void getAllChucVusByPhuCapIsEqualToSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where phuCap equals to DEFAULT_PHU_CAP
        defaultChucVuShouldBeFound("phuCap.equals=" + DEFAULT_PHU_CAP);

        // Get all the chucVuList where phuCap equals to UPDATED_PHU_CAP
        defaultChucVuShouldNotBeFound("phuCap.equals=" + UPDATED_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllChucVusByPhuCapIsInShouldWork() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where phuCap in DEFAULT_PHU_CAP or UPDATED_PHU_CAP
        defaultChucVuShouldBeFound("phuCap.in=" + DEFAULT_PHU_CAP + "," + UPDATED_PHU_CAP);

        // Get all the chucVuList where phuCap equals to UPDATED_PHU_CAP
        defaultChucVuShouldNotBeFound("phuCap.in=" + UPDATED_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllChucVusByPhuCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where phuCap is not null
        defaultChucVuShouldBeFound("phuCap.specified=true");

        // Get all the chucVuList where phuCap is null
        defaultChucVuShouldNotBeFound("phuCap.specified=false");
    }

    @Test
    @Transactional
    void getAllChucVusByPhuCapContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where phuCap contains DEFAULT_PHU_CAP
        defaultChucVuShouldBeFound("phuCap.contains=" + DEFAULT_PHU_CAP);

        // Get all the chucVuList where phuCap contains UPDATED_PHU_CAP
        defaultChucVuShouldNotBeFound("phuCap.contains=" + UPDATED_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllChucVusByPhuCapNotContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where phuCap does not contain DEFAULT_PHU_CAP
        defaultChucVuShouldNotBeFound("phuCap.doesNotContain=" + DEFAULT_PHU_CAP);

        // Get all the chucVuList where phuCap does not contain UPDATED_PHU_CAP
        defaultChucVuShouldBeFound("phuCap.doesNotContain=" + UPDATED_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllChucVusByGhiChuIsEqualToSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where ghiChu equals to DEFAULT_GHI_CHU
        defaultChucVuShouldBeFound("ghiChu.equals=" + DEFAULT_GHI_CHU);

        // Get all the chucVuList where ghiChu equals to UPDATED_GHI_CHU
        defaultChucVuShouldNotBeFound("ghiChu.equals=" + UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void getAllChucVusByGhiChuIsInShouldWork() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where ghiChu in DEFAULT_GHI_CHU or UPDATED_GHI_CHU
        defaultChucVuShouldBeFound("ghiChu.in=" + DEFAULT_GHI_CHU + "," + UPDATED_GHI_CHU);

        // Get all the chucVuList where ghiChu equals to UPDATED_GHI_CHU
        defaultChucVuShouldNotBeFound("ghiChu.in=" + UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void getAllChucVusByGhiChuIsNullOrNotNull() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where ghiChu is not null
        defaultChucVuShouldBeFound("ghiChu.specified=true");

        // Get all the chucVuList where ghiChu is null
        defaultChucVuShouldNotBeFound("ghiChu.specified=false");
    }

    @Test
    @Transactional
    void getAllChucVusByGhiChuContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where ghiChu contains DEFAULT_GHI_CHU
        defaultChucVuShouldBeFound("ghiChu.contains=" + DEFAULT_GHI_CHU);

        // Get all the chucVuList where ghiChu contains UPDATED_GHI_CHU
        defaultChucVuShouldNotBeFound("ghiChu.contains=" + UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void getAllChucVusByGhiChuNotContainsSomething() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        // Get all the chucVuList where ghiChu does not contain DEFAULT_GHI_CHU
        defaultChucVuShouldNotBeFound("ghiChu.doesNotContain=" + DEFAULT_GHI_CHU);

        // Get all the chucVuList where ghiChu does not contain UPDATED_GHI_CHU
        defaultChucVuShouldBeFound("ghiChu.doesNotContain=" + UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void getAllChucVusByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            chucVuRepository.saveAndFlush(chucVu);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        chucVu.addNhanVien(nhanVien);
        chucVuRepository.saveAndFlush(chucVu);
        Long nhanVienId = nhanVien.getId();

        // Get all the chucVuList where nhanVien equals to nhanVienId
        defaultChucVuShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the chucVuList where nhanVien equals to (nhanVienId + 1)
        defaultChucVuShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChucVuShouldBeFound(String filter) throws Exception {
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chucVu.getId().intValue())))
            .andExpect(jsonPath("$.[*].maCV").value(hasItem(DEFAULT_MA_CV)))
            .andExpect(jsonPath("$.[*].tenChucVu").value(hasItem(DEFAULT_TEN_CHUC_VU)))
            .andExpect(jsonPath("$.[*].phuCap").value(hasItem(DEFAULT_PHU_CAP)))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU)));

        // Check, that the count call also returns 1
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChucVuShouldNotBeFound(String filter) throws Exception {
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChucVuMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingChucVu() throws Exception {
        // Get the chucVu
        restChucVuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChucVu() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();

        // Update the chucVu
        ChucVu updatedChucVu = chucVuRepository.findById(chucVu.getId()).get();
        // Disconnect from session so that the updates on updatedChucVu are not directly saved in db
        em.detach(updatedChucVu);
        updatedChucVu.maCV(UPDATED_MA_CV).tenChucVu(UPDATED_TEN_CHUC_VU).phuCap(UPDATED_PHU_CAP).ghiChu(UPDATED_GHI_CHU);

        restChucVuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChucVu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChucVu))
            )
            .andExpect(status().isOk());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
        ChucVu testChucVu = chucVuList.get(chucVuList.size() - 1);
        assertThat(testChucVu.getMaCV()).isEqualTo(UPDATED_MA_CV);
        assertThat(testChucVu.getTenChucVu()).isEqualTo(UPDATED_TEN_CHUC_VU);
        assertThat(testChucVu.getPhuCap()).isEqualTo(UPDATED_PHU_CAP);
        assertThat(testChucVu.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void putNonExistingChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chucVu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chucVu))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chucVu))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chucVu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChucVuWithPatch() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();

        // Update the chucVu using partial update
        ChucVu partialUpdatedChucVu = new ChucVu();
        partialUpdatedChucVu.setId(chucVu.getId());

        partialUpdatedChucVu.tenChucVu(UPDATED_TEN_CHUC_VU).ghiChu(UPDATED_GHI_CHU);

        restChucVuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChucVu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChucVu))
            )
            .andExpect(status().isOk());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
        ChucVu testChucVu = chucVuList.get(chucVuList.size() - 1);
        assertThat(testChucVu.getMaCV()).isEqualTo(DEFAULT_MA_CV);
        assertThat(testChucVu.getTenChucVu()).isEqualTo(UPDATED_TEN_CHUC_VU);
        assertThat(testChucVu.getPhuCap()).isEqualTo(DEFAULT_PHU_CAP);
        assertThat(testChucVu.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void fullUpdateChucVuWithPatch() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();

        // Update the chucVu using partial update
        ChucVu partialUpdatedChucVu = new ChucVu();
        partialUpdatedChucVu.setId(chucVu.getId());

        partialUpdatedChucVu.maCV(UPDATED_MA_CV).tenChucVu(UPDATED_TEN_CHUC_VU).phuCap(UPDATED_PHU_CAP).ghiChu(UPDATED_GHI_CHU);

        restChucVuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChucVu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChucVu))
            )
            .andExpect(status().isOk());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
        ChucVu testChucVu = chucVuList.get(chucVuList.size() - 1);
        assertThat(testChucVu.getMaCV()).isEqualTo(UPDATED_MA_CV);
        assertThat(testChucVu.getTenChucVu()).isEqualTo(UPDATED_TEN_CHUC_VU);
        assertThat(testChucVu.getPhuCap()).isEqualTo(UPDATED_PHU_CAP);
        assertThat(testChucVu.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void patchNonExistingChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chucVu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chucVu))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chucVu))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChucVu() throws Exception {
        int databaseSizeBeforeUpdate = chucVuRepository.findAll().size();
        chucVu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucVuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chucVu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChucVu in the database
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChucVu() throws Exception {
        // Initialize the database
        chucVuRepository.saveAndFlush(chucVu);

        int databaseSizeBeforeDelete = chucVuRepository.findAll().size();

        // Delete the chucVu
        restChucVuMockMvc
            .perform(delete(ENTITY_API_URL_ID, chucVu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        assertThat(chucVuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
