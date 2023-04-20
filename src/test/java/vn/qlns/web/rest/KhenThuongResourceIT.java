package vn.qlns.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.qlns.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import vn.qlns.domain.KhenThuong;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.KhenThuongRepository;
import vn.qlns.service.criteria.KhenThuongCriteria;

/**
 * Integration tests for the {@link KhenThuongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KhenThuongResourceIT {

    private static final String DEFAULT_SOQD = "AAAAAAAAAA";
    private static final String UPDATED_SOQD = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_NGAY_QD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAY_QD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_NGAY_QD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_LOAI = "AAAAAAAAAA";
    private static final String UPDATED_LOAI = "BBBBBBBBBB";

    private static final String DEFAULT_HINH_THUC = "AAAAAAAAAA";
    private static final String UPDATED_HINH_THUC = "BBBBBBBBBB";

    private static final String DEFAULT_SO_TIEN = "AAAAAAAAAA";
    private static final String UPDATED_SO_TIEN = "BBBBBBBBBB";

    private static final String DEFAULT_NOI_DUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOI_DUNG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/khen-thuongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KhenThuongRepository khenThuongRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKhenThuongMockMvc;

    private KhenThuong khenThuong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhenThuong createEntity(EntityManager em) {
        KhenThuong khenThuong = new KhenThuong()
            .soqd(DEFAULT_SOQD)
            .ngayQd(DEFAULT_NGAY_QD)
            .ten(DEFAULT_TEN)
            .loai(DEFAULT_LOAI)
            .hinhThuc(DEFAULT_HINH_THUC)
            .soTien(DEFAULT_SO_TIEN)
            .noiDung(DEFAULT_NOI_DUNG);
        return khenThuong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhenThuong createUpdatedEntity(EntityManager em) {
        KhenThuong khenThuong = new KhenThuong()
            .soqd(UPDATED_SOQD)
            .ngayQd(UPDATED_NGAY_QD)
            .ten(UPDATED_TEN)
            .loai(UPDATED_LOAI)
            .hinhThuc(UPDATED_HINH_THUC)
            .soTien(UPDATED_SO_TIEN)
            .noiDung(UPDATED_NOI_DUNG);
        return khenThuong;
    }

    @BeforeEach
    public void initTest() {
        khenThuong = createEntity(em);
    }

    @Test
    @Transactional
    void createKhenThuong() throws Exception {
        int databaseSizeBeforeCreate = khenThuongRepository.findAll().size();
        // Create the KhenThuong
        restKhenThuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khenThuong)))
            .andExpect(status().isCreated());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeCreate + 1);
        KhenThuong testKhenThuong = khenThuongList.get(khenThuongList.size() - 1);
        assertThat(testKhenThuong.getSoqd()).isEqualTo(DEFAULT_SOQD);
        assertThat(testKhenThuong.getNgayQd()).isEqualTo(DEFAULT_NGAY_QD);
        assertThat(testKhenThuong.getTen()).isEqualTo(DEFAULT_TEN);
        assertThat(testKhenThuong.getLoai()).isEqualTo(DEFAULT_LOAI);
        assertThat(testKhenThuong.getHinhThuc()).isEqualTo(DEFAULT_HINH_THUC);
        assertThat(testKhenThuong.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
        assertThat(testKhenThuong.getNoiDung()).isEqualTo(DEFAULT_NOI_DUNG);
    }

    @Test
    @Transactional
    void createKhenThuongWithExistingId() throws Exception {
        // Create the KhenThuong with an existing ID
        khenThuong.setId(1L);

        int databaseSizeBeforeCreate = khenThuongRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKhenThuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khenThuong)))
            .andExpect(status().isBadRequest());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoqdIsRequired() throws Exception {
        int databaseSizeBeforeTest = khenThuongRepository.findAll().size();
        // set the field null
        khenThuong.setSoqd(null);

        // Create the KhenThuong, which fails.

        restKhenThuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khenThuong)))
            .andExpect(status().isBadRequest());

        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgayQdIsRequired() throws Exception {
        int databaseSizeBeforeTest = khenThuongRepository.findAll().size();
        // set the field null
        khenThuong.setNgayQd(null);

        // Create the KhenThuong, which fails.

        restKhenThuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khenThuong)))
            .andExpect(status().isBadRequest());

        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKhenThuongs() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khenThuong.getId().intValue())))
            .andExpect(jsonPath("$.[*].soqd").value(hasItem(DEFAULT_SOQD)))
            .andExpect(jsonPath("$.[*].ngayQd").value(hasItem(sameInstant(DEFAULT_NGAY_QD))))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].loai").value(hasItem(DEFAULT_LOAI)))
            .andExpect(jsonPath("$.[*].hinhThuc").value(hasItem(DEFAULT_HINH_THUC)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)))
            .andExpect(jsonPath("$.[*].noiDung").value(hasItem(DEFAULT_NOI_DUNG)));
    }

    @Test
    @Transactional
    void getKhenThuong() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get the khenThuong
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL_ID, khenThuong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(khenThuong.getId().intValue()))
            .andExpect(jsonPath("$.soqd").value(DEFAULT_SOQD))
            .andExpect(jsonPath("$.ngayQd").value(sameInstant(DEFAULT_NGAY_QD)))
            .andExpect(jsonPath("$.ten").value(DEFAULT_TEN))
            .andExpect(jsonPath("$.loai").value(DEFAULT_LOAI))
            .andExpect(jsonPath("$.hinhThuc").value(DEFAULT_HINH_THUC))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN))
            .andExpect(jsonPath("$.noiDung").value(DEFAULT_NOI_DUNG));
    }

    @Test
    @Transactional
    void getKhenThuongsByIdFiltering() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        Long id = khenThuong.getId();

        defaultKhenThuongShouldBeFound("id.equals=" + id);
        defaultKhenThuongShouldNotBeFound("id.notEquals=" + id);

        defaultKhenThuongShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKhenThuongShouldNotBeFound("id.greaterThan=" + id);

        defaultKhenThuongShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKhenThuongShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoqdIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soqd equals to DEFAULT_SOQD
        defaultKhenThuongShouldBeFound("soqd.equals=" + DEFAULT_SOQD);

        // Get all the khenThuongList where soqd equals to UPDATED_SOQD
        defaultKhenThuongShouldNotBeFound("soqd.equals=" + UPDATED_SOQD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoqdIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soqd in DEFAULT_SOQD or UPDATED_SOQD
        defaultKhenThuongShouldBeFound("soqd.in=" + DEFAULT_SOQD + "," + UPDATED_SOQD);

        // Get all the khenThuongList where soqd equals to UPDATED_SOQD
        defaultKhenThuongShouldNotBeFound("soqd.in=" + UPDATED_SOQD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoqdIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soqd is not null
        defaultKhenThuongShouldBeFound("soqd.specified=true");

        // Get all the khenThuongList where soqd is null
        defaultKhenThuongShouldNotBeFound("soqd.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoqdContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soqd contains DEFAULT_SOQD
        defaultKhenThuongShouldBeFound("soqd.contains=" + DEFAULT_SOQD);

        // Get all the khenThuongList where soqd contains UPDATED_SOQD
        defaultKhenThuongShouldNotBeFound("soqd.contains=" + UPDATED_SOQD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoqdNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soqd does not contain DEFAULT_SOQD
        defaultKhenThuongShouldNotBeFound("soqd.doesNotContain=" + DEFAULT_SOQD);

        // Get all the khenThuongList where soqd does not contain UPDATED_SOQD
        defaultKhenThuongShouldBeFound("soqd.doesNotContain=" + UPDATED_SOQD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd equals to DEFAULT_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.equals=" + DEFAULT_NGAY_QD);

        // Get all the khenThuongList where ngayQd equals to UPDATED_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.equals=" + UPDATED_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd in DEFAULT_NGAY_QD or UPDATED_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.in=" + DEFAULT_NGAY_QD + "," + UPDATED_NGAY_QD);

        // Get all the khenThuongList where ngayQd equals to UPDATED_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.in=" + UPDATED_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd is not null
        defaultKhenThuongShouldBeFound("ngayQd.specified=true");

        // Get all the khenThuongList where ngayQd is null
        defaultKhenThuongShouldNotBeFound("ngayQd.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd is greater than or equal to DEFAULT_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.greaterThanOrEqual=" + DEFAULT_NGAY_QD);

        // Get all the khenThuongList where ngayQd is greater than or equal to UPDATED_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.greaterThanOrEqual=" + UPDATED_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd is less than or equal to DEFAULT_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.lessThanOrEqual=" + DEFAULT_NGAY_QD);

        // Get all the khenThuongList where ngayQd is less than or equal to SMALLER_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.lessThanOrEqual=" + SMALLER_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsLessThanSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd is less than DEFAULT_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.lessThan=" + DEFAULT_NGAY_QD);

        // Get all the khenThuongList where ngayQd is less than UPDATED_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.lessThan=" + UPDATED_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNgayQdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ngayQd is greater than DEFAULT_NGAY_QD
        defaultKhenThuongShouldNotBeFound("ngayQd.greaterThan=" + DEFAULT_NGAY_QD);

        // Get all the khenThuongList where ngayQd is greater than SMALLER_NGAY_QD
        defaultKhenThuongShouldBeFound("ngayQd.greaterThan=" + SMALLER_NGAY_QD);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByTenIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ten equals to DEFAULT_TEN
        defaultKhenThuongShouldBeFound("ten.equals=" + DEFAULT_TEN);

        // Get all the khenThuongList where ten equals to UPDATED_TEN
        defaultKhenThuongShouldNotBeFound("ten.equals=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByTenIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ten in DEFAULT_TEN or UPDATED_TEN
        defaultKhenThuongShouldBeFound("ten.in=" + DEFAULT_TEN + "," + UPDATED_TEN);

        // Get all the khenThuongList where ten equals to UPDATED_TEN
        defaultKhenThuongShouldNotBeFound("ten.in=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByTenIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ten is not null
        defaultKhenThuongShouldBeFound("ten.specified=true");

        // Get all the khenThuongList where ten is null
        defaultKhenThuongShouldNotBeFound("ten.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsByTenContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ten contains DEFAULT_TEN
        defaultKhenThuongShouldBeFound("ten.contains=" + DEFAULT_TEN);

        // Get all the khenThuongList where ten contains UPDATED_TEN
        defaultKhenThuongShouldNotBeFound("ten.contains=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByTenNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where ten does not contain DEFAULT_TEN
        defaultKhenThuongShouldNotBeFound("ten.doesNotContain=" + DEFAULT_TEN);

        // Get all the khenThuongList where ten does not contain UPDATED_TEN
        defaultKhenThuongShouldBeFound("ten.doesNotContain=" + UPDATED_TEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByLoaiIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where loai equals to DEFAULT_LOAI
        defaultKhenThuongShouldBeFound("loai.equals=" + DEFAULT_LOAI);

        // Get all the khenThuongList where loai equals to UPDATED_LOAI
        defaultKhenThuongShouldNotBeFound("loai.equals=" + UPDATED_LOAI);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByLoaiIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where loai in DEFAULT_LOAI or UPDATED_LOAI
        defaultKhenThuongShouldBeFound("loai.in=" + DEFAULT_LOAI + "," + UPDATED_LOAI);

        // Get all the khenThuongList where loai equals to UPDATED_LOAI
        defaultKhenThuongShouldNotBeFound("loai.in=" + UPDATED_LOAI);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByLoaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where loai is not null
        defaultKhenThuongShouldBeFound("loai.specified=true");

        // Get all the khenThuongList where loai is null
        defaultKhenThuongShouldNotBeFound("loai.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsByLoaiContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where loai contains DEFAULT_LOAI
        defaultKhenThuongShouldBeFound("loai.contains=" + DEFAULT_LOAI);

        // Get all the khenThuongList where loai contains UPDATED_LOAI
        defaultKhenThuongShouldNotBeFound("loai.contains=" + UPDATED_LOAI);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByLoaiNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where loai does not contain DEFAULT_LOAI
        defaultKhenThuongShouldNotBeFound("loai.doesNotContain=" + DEFAULT_LOAI);

        // Get all the khenThuongList where loai does not contain UPDATED_LOAI
        defaultKhenThuongShouldBeFound("loai.doesNotContain=" + UPDATED_LOAI);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByHinhThucIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where hinhThuc equals to DEFAULT_HINH_THUC
        defaultKhenThuongShouldBeFound("hinhThuc.equals=" + DEFAULT_HINH_THUC);

        // Get all the khenThuongList where hinhThuc equals to UPDATED_HINH_THUC
        defaultKhenThuongShouldNotBeFound("hinhThuc.equals=" + UPDATED_HINH_THUC);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByHinhThucIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where hinhThuc in DEFAULT_HINH_THUC or UPDATED_HINH_THUC
        defaultKhenThuongShouldBeFound("hinhThuc.in=" + DEFAULT_HINH_THUC + "," + UPDATED_HINH_THUC);

        // Get all the khenThuongList where hinhThuc equals to UPDATED_HINH_THUC
        defaultKhenThuongShouldNotBeFound("hinhThuc.in=" + UPDATED_HINH_THUC);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByHinhThucIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where hinhThuc is not null
        defaultKhenThuongShouldBeFound("hinhThuc.specified=true");

        // Get all the khenThuongList where hinhThuc is null
        defaultKhenThuongShouldNotBeFound("hinhThuc.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsByHinhThucContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where hinhThuc contains DEFAULT_HINH_THUC
        defaultKhenThuongShouldBeFound("hinhThuc.contains=" + DEFAULT_HINH_THUC);

        // Get all the khenThuongList where hinhThuc contains UPDATED_HINH_THUC
        defaultKhenThuongShouldNotBeFound("hinhThuc.contains=" + UPDATED_HINH_THUC);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByHinhThucNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where hinhThuc does not contain DEFAULT_HINH_THUC
        defaultKhenThuongShouldNotBeFound("hinhThuc.doesNotContain=" + DEFAULT_HINH_THUC);

        // Get all the khenThuongList where hinhThuc does not contain UPDATED_HINH_THUC
        defaultKhenThuongShouldBeFound("hinhThuc.doesNotContain=" + UPDATED_HINH_THUC);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoTienIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soTien equals to DEFAULT_SO_TIEN
        defaultKhenThuongShouldBeFound("soTien.equals=" + DEFAULT_SO_TIEN);

        // Get all the khenThuongList where soTien equals to UPDATED_SO_TIEN
        defaultKhenThuongShouldNotBeFound("soTien.equals=" + UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoTienIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soTien in DEFAULT_SO_TIEN or UPDATED_SO_TIEN
        defaultKhenThuongShouldBeFound("soTien.in=" + DEFAULT_SO_TIEN + "," + UPDATED_SO_TIEN);

        // Get all the khenThuongList where soTien equals to UPDATED_SO_TIEN
        defaultKhenThuongShouldNotBeFound("soTien.in=" + UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoTienIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soTien is not null
        defaultKhenThuongShouldBeFound("soTien.specified=true");

        // Get all the khenThuongList where soTien is null
        defaultKhenThuongShouldNotBeFound("soTien.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoTienContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soTien contains DEFAULT_SO_TIEN
        defaultKhenThuongShouldBeFound("soTien.contains=" + DEFAULT_SO_TIEN);

        // Get all the khenThuongList where soTien contains UPDATED_SO_TIEN
        defaultKhenThuongShouldNotBeFound("soTien.contains=" + UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsBySoTienNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where soTien does not contain DEFAULT_SO_TIEN
        defaultKhenThuongShouldNotBeFound("soTien.doesNotContain=" + DEFAULT_SO_TIEN);

        // Get all the khenThuongList where soTien does not contain UPDATED_SO_TIEN
        defaultKhenThuongShouldBeFound("soTien.doesNotContain=" + UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNoiDungIsEqualToSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where noiDung equals to DEFAULT_NOI_DUNG
        defaultKhenThuongShouldBeFound("noiDung.equals=" + DEFAULT_NOI_DUNG);

        // Get all the khenThuongList where noiDung equals to UPDATED_NOI_DUNG
        defaultKhenThuongShouldNotBeFound("noiDung.equals=" + UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNoiDungIsInShouldWork() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where noiDung in DEFAULT_NOI_DUNG or UPDATED_NOI_DUNG
        defaultKhenThuongShouldBeFound("noiDung.in=" + DEFAULT_NOI_DUNG + "," + UPDATED_NOI_DUNG);

        // Get all the khenThuongList where noiDung equals to UPDATED_NOI_DUNG
        defaultKhenThuongShouldNotBeFound("noiDung.in=" + UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNoiDungIsNullOrNotNull() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where noiDung is not null
        defaultKhenThuongShouldBeFound("noiDung.specified=true");

        // Get all the khenThuongList where noiDung is null
        defaultKhenThuongShouldNotBeFound("noiDung.specified=false");
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNoiDungContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where noiDung contains DEFAULT_NOI_DUNG
        defaultKhenThuongShouldBeFound("noiDung.contains=" + DEFAULT_NOI_DUNG);

        // Get all the khenThuongList where noiDung contains UPDATED_NOI_DUNG
        defaultKhenThuongShouldNotBeFound("noiDung.contains=" + UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNoiDungNotContainsSomething() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        // Get all the khenThuongList where noiDung does not contain DEFAULT_NOI_DUNG
        defaultKhenThuongShouldNotBeFound("noiDung.doesNotContain=" + DEFAULT_NOI_DUNG);

        // Get all the khenThuongList where noiDung does not contain UPDATED_NOI_DUNG
        defaultKhenThuongShouldBeFound("noiDung.doesNotContain=" + UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void getAllKhenThuongsByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            khenThuongRepository.saveAndFlush(khenThuong);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        khenThuong.setNhanVien(nhanVien);
        khenThuongRepository.saveAndFlush(khenThuong);
        Long nhanVienId = nhanVien.getId();

        // Get all the khenThuongList where nhanVien equals to nhanVienId
        defaultKhenThuongShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the khenThuongList where nhanVien equals to (nhanVienId + 1)
        defaultKhenThuongShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKhenThuongShouldBeFound(String filter) throws Exception {
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khenThuong.getId().intValue())))
            .andExpect(jsonPath("$.[*].soqd").value(hasItem(DEFAULT_SOQD)))
            .andExpect(jsonPath("$.[*].ngayQd").value(hasItem(sameInstant(DEFAULT_NGAY_QD))))
            .andExpect(jsonPath("$.[*].ten").value(hasItem(DEFAULT_TEN)))
            .andExpect(jsonPath("$.[*].loai").value(hasItem(DEFAULT_LOAI)))
            .andExpect(jsonPath("$.[*].hinhThuc").value(hasItem(DEFAULT_HINH_THUC)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)))
            .andExpect(jsonPath("$.[*].noiDung").value(hasItem(DEFAULT_NOI_DUNG)));

        // Check, that the count call also returns 1
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKhenThuongShouldNotBeFound(String filter) throws Exception {
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKhenThuongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingKhenThuong() throws Exception {
        // Get the khenThuong
        restKhenThuongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKhenThuong() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();

        // Update the khenThuong
        KhenThuong updatedKhenThuong = khenThuongRepository.findById(khenThuong.getId()).get();
        // Disconnect from session so that the updates on updatedKhenThuong are not directly saved in db
        em.detach(updatedKhenThuong);
        updatedKhenThuong
            .soqd(UPDATED_SOQD)
            .ngayQd(UPDATED_NGAY_QD)
            .ten(UPDATED_TEN)
            .loai(UPDATED_LOAI)
            .hinhThuc(UPDATED_HINH_THUC)
            .soTien(UPDATED_SO_TIEN)
            .noiDung(UPDATED_NOI_DUNG);

        restKhenThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKhenThuong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKhenThuong))
            )
            .andExpect(status().isOk());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
        KhenThuong testKhenThuong = khenThuongList.get(khenThuongList.size() - 1);
        assertThat(testKhenThuong.getSoqd()).isEqualTo(UPDATED_SOQD);
        assertThat(testKhenThuong.getNgayQd()).isEqualTo(UPDATED_NGAY_QD);
        assertThat(testKhenThuong.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testKhenThuong.getLoai()).isEqualTo(UPDATED_LOAI);
        assertThat(testKhenThuong.getHinhThuc()).isEqualTo(UPDATED_HINH_THUC);
        assertThat(testKhenThuong.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testKhenThuong.getNoiDung()).isEqualTo(UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void putNonExistingKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khenThuong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khenThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khenThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khenThuong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKhenThuongWithPatch() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();

        // Update the khenThuong using partial update
        KhenThuong partialUpdatedKhenThuong = new KhenThuong();
        partialUpdatedKhenThuong.setId(khenThuong.getId());

        partialUpdatedKhenThuong.ten(UPDATED_TEN).soTien(UPDATED_SO_TIEN).noiDung(UPDATED_NOI_DUNG);

        restKhenThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhenThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhenThuong))
            )
            .andExpect(status().isOk());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
        KhenThuong testKhenThuong = khenThuongList.get(khenThuongList.size() - 1);
        assertThat(testKhenThuong.getSoqd()).isEqualTo(DEFAULT_SOQD);
        assertThat(testKhenThuong.getNgayQd()).isEqualTo(DEFAULT_NGAY_QD);
        assertThat(testKhenThuong.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testKhenThuong.getLoai()).isEqualTo(DEFAULT_LOAI);
        assertThat(testKhenThuong.getHinhThuc()).isEqualTo(DEFAULT_HINH_THUC);
        assertThat(testKhenThuong.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testKhenThuong.getNoiDung()).isEqualTo(UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void fullUpdateKhenThuongWithPatch() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();

        // Update the khenThuong using partial update
        KhenThuong partialUpdatedKhenThuong = new KhenThuong();
        partialUpdatedKhenThuong.setId(khenThuong.getId());

        partialUpdatedKhenThuong
            .soqd(UPDATED_SOQD)
            .ngayQd(UPDATED_NGAY_QD)
            .ten(UPDATED_TEN)
            .loai(UPDATED_LOAI)
            .hinhThuc(UPDATED_HINH_THUC)
            .soTien(UPDATED_SO_TIEN)
            .noiDung(UPDATED_NOI_DUNG);

        restKhenThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhenThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhenThuong))
            )
            .andExpect(status().isOk());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
        KhenThuong testKhenThuong = khenThuongList.get(khenThuongList.size() - 1);
        assertThat(testKhenThuong.getSoqd()).isEqualTo(UPDATED_SOQD);
        assertThat(testKhenThuong.getNgayQd()).isEqualTo(UPDATED_NGAY_QD);
        assertThat(testKhenThuong.getTen()).isEqualTo(UPDATED_TEN);
        assertThat(testKhenThuong.getLoai()).isEqualTo(UPDATED_LOAI);
        assertThat(testKhenThuong.getHinhThuc()).isEqualTo(UPDATED_HINH_THUC);
        assertThat(testKhenThuong.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testKhenThuong.getNoiDung()).isEqualTo(UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    void patchNonExistingKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, khenThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khenThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khenThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKhenThuong() throws Exception {
        int databaseSizeBeforeUpdate = khenThuongRepository.findAll().size();
        khenThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhenThuongMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(khenThuong))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhenThuong in the database
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKhenThuong() throws Exception {
        // Initialize the database
        khenThuongRepository.saveAndFlush(khenThuong);

        int databaseSizeBeforeDelete = khenThuongRepository.findAll().size();

        // Delete the khenThuong
        restKhenThuongMockMvc
            .perform(delete(ENTITY_API_URL_ID, khenThuong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KhenThuong> khenThuongList = khenThuongRepository.findAll();
        assertThat(khenThuongList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
