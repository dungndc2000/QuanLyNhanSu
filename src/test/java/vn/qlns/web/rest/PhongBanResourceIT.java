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
import vn.qlns.domain.PhongBan;
import vn.qlns.repository.PhongBanRepository;
import vn.qlns.service.criteria.PhongBanCriteria;

/**
 * Integration tests for the {@link PhongBanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhongBanResourceIT {

    private static final String DEFAULT_MA_PB = "AAAAAAAAAA";
    private static final String UPDATED_MA_PB = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_PB = "AAAAAAAAAA";
    private static final String UPDATED_TEN_PB = "BBBBBBBBBB";

    private static final String DEFAULT_S_DT = "AAAAAAAAAA";
    private static final String UPDATED_S_DT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phong-bans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongBanMockMvc;

    private PhongBan phongBan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBan createEntity(EntityManager em) {
        PhongBan phongBan = new PhongBan().maPB(DEFAULT_MA_PB).tenPB(DEFAULT_TEN_PB).sDT(DEFAULT_S_DT);
        return phongBan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBan createUpdatedEntity(EntityManager em) {
        PhongBan phongBan = new PhongBan().maPB(UPDATED_MA_PB).tenPB(UPDATED_TEN_PB).sDT(UPDATED_S_DT);
        return phongBan;
    }

    @BeforeEach
    public void initTest() {
        phongBan = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongBan() throws Exception {
        int databaseSizeBeforeCreate = phongBanRepository.findAll().size();
        // Create the PhongBan
        restPhongBanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isCreated());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeCreate + 1);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPB()).isEqualTo(DEFAULT_MA_PB);
        assertThat(testPhongBan.getTenPB()).isEqualTo(DEFAULT_TEN_PB);
        assertThat(testPhongBan.getsDT()).isEqualTo(DEFAULT_S_DT);
    }

    @Test
    @Transactional
    void createPhongBanWithExistingId() throws Exception {
        // Create the PhongBan with an existing ID
        phongBan.setId(1L);

        int databaseSizeBeforeCreate = phongBanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongBanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaPBIsRequired() throws Exception {
        int databaseSizeBeforeTest = phongBanRepository.findAll().size();
        // set the field null
        phongBan.setMaPB(null);

        // Create the PhongBan, which fails.

        restPhongBanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isBadRequest());

        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhongBans() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongBan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maPB").value(hasItem(DEFAULT_MA_PB)))
            .andExpect(jsonPath("$.[*].tenPB").value(hasItem(DEFAULT_TEN_PB)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)));
    }

    @Test
    @Transactional
    void getPhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get the phongBan
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL_ID, phongBan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongBan.getId().intValue()))
            .andExpect(jsonPath("$.maPB").value(DEFAULT_MA_PB))
            .andExpect(jsonPath("$.tenPB").value(DEFAULT_TEN_PB))
            .andExpect(jsonPath("$.sDT").value(DEFAULT_S_DT));
    }

    @Test
    @Transactional
    void getPhongBansByIdFiltering() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        Long id = phongBan.getId();

        defaultPhongBanShouldBeFound("id.equals=" + id);
        defaultPhongBanShouldNotBeFound("id.notEquals=" + id);

        defaultPhongBanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhongBanShouldNotBeFound("id.greaterThan=" + id);

        defaultPhongBanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhongBanShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPhongBansByMaPBIsEqualToSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where maPB equals to DEFAULT_MA_PB
        defaultPhongBanShouldBeFound("maPB.equals=" + DEFAULT_MA_PB);

        // Get all the phongBanList where maPB equals to UPDATED_MA_PB
        defaultPhongBanShouldNotBeFound("maPB.equals=" + UPDATED_MA_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByMaPBIsInShouldWork() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where maPB in DEFAULT_MA_PB or UPDATED_MA_PB
        defaultPhongBanShouldBeFound("maPB.in=" + DEFAULT_MA_PB + "," + UPDATED_MA_PB);

        // Get all the phongBanList where maPB equals to UPDATED_MA_PB
        defaultPhongBanShouldNotBeFound("maPB.in=" + UPDATED_MA_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByMaPBIsNullOrNotNull() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where maPB is not null
        defaultPhongBanShouldBeFound("maPB.specified=true");

        // Get all the phongBanList where maPB is null
        defaultPhongBanShouldNotBeFound("maPB.specified=false");
    }

    @Test
    @Transactional
    void getAllPhongBansByMaPBContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where maPB contains DEFAULT_MA_PB
        defaultPhongBanShouldBeFound("maPB.contains=" + DEFAULT_MA_PB);

        // Get all the phongBanList where maPB contains UPDATED_MA_PB
        defaultPhongBanShouldNotBeFound("maPB.contains=" + UPDATED_MA_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByMaPBNotContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where maPB does not contain DEFAULT_MA_PB
        defaultPhongBanShouldNotBeFound("maPB.doesNotContain=" + DEFAULT_MA_PB);

        // Get all the phongBanList where maPB does not contain UPDATED_MA_PB
        defaultPhongBanShouldBeFound("maPB.doesNotContain=" + UPDATED_MA_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByTenPBIsEqualToSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where tenPB equals to DEFAULT_TEN_PB
        defaultPhongBanShouldBeFound("tenPB.equals=" + DEFAULT_TEN_PB);

        // Get all the phongBanList where tenPB equals to UPDATED_TEN_PB
        defaultPhongBanShouldNotBeFound("tenPB.equals=" + UPDATED_TEN_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByTenPBIsInShouldWork() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where tenPB in DEFAULT_TEN_PB or UPDATED_TEN_PB
        defaultPhongBanShouldBeFound("tenPB.in=" + DEFAULT_TEN_PB + "," + UPDATED_TEN_PB);

        // Get all the phongBanList where tenPB equals to UPDATED_TEN_PB
        defaultPhongBanShouldNotBeFound("tenPB.in=" + UPDATED_TEN_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByTenPBIsNullOrNotNull() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where tenPB is not null
        defaultPhongBanShouldBeFound("tenPB.specified=true");

        // Get all the phongBanList where tenPB is null
        defaultPhongBanShouldNotBeFound("tenPB.specified=false");
    }

    @Test
    @Transactional
    void getAllPhongBansByTenPBContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where tenPB contains DEFAULT_TEN_PB
        defaultPhongBanShouldBeFound("tenPB.contains=" + DEFAULT_TEN_PB);

        // Get all the phongBanList where tenPB contains UPDATED_TEN_PB
        defaultPhongBanShouldNotBeFound("tenPB.contains=" + UPDATED_TEN_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansByTenPBNotContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where tenPB does not contain DEFAULT_TEN_PB
        defaultPhongBanShouldNotBeFound("tenPB.doesNotContain=" + DEFAULT_TEN_PB);

        // Get all the phongBanList where tenPB does not contain UPDATED_TEN_PB
        defaultPhongBanShouldBeFound("tenPB.doesNotContain=" + UPDATED_TEN_PB);
    }

    @Test
    @Transactional
    void getAllPhongBansBysDTIsEqualToSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where sDT equals to DEFAULT_S_DT
        defaultPhongBanShouldBeFound("sDT.equals=" + DEFAULT_S_DT);

        // Get all the phongBanList where sDT equals to UPDATED_S_DT
        defaultPhongBanShouldNotBeFound("sDT.equals=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllPhongBansBysDTIsInShouldWork() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where sDT in DEFAULT_S_DT or UPDATED_S_DT
        defaultPhongBanShouldBeFound("sDT.in=" + DEFAULT_S_DT + "," + UPDATED_S_DT);

        // Get all the phongBanList where sDT equals to UPDATED_S_DT
        defaultPhongBanShouldNotBeFound("sDT.in=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllPhongBansBysDTIsNullOrNotNull() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where sDT is not null
        defaultPhongBanShouldBeFound("sDT.specified=true");

        // Get all the phongBanList where sDT is null
        defaultPhongBanShouldNotBeFound("sDT.specified=false");
    }

    @Test
    @Transactional
    void getAllPhongBansBysDTContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where sDT contains DEFAULT_S_DT
        defaultPhongBanShouldBeFound("sDT.contains=" + DEFAULT_S_DT);

        // Get all the phongBanList where sDT contains UPDATED_S_DT
        defaultPhongBanShouldNotBeFound("sDT.contains=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllPhongBansBysDTNotContainsSomething() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList where sDT does not contain DEFAULT_S_DT
        defaultPhongBanShouldNotBeFound("sDT.doesNotContain=" + DEFAULT_S_DT);

        // Get all the phongBanList where sDT does not contain UPDATED_S_DT
        defaultPhongBanShouldBeFound("sDT.doesNotContain=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllPhongBansByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            phongBanRepository.saveAndFlush(phongBan);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        phongBan.addNhanVien(nhanVien);
        phongBanRepository.saveAndFlush(phongBan);
        Long nhanVienId = nhanVien.getId();

        // Get all the phongBanList where nhanVien equals to nhanVienId
        defaultPhongBanShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the phongBanList where nhanVien equals to (nhanVienId + 1)
        defaultPhongBanShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhongBanShouldBeFound(String filter) throws Exception {
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongBan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maPB").value(hasItem(DEFAULT_MA_PB)))
            .andExpect(jsonPath("$.[*].tenPB").value(hasItem(DEFAULT_TEN_PB)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)));

        // Check, that the count call also returns 1
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhongBanShouldNotBeFound(String filter) throws Exception {
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPhongBan() throws Exception {
        // Get the phongBan
        restPhongBanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan
        PhongBan updatedPhongBan = phongBanRepository.findById(phongBan.getId()).get();
        // Disconnect from session so that the updates on updatedPhongBan are not directly saved in db
        em.detach(updatedPhongBan);
        updatedPhongBan.maPB(UPDATED_MA_PB).tenPB(UPDATED_TEN_PB).sDT(UPDATED_S_DT);

        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongBan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPB()).isEqualTo(UPDATED_MA_PB);
        assertThat(testPhongBan.getTenPB()).isEqualTo(UPDATED_TEN_PB);
        assertThat(testPhongBan.getsDT()).isEqualTo(UPDATED_S_DT);
    }

    @Test
    @Transactional
    void putNonExistingPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongBan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongBanWithPatch() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan using partial update
        PhongBan partialUpdatedPhongBan = new PhongBan();
        partialUpdatedPhongBan.setId(phongBan.getId());

        partialUpdatedPhongBan.sDT(UPDATED_S_DT);

        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPB()).isEqualTo(DEFAULT_MA_PB);
        assertThat(testPhongBan.getTenPB()).isEqualTo(DEFAULT_TEN_PB);
        assertThat(testPhongBan.getsDT()).isEqualTo(UPDATED_S_DT);
    }

    @Test
    @Transactional
    void fullUpdatePhongBanWithPatch() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan using partial update
        PhongBan partialUpdatedPhongBan = new PhongBan();
        partialUpdatedPhongBan.setId(phongBan.getId());

        partialUpdatedPhongBan.maPB(UPDATED_MA_PB).tenPB(UPDATED_TEN_PB).sDT(UPDATED_S_DT);

        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPB()).isEqualTo(UPDATED_MA_PB);
        assertThat(testPhongBan.getTenPB()).isEqualTo(UPDATED_TEN_PB);
        assertThat(testPhongBan.getsDT()).isEqualTo(UPDATED_S_DT);
    }

    @Test
    @Transactional
    void patchNonExistingPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeDelete = phongBanRepository.findAll().size();

        // Delete the phongBan
        restPhongBanMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongBan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
