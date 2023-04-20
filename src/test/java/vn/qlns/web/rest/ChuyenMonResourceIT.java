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
import vn.qlns.domain.ChuyenMon;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.ChuyenMonRepository;
import vn.qlns.service.criteria.ChuyenMonCriteria;

/**
 * Integration tests for the {@link ChuyenMonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChuyenMonResourceIT {

    private static final String DEFAULT_MA_CHUYEN_MON = "AAAAAAAAAA";
    private static final String UPDATED_MA_CHUYEN_MON = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_CHUYEN_MON = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHUYEN_MON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chuyen-mons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChuyenMonRepository chuyenMonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChuyenMonMockMvc;

    private ChuyenMon chuyenMon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenMon createEntity(EntityManager em) {
        ChuyenMon chuyenMon = new ChuyenMon().maChuyenMon(DEFAULT_MA_CHUYEN_MON).tenChuyenMon(DEFAULT_TEN_CHUYEN_MON);
        return chuyenMon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenMon createUpdatedEntity(EntityManager em) {
        ChuyenMon chuyenMon = new ChuyenMon().maChuyenMon(UPDATED_MA_CHUYEN_MON).tenChuyenMon(UPDATED_TEN_CHUYEN_MON);
        return chuyenMon;
    }

    @BeforeEach
    public void initTest() {
        chuyenMon = createEntity(em);
    }

    @Test
    @Transactional
    void createChuyenMon() throws Exception {
        int databaseSizeBeforeCreate = chuyenMonRepository.findAll().size();
        // Create the ChuyenMon
        restChuyenMonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenMon)))
            .andExpect(status().isCreated());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeCreate + 1);
        ChuyenMon testChuyenMon = chuyenMonList.get(chuyenMonList.size() - 1);
        assertThat(testChuyenMon.getMaChuyenMon()).isEqualTo(DEFAULT_MA_CHUYEN_MON);
        assertThat(testChuyenMon.getTenChuyenMon()).isEqualTo(DEFAULT_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void createChuyenMonWithExistingId() throws Exception {
        // Create the ChuyenMon with an existing ID
        chuyenMon.setId(1L);

        int databaseSizeBeforeCreate = chuyenMonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChuyenMonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenMon)))
            .andExpect(status().isBadRequest());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaChuyenMonIsRequired() throws Exception {
        int databaseSizeBeforeTest = chuyenMonRepository.findAll().size();
        // set the field null
        chuyenMon.setMaChuyenMon(null);

        // Create the ChuyenMon, which fails.

        restChuyenMonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenMon)))
            .andExpect(status().isBadRequest());

        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChuyenMons() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chuyenMon.getId().intValue())))
            .andExpect(jsonPath("$.[*].maChuyenMon").value(hasItem(DEFAULT_MA_CHUYEN_MON)))
            .andExpect(jsonPath("$.[*].tenChuyenMon").value(hasItem(DEFAULT_TEN_CHUYEN_MON)));
    }

    @Test
    @Transactional
    void getChuyenMon() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get the chuyenMon
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL_ID, chuyenMon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chuyenMon.getId().intValue()))
            .andExpect(jsonPath("$.maChuyenMon").value(DEFAULT_MA_CHUYEN_MON))
            .andExpect(jsonPath("$.tenChuyenMon").value(DEFAULT_TEN_CHUYEN_MON));
    }

    @Test
    @Transactional
    void getChuyenMonsByIdFiltering() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        Long id = chuyenMon.getId();

        defaultChuyenMonShouldBeFound("id.equals=" + id);
        defaultChuyenMonShouldNotBeFound("id.notEquals=" + id);

        defaultChuyenMonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChuyenMonShouldNotBeFound("id.greaterThan=" + id);

        defaultChuyenMonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChuyenMonShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByMaChuyenMonIsEqualToSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where maChuyenMon equals to DEFAULT_MA_CHUYEN_MON
        defaultChuyenMonShouldBeFound("maChuyenMon.equals=" + DEFAULT_MA_CHUYEN_MON);

        // Get all the chuyenMonList where maChuyenMon equals to UPDATED_MA_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("maChuyenMon.equals=" + UPDATED_MA_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByMaChuyenMonIsInShouldWork() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where maChuyenMon in DEFAULT_MA_CHUYEN_MON or UPDATED_MA_CHUYEN_MON
        defaultChuyenMonShouldBeFound("maChuyenMon.in=" + DEFAULT_MA_CHUYEN_MON + "," + UPDATED_MA_CHUYEN_MON);

        // Get all the chuyenMonList where maChuyenMon equals to UPDATED_MA_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("maChuyenMon.in=" + UPDATED_MA_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByMaChuyenMonIsNullOrNotNull() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where maChuyenMon is not null
        defaultChuyenMonShouldBeFound("maChuyenMon.specified=true");

        // Get all the chuyenMonList where maChuyenMon is null
        defaultChuyenMonShouldNotBeFound("maChuyenMon.specified=false");
    }

    @Test
    @Transactional
    void getAllChuyenMonsByMaChuyenMonContainsSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where maChuyenMon contains DEFAULT_MA_CHUYEN_MON
        defaultChuyenMonShouldBeFound("maChuyenMon.contains=" + DEFAULT_MA_CHUYEN_MON);

        // Get all the chuyenMonList where maChuyenMon contains UPDATED_MA_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("maChuyenMon.contains=" + UPDATED_MA_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByMaChuyenMonNotContainsSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where maChuyenMon does not contain DEFAULT_MA_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("maChuyenMon.doesNotContain=" + DEFAULT_MA_CHUYEN_MON);

        // Get all the chuyenMonList where maChuyenMon does not contain UPDATED_MA_CHUYEN_MON
        defaultChuyenMonShouldBeFound("maChuyenMon.doesNotContain=" + UPDATED_MA_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByTenChuyenMonIsEqualToSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where tenChuyenMon equals to DEFAULT_TEN_CHUYEN_MON
        defaultChuyenMonShouldBeFound("tenChuyenMon.equals=" + DEFAULT_TEN_CHUYEN_MON);

        // Get all the chuyenMonList where tenChuyenMon equals to UPDATED_TEN_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("tenChuyenMon.equals=" + UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByTenChuyenMonIsInShouldWork() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where tenChuyenMon in DEFAULT_TEN_CHUYEN_MON or UPDATED_TEN_CHUYEN_MON
        defaultChuyenMonShouldBeFound("tenChuyenMon.in=" + DEFAULT_TEN_CHUYEN_MON + "," + UPDATED_TEN_CHUYEN_MON);

        // Get all the chuyenMonList where tenChuyenMon equals to UPDATED_TEN_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("tenChuyenMon.in=" + UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByTenChuyenMonIsNullOrNotNull() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where tenChuyenMon is not null
        defaultChuyenMonShouldBeFound("tenChuyenMon.specified=true");

        // Get all the chuyenMonList where tenChuyenMon is null
        defaultChuyenMonShouldNotBeFound("tenChuyenMon.specified=false");
    }

    @Test
    @Transactional
    void getAllChuyenMonsByTenChuyenMonContainsSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where tenChuyenMon contains DEFAULT_TEN_CHUYEN_MON
        defaultChuyenMonShouldBeFound("tenChuyenMon.contains=" + DEFAULT_TEN_CHUYEN_MON);

        // Get all the chuyenMonList where tenChuyenMon contains UPDATED_TEN_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("tenChuyenMon.contains=" + UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByTenChuyenMonNotContainsSomething() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        // Get all the chuyenMonList where tenChuyenMon does not contain DEFAULT_TEN_CHUYEN_MON
        defaultChuyenMonShouldNotBeFound("tenChuyenMon.doesNotContain=" + DEFAULT_TEN_CHUYEN_MON);

        // Get all the chuyenMonList where tenChuyenMon does not contain UPDATED_TEN_CHUYEN_MON
        defaultChuyenMonShouldBeFound("tenChuyenMon.doesNotContain=" + UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void getAllChuyenMonsByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            chuyenMonRepository.saveAndFlush(chuyenMon);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        chuyenMon.addNhanVien(nhanVien);
        chuyenMonRepository.saveAndFlush(chuyenMon);
        Long nhanVienId = nhanVien.getId();

        // Get all the chuyenMonList where nhanVien equals to nhanVienId
        defaultChuyenMonShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the chuyenMonList where nhanVien equals to (nhanVienId + 1)
        defaultChuyenMonShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChuyenMonShouldBeFound(String filter) throws Exception {
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chuyenMon.getId().intValue())))
            .andExpect(jsonPath("$.[*].maChuyenMon").value(hasItem(DEFAULT_MA_CHUYEN_MON)))
            .andExpect(jsonPath("$.[*].tenChuyenMon").value(hasItem(DEFAULT_TEN_CHUYEN_MON)));

        // Check, that the count call also returns 1
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChuyenMonShouldNotBeFound(String filter) throws Exception {
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChuyenMonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingChuyenMon() throws Exception {
        // Get the chuyenMon
        restChuyenMonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChuyenMon() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();

        // Update the chuyenMon
        ChuyenMon updatedChuyenMon = chuyenMonRepository.findById(chuyenMon.getId()).get();
        // Disconnect from session so that the updates on updatedChuyenMon are not directly saved in db
        em.detach(updatedChuyenMon);
        updatedChuyenMon.maChuyenMon(UPDATED_MA_CHUYEN_MON).tenChuyenMon(UPDATED_TEN_CHUYEN_MON);

        restChuyenMonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChuyenMon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChuyenMon))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
        ChuyenMon testChuyenMon = chuyenMonList.get(chuyenMonList.size() - 1);
        assertThat(testChuyenMon.getMaChuyenMon()).isEqualTo(UPDATED_MA_CHUYEN_MON);
        assertThat(testChuyenMon.getTenChuyenMon()).isEqualTo(UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void putNonExistingChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chuyenMon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenMon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenMon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenMon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChuyenMonWithPatch() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();

        // Update the chuyenMon using partial update
        ChuyenMon partialUpdatedChuyenMon = new ChuyenMon();
        partialUpdatedChuyenMon.setId(chuyenMon.getId());

        partialUpdatedChuyenMon.maChuyenMon(UPDATED_MA_CHUYEN_MON).tenChuyenMon(UPDATED_TEN_CHUYEN_MON);

        restChuyenMonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenMon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenMon))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
        ChuyenMon testChuyenMon = chuyenMonList.get(chuyenMonList.size() - 1);
        assertThat(testChuyenMon.getMaChuyenMon()).isEqualTo(UPDATED_MA_CHUYEN_MON);
        assertThat(testChuyenMon.getTenChuyenMon()).isEqualTo(UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void fullUpdateChuyenMonWithPatch() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();

        // Update the chuyenMon using partial update
        ChuyenMon partialUpdatedChuyenMon = new ChuyenMon();
        partialUpdatedChuyenMon.setId(chuyenMon.getId());

        partialUpdatedChuyenMon.maChuyenMon(UPDATED_MA_CHUYEN_MON).tenChuyenMon(UPDATED_TEN_CHUYEN_MON);

        restChuyenMonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenMon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenMon))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
        ChuyenMon testChuyenMon = chuyenMonList.get(chuyenMonList.size() - 1);
        assertThat(testChuyenMon.getMaChuyenMon()).isEqualTo(UPDATED_MA_CHUYEN_MON);
        assertThat(testChuyenMon.getTenChuyenMon()).isEqualTo(UPDATED_TEN_CHUYEN_MON);
    }

    @Test
    @Transactional
    void patchNonExistingChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chuyenMon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenMon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenMon))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChuyenMon() throws Exception {
        int databaseSizeBeforeUpdate = chuyenMonRepository.findAll().size();
        chuyenMon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenMonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chuyenMon))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenMon in the database
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChuyenMon() throws Exception {
        // Initialize the database
        chuyenMonRepository.saveAndFlush(chuyenMon);

        int databaseSizeBeforeDelete = chuyenMonRepository.findAll().size();

        // Delete the chuyenMon
        restChuyenMonMockMvc
            .perform(delete(ENTITY_API_URL_ID, chuyenMon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChuyenMon> chuyenMonList = chuyenMonRepository.findAll();
        assertThat(chuyenMonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
