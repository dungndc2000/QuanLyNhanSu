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
import vn.qlns.domain.NguoiThan;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.NguoiThanRepository;
import vn.qlns.service.criteria.NguoiThanCriteria;

/**
 * Integration tests for the {@link NguoiThanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NguoiThanResourceIT {

    private static final String DEFAULT_MA_NT = "AAAAAAAAAA";
    private static final String UPDATED_MA_NT = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_NT = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NT = "BBBBBBBBBB";

    private static final String DEFAULT_S_DT = "AAAAAAAAAA";
    private static final String UPDATED_S_DT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nguoi-thans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NguoiThanRepository nguoiThanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNguoiThanMockMvc;

    private NguoiThan nguoiThan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NguoiThan createEntity(EntityManager em) {
        NguoiThan nguoiThan = new NguoiThan().maNT(DEFAULT_MA_NT).tenNT(DEFAULT_TEN_NT).sDT(DEFAULT_S_DT);
        return nguoiThan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NguoiThan createUpdatedEntity(EntityManager em) {
        NguoiThan nguoiThan = new NguoiThan().maNT(UPDATED_MA_NT).tenNT(UPDATED_TEN_NT).sDT(UPDATED_S_DT);
        return nguoiThan;
    }

    @BeforeEach
    public void initTest() {
        nguoiThan = createEntity(em);
    }

    @Test
    @Transactional
    void createNguoiThan() throws Exception {
        int databaseSizeBeforeCreate = nguoiThanRepository.findAll().size();
        // Create the NguoiThan
        restNguoiThanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguoiThan)))
            .andExpect(status().isCreated());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeCreate + 1);
        NguoiThan testNguoiThan = nguoiThanList.get(nguoiThanList.size() - 1);
        assertThat(testNguoiThan.getMaNT()).isEqualTo(DEFAULT_MA_NT);
        assertThat(testNguoiThan.getTenNT()).isEqualTo(DEFAULT_TEN_NT);
        assertThat(testNguoiThan.getsDT()).isEqualTo(DEFAULT_S_DT);
    }

    @Test
    @Transactional
    void createNguoiThanWithExistingId() throws Exception {
        // Create the NguoiThan with an existing ID
        nguoiThan.setId(1L);

        int databaseSizeBeforeCreate = nguoiThanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNguoiThanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguoiThan)))
            .andExpect(status().isBadRequest());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaNTIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguoiThanRepository.findAll().size();
        // set the field null
        nguoiThan.setMaNT(null);

        // Create the NguoiThan, which fails.

        restNguoiThanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguoiThan)))
            .andExpect(status().isBadRequest());

        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNguoiThans() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nguoiThan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNT").value(hasItem(DEFAULT_MA_NT)))
            .andExpect(jsonPath("$.[*].tenNT").value(hasItem(DEFAULT_TEN_NT)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)));
    }

    @Test
    @Transactional
    void getNguoiThan() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get the nguoiThan
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL_ID, nguoiThan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nguoiThan.getId().intValue()))
            .andExpect(jsonPath("$.maNT").value(DEFAULT_MA_NT))
            .andExpect(jsonPath("$.tenNT").value(DEFAULT_TEN_NT))
            .andExpect(jsonPath("$.sDT").value(DEFAULT_S_DT));
    }

    @Test
    @Transactional
    void getNguoiThansByIdFiltering() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        Long id = nguoiThan.getId();

        defaultNguoiThanShouldBeFound("id.equals=" + id);
        defaultNguoiThanShouldNotBeFound("id.notEquals=" + id);

        defaultNguoiThanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNguoiThanShouldNotBeFound("id.greaterThan=" + id);

        defaultNguoiThanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNguoiThanShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNguoiThansByMaNTIsEqualToSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where maNT equals to DEFAULT_MA_NT
        defaultNguoiThanShouldBeFound("maNT.equals=" + DEFAULT_MA_NT);

        // Get all the nguoiThanList where maNT equals to UPDATED_MA_NT
        defaultNguoiThanShouldNotBeFound("maNT.equals=" + UPDATED_MA_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByMaNTIsInShouldWork() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where maNT in DEFAULT_MA_NT or UPDATED_MA_NT
        defaultNguoiThanShouldBeFound("maNT.in=" + DEFAULT_MA_NT + "," + UPDATED_MA_NT);

        // Get all the nguoiThanList where maNT equals to UPDATED_MA_NT
        defaultNguoiThanShouldNotBeFound("maNT.in=" + UPDATED_MA_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByMaNTIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where maNT is not null
        defaultNguoiThanShouldBeFound("maNT.specified=true");

        // Get all the nguoiThanList where maNT is null
        defaultNguoiThanShouldNotBeFound("maNT.specified=false");
    }

    @Test
    @Transactional
    void getAllNguoiThansByMaNTContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where maNT contains DEFAULT_MA_NT
        defaultNguoiThanShouldBeFound("maNT.contains=" + DEFAULT_MA_NT);

        // Get all the nguoiThanList where maNT contains UPDATED_MA_NT
        defaultNguoiThanShouldNotBeFound("maNT.contains=" + UPDATED_MA_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByMaNTNotContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where maNT does not contain DEFAULT_MA_NT
        defaultNguoiThanShouldNotBeFound("maNT.doesNotContain=" + DEFAULT_MA_NT);

        // Get all the nguoiThanList where maNT does not contain UPDATED_MA_NT
        defaultNguoiThanShouldBeFound("maNT.doesNotContain=" + UPDATED_MA_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByTenNTIsEqualToSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where tenNT equals to DEFAULT_TEN_NT
        defaultNguoiThanShouldBeFound("tenNT.equals=" + DEFAULT_TEN_NT);

        // Get all the nguoiThanList where tenNT equals to UPDATED_TEN_NT
        defaultNguoiThanShouldNotBeFound("tenNT.equals=" + UPDATED_TEN_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByTenNTIsInShouldWork() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where tenNT in DEFAULT_TEN_NT or UPDATED_TEN_NT
        defaultNguoiThanShouldBeFound("tenNT.in=" + DEFAULT_TEN_NT + "," + UPDATED_TEN_NT);

        // Get all the nguoiThanList where tenNT equals to UPDATED_TEN_NT
        defaultNguoiThanShouldNotBeFound("tenNT.in=" + UPDATED_TEN_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByTenNTIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where tenNT is not null
        defaultNguoiThanShouldBeFound("tenNT.specified=true");

        // Get all the nguoiThanList where tenNT is null
        defaultNguoiThanShouldNotBeFound("tenNT.specified=false");
    }

    @Test
    @Transactional
    void getAllNguoiThansByTenNTContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where tenNT contains DEFAULT_TEN_NT
        defaultNguoiThanShouldBeFound("tenNT.contains=" + DEFAULT_TEN_NT);

        // Get all the nguoiThanList where tenNT contains UPDATED_TEN_NT
        defaultNguoiThanShouldNotBeFound("tenNT.contains=" + UPDATED_TEN_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByTenNTNotContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where tenNT does not contain DEFAULT_TEN_NT
        defaultNguoiThanShouldNotBeFound("tenNT.doesNotContain=" + DEFAULT_TEN_NT);

        // Get all the nguoiThanList where tenNT does not contain UPDATED_TEN_NT
        defaultNguoiThanShouldBeFound("tenNT.doesNotContain=" + UPDATED_TEN_NT);
    }

    @Test
    @Transactional
    void getAllNguoiThansBysDTIsEqualToSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where sDT equals to DEFAULT_S_DT
        defaultNguoiThanShouldBeFound("sDT.equals=" + DEFAULT_S_DT);

        // Get all the nguoiThanList where sDT equals to UPDATED_S_DT
        defaultNguoiThanShouldNotBeFound("sDT.equals=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNguoiThansBysDTIsInShouldWork() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where sDT in DEFAULT_S_DT or UPDATED_S_DT
        defaultNguoiThanShouldBeFound("sDT.in=" + DEFAULT_S_DT + "," + UPDATED_S_DT);

        // Get all the nguoiThanList where sDT equals to UPDATED_S_DT
        defaultNguoiThanShouldNotBeFound("sDT.in=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNguoiThansBysDTIsNullOrNotNull() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where sDT is not null
        defaultNguoiThanShouldBeFound("sDT.specified=true");

        // Get all the nguoiThanList where sDT is null
        defaultNguoiThanShouldNotBeFound("sDT.specified=false");
    }

    @Test
    @Transactional
    void getAllNguoiThansBysDTContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where sDT contains DEFAULT_S_DT
        defaultNguoiThanShouldBeFound("sDT.contains=" + DEFAULT_S_DT);

        // Get all the nguoiThanList where sDT contains UPDATED_S_DT
        defaultNguoiThanShouldNotBeFound("sDT.contains=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNguoiThansBysDTNotContainsSomething() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        // Get all the nguoiThanList where sDT does not contain DEFAULT_S_DT
        defaultNguoiThanShouldNotBeFound("sDT.doesNotContain=" + DEFAULT_S_DT);

        // Get all the nguoiThanList where sDT does not contain UPDATED_S_DT
        defaultNguoiThanShouldBeFound("sDT.doesNotContain=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNguoiThansByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            nguoiThanRepository.saveAndFlush(nguoiThan);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        nguoiThan.setNhanVien(nhanVien);
        nhanVien.setNguoiThan(nguoiThan);
        nguoiThanRepository.saveAndFlush(nguoiThan);
        Long nhanVienId = nhanVien.getId();

        // Get all the nguoiThanList where nhanVien equals to nhanVienId
        defaultNguoiThanShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the nguoiThanList where nhanVien equals to (nhanVienId + 1)
        defaultNguoiThanShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNguoiThanShouldBeFound(String filter) throws Exception {
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nguoiThan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNT").value(hasItem(DEFAULT_MA_NT)))
            .andExpect(jsonPath("$.[*].tenNT").value(hasItem(DEFAULT_TEN_NT)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)));

        // Check, that the count call also returns 1
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNguoiThanShouldNotBeFound(String filter) throws Exception {
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNguoiThanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNguoiThan() throws Exception {
        // Get the nguoiThan
        restNguoiThanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNguoiThan() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();

        // Update the nguoiThan
        NguoiThan updatedNguoiThan = nguoiThanRepository.findById(nguoiThan.getId()).get();
        // Disconnect from session so that the updates on updatedNguoiThan are not directly saved in db
        em.detach(updatedNguoiThan);
        updatedNguoiThan.maNT(UPDATED_MA_NT).tenNT(UPDATED_TEN_NT).sDT(UPDATED_S_DT);

        restNguoiThanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNguoiThan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNguoiThan))
            )
            .andExpect(status().isOk());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
        NguoiThan testNguoiThan = nguoiThanList.get(nguoiThanList.size() - 1);
        assertThat(testNguoiThan.getMaNT()).isEqualTo(UPDATED_MA_NT);
        assertThat(testNguoiThan.getTenNT()).isEqualTo(UPDATED_TEN_NT);
        assertThat(testNguoiThan.getsDT()).isEqualTo(UPDATED_S_DT);
    }

    @Test
    @Transactional
    void putNonExistingNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nguoiThan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nguoiThan))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nguoiThan))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguoiThan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNguoiThanWithPatch() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();

        // Update the nguoiThan using partial update
        NguoiThan partialUpdatedNguoiThan = new NguoiThan();
        partialUpdatedNguoiThan.setId(nguoiThan.getId());

        partialUpdatedNguoiThan.tenNT(UPDATED_TEN_NT);

        restNguoiThanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNguoiThan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNguoiThan))
            )
            .andExpect(status().isOk());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
        NguoiThan testNguoiThan = nguoiThanList.get(nguoiThanList.size() - 1);
        assertThat(testNguoiThan.getMaNT()).isEqualTo(DEFAULT_MA_NT);
        assertThat(testNguoiThan.getTenNT()).isEqualTo(UPDATED_TEN_NT);
        assertThat(testNguoiThan.getsDT()).isEqualTo(DEFAULT_S_DT);
    }

    @Test
    @Transactional
    void fullUpdateNguoiThanWithPatch() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();

        // Update the nguoiThan using partial update
        NguoiThan partialUpdatedNguoiThan = new NguoiThan();
        partialUpdatedNguoiThan.setId(nguoiThan.getId());

        partialUpdatedNguoiThan.maNT(UPDATED_MA_NT).tenNT(UPDATED_TEN_NT).sDT(UPDATED_S_DT);

        restNguoiThanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNguoiThan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNguoiThan))
            )
            .andExpect(status().isOk());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
        NguoiThan testNguoiThan = nguoiThanList.get(nguoiThanList.size() - 1);
        assertThat(testNguoiThan.getMaNT()).isEqualTo(UPDATED_MA_NT);
        assertThat(testNguoiThan.getTenNT()).isEqualTo(UPDATED_TEN_NT);
        assertThat(testNguoiThan.getsDT()).isEqualTo(UPDATED_S_DT);
    }

    @Test
    @Transactional
    void patchNonExistingNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nguoiThan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nguoiThan))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nguoiThan))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNguoiThan() throws Exception {
        int databaseSizeBeforeUpdate = nguoiThanRepository.findAll().size();
        nguoiThan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguoiThanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nguoiThan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NguoiThan in the database
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNguoiThan() throws Exception {
        // Initialize the database
        nguoiThanRepository.saveAndFlush(nguoiThan);

        int databaseSizeBeforeDelete = nguoiThanRepository.findAll().size();

        // Delete the nguoiThan
        restNguoiThanMockMvc
            .perform(delete(ENTITY_API_URL_ID, nguoiThan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NguoiThan> nguoiThanList = nguoiThanRepository.findAll();
        assertThat(nguoiThanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
