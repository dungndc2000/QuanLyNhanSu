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
import vn.qlns.domain.ChucVu;
import vn.qlns.domain.ChuyenMon;
import vn.qlns.domain.KhenThuong;
import vn.qlns.domain.Luong;
import vn.qlns.domain.NguoiThan;
import vn.qlns.domain.NhanVien;
import vn.qlns.domain.PhongBan;
import vn.qlns.domain.TrinhDoHV;
import vn.qlns.repository.NhanVienRepository;
import vn.qlns.service.criteria.NhanVienCriteria;

/**
 * Integration tests for the {@link NhanVienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhanVienResourceIT {

    private static final String DEFAULT_MA_NV = "AAAAAAAAAA";
    private static final String UPDATED_MA_NV = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_NV = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NV = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_NGAY_SINH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAY_SINH = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_NGAY_SINH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_GIOI_TINH = "AAAAAAAAAA";
    private static final String UPDATED_GIOI_TINH = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_SO_CMND = "AAAAAAAAAA";
    private static final String UPDATED_SO_CMND = "BBBBBBBBBB";

    private static final String DEFAULT_S_DT = "AAAAAAAAAA";
    private static final String UPDATED_S_DT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_HE_SO_LUONG = "AAAAAAAAAA";
    private static final String UPDATED_HE_SO_LUONG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nhan-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhanVienMockMvc;

    private NhanVien nhanVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .maNV(DEFAULT_MA_NV)
            .tenNV(DEFAULT_TEN_NV)
            .ngaySinh(DEFAULT_NGAY_SINH)
            .gioiTinh(DEFAULT_GIOI_TINH)
            .diaChi(DEFAULT_DIA_CHI)
            .soCMND(DEFAULT_SO_CMND)
            .sDT(DEFAULT_S_DT)
            .email(DEFAULT_EMAIL)
            .heSoLuong(DEFAULT_HE_SO_LUONG);
        return nhanVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createUpdatedEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .maNV(UPDATED_MA_NV)
            .tenNV(UPDATED_TEN_NV)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioiTinh(UPDATED_GIOI_TINH)
            .diaChi(UPDATED_DIA_CHI)
            .soCMND(UPDATED_SO_CMND)
            .sDT(UPDATED_S_DT)
            .email(UPDATED_EMAIL)
            .heSoLuong(UPDATED_HE_SO_LUONG);
        return nhanVien;
    }

    @BeforeEach
    public void initTest() {
        nhanVien = createEntity(em);
    }

    @Test
    @Transactional
    void createNhanVien() throws Exception {
        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();
        // Create the NhanVien
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isCreated());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate + 1);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNV()).isEqualTo(DEFAULT_MA_NV);
        assertThat(testNhanVien.getTenNV()).isEqualTo(DEFAULT_TEN_NV);
        assertThat(testNhanVien.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
        assertThat(testNhanVien.getGioiTinh()).isEqualTo(DEFAULT_GIOI_TINH);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testNhanVien.getSoCMND()).isEqualTo(DEFAULT_SO_CMND);
        assertThat(testNhanVien.getsDT()).isEqualTo(DEFAULT_S_DT);
        assertThat(testNhanVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhanVien.getHeSoLuong()).isEqualTo(DEFAULT_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void createNhanVienWithExistingId() throws Exception {
        // Create the NhanVien with an existing ID
        nhanVien.setId(1L);

        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaNVIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setMaNV(null);

        // Create the NhanVien, which fails.

        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenNVIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setTenNV(null);

        // Create the NhanVien, which fails.

        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgaySinhIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setNgaySinh(null);

        // Create the NhanVien, which fails.

        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGioiTinhIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setGioiTinh(null);

        // Create the NhanVien, which fails.

        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoCMNDIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhanVienRepository.findAll().size();
        // set the field null
        nhanVien.setSoCMND(null);

        // Create the NhanVien, which fails.

        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNhanViens() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNV").value(hasItem(DEFAULT_MA_NV)))
            .andExpect(jsonPath("$.[*].tenNV").value(hasItem(DEFAULT_TEN_NV)))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(sameInstant(DEFAULT_NGAY_SINH))))
            .andExpect(jsonPath("$.[*].gioiTinh").value(hasItem(DEFAULT_GIOI_TINH)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].soCMND").value(hasItem(DEFAULT_SO_CMND)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].heSoLuong").value(hasItem(DEFAULT_HE_SO_LUONG)));
    }

    @Test
    @Transactional
    void getNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get the nhanVien
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL_ID, nhanVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhanVien.getId().intValue()))
            .andExpect(jsonPath("$.maNV").value(DEFAULT_MA_NV))
            .andExpect(jsonPath("$.tenNV").value(DEFAULT_TEN_NV))
            .andExpect(jsonPath("$.ngaySinh").value(sameInstant(DEFAULT_NGAY_SINH)))
            .andExpect(jsonPath("$.gioiTinh").value(DEFAULT_GIOI_TINH))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI))
            .andExpect(jsonPath("$.soCMND").value(DEFAULT_SO_CMND))
            .andExpect(jsonPath("$.sDT").value(DEFAULT_S_DT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.heSoLuong").value(DEFAULT_HE_SO_LUONG));
    }

    @Test
    @Transactional
    void getNhanViensByIdFiltering() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        Long id = nhanVien.getId();

        defaultNhanVienShouldBeFound("id.equals=" + id);
        defaultNhanVienShouldNotBeFound("id.notEquals=" + id);

        defaultNhanVienShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNhanVienShouldNotBeFound("id.greaterThan=" + id);

        defaultNhanVienShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNhanVienShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNhanViensByMaNVIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where maNV equals to DEFAULT_MA_NV
        defaultNhanVienShouldBeFound("maNV.equals=" + DEFAULT_MA_NV);

        // Get all the nhanVienList where maNV equals to UPDATED_MA_NV
        defaultNhanVienShouldNotBeFound("maNV.equals=" + UPDATED_MA_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByMaNVIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where maNV in DEFAULT_MA_NV or UPDATED_MA_NV
        defaultNhanVienShouldBeFound("maNV.in=" + DEFAULT_MA_NV + "," + UPDATED_MA_NV);

        // Get all the nhanVienList where maNV equals to UPDATED_MA_NV
        defaultNhanVienShouldNotBeFound("maNV.in=" + UPDATED_MA_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByMaNVIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where maNV is not null
        defaultNhanVienShouldBeFound("maNV.specified=true");

        // Get all the nhanVienList where maNV is null
        defaultNhanVienShouldNotBeFound("maNV.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByMaNVContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where maNV contains DEFAULT_MA_NV
        defaultNhanVienShouldBeFound("maNV.contains=" + DEFAULT_MA_NV);

        // Get all the nhanVienList where maNV contains UPDATED_MA_NV
        defaultNhanVienShouldNotBeFound("maNV.contains=" + UPDATED_MA_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByMaNVNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where maNV does not contain DEFAULT_MA_NV
        defaultNhanVienShouldNotBeFound("maNV.doesNotContain=" + DEFAULT_MA_NV);

        // Get all the nhanVienList where maNV does not contain UPDATED_MA_NV
        defaultNhanVienShouldBeFound("maNV.doesNotContain=" + UPDATED_MA_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByTenNVIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where tenNV equals to DEFAULT_TEN_NV
        defaultNhanVienShouldBeFound("tenNV.equals=" + DEFAULT_TEN_NV);

        // Get all the nhanVienList where tenNV equals to UPDATED_TEN_NV
        defaultNhanVienShouldNotBeFound("tenNV.equals=" + UPDATED_TEN_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByTenNVIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where tenNV in DEFAULT_TEN_NV or UPDATED_TEN_NV
        defaultNhanVienShouldBeFound("tenNV.in=" + DEFAULT_TEN_NV + "," + UPDATED_TEN_NV);

        // Get all the nhanVienList where tenNV equals to UPDATED_TEN_NV
        defaultNhanVienShouldNotBeFound("tenNV.in=" + UPDATED_TEN_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByTenNVIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where tenNV is not null
        defaultNhanVienShouldBeFound("tenNV.specified=true");

        // Get all the nhanVienList where tenNV is null
        defaultNhanVienShouldNotBeFound("tenNV.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByTenNVContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where tenNV contains DEFAULT_TEN_NV
        defaultNhanVienShouldBeFound("tenNV.contains=" + DEFAULT_TEN_NV);

        // Get all the nhanVienList where tenNV contains UPDATED_TEN_NV
        defaultNhanVienShouldNotBeFound("tenNV.contains=" + UPDATED_TEN_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByTenNVNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where tenNV does not contain DEFAULT_TEN_NV
        defaultNhanVienShouldNotBeFound("tenNV.doesNotContain=" + DEFAULT_TEN_NV);

        // Get all the nhanVienList where tenNV does not contain UPDATED_TEN_NV
        defaultNhanVienShouldBeFound("tenNV.doesNotContain=" + UPDATED_TEN_NV);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh equals to DEFAULT_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.equals=" + DEFAULT_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh equals to UPDATED_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.equals=" + UPDATED_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh in DEFAULT_NGAY_SINH or UPDATED_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.in=" + DEFAULT_NGAY_SINH + "," + UPDATED_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh equals to UPDATED_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.in=" + UPDATED_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh is not null
        defaultNhanVienShouldBeFound("ngaySinh.specified=true");

        // Get all the nhanVienList where ngaySinh is null
        defaultNhanVienShouldNotBeFound("ngaySinh.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh is greater than or equal to DEFAULT_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.greaterThanOrEqual=" + DEFAULT_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh is greater than or equal to UPDATED_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.greaterThanOrEqual=" + UPDATED_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh is less than or equal to DEFAULT_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.lessThanOrEqual=" + DEFAULT_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh is less than or equal to SMALLER_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.lessThanOrEqual=" + SMALLER_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsLessThanSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh is less than DEFAULT_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.lessThan=" + DEFAULT_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh is less than UPDATED_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.lessThan=" + UPDATED_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByNgaySinhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where ngaySinh is greater than DEFAULT_NGAY_SINH
        defaultNhanVienShouldNotBeFound("ngaySinh.greaterThan=" + DEFAULT_NGAY_SINH);

        // Get all the nhanVienList where ngaySinh is greater than SMALLER_NGAY_SINH
        defaultNhanVienShouldBeFound("ngaySinh.greaterThan=" + SMALLER_NGAY_SINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByGioiTinhIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where gioiTinh equals to DEFAULT_GIOI_TINH
        defaultNhanVienShouldBeFound("gioiTinh.equals=" + DEFAULT_GIOI_TINH);

        // Get all the nhanVienList where gioiTinh equals to UPDATED_GIOI_TINH
        defaultNhanVienShouldNotBeFound("gioiTinh.equals=" + UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByGioiTinhIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where gioiTinh in DEFAULT_GIOI_TINH or UPDATED_GIOI_TINH
        defaultNhanVienShouldBeFound("gioiTinh.in=" + DEFAULT_GIOI_TINH + "," + UPDATED_GIOI_TINH);

        // Get all the nhanVienList where gioiTinh equals to UPDATED_GIOI_TINH
        defaultNhanVienShouldNotBeFound("gioiTinh.in=" + UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByGioiTinhIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where gioiTinh is not null
        defaultNhanVienShouldBeFound("gioiTinh.specified=true");

        // Get all the nhanVienList where gioiTinh is null
        defaultNhanVienShouldNotBeFound("gioiTinh.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByGioiTinhContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where gioiTinh contains DEFAULT_GIOI_TINH
        defaultNhanVienShouldBeFound("gioiTinh.contains=" + DEFAULT_GIOI_TINH);

        // Get all the nhanVienList where gioiTinh contains UPDATED_GIOI_TINH
        defaultNhanVienShouldNotBeFound("gioiTinh.contains=" + UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByGioiTinhNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where gioiTinh does not contain DEFAULT_GIOI_TINH
        defaultNhanVienShouldNotBeFound("gioiTinh.doesNotContain=" + DEFAULT_GIOI_TINH);

        // Get all the nhanVienList where gioiTinh does not contain UPDATED_GIOI_TINH
        defaultNhanVienShouldBeFound("gioiTinh.doesNotContain=" + UPDATED_GIOI_TINH);
    }

    @Test
    @Transactional
    void getAllNhanViensByDiaChiIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where diaChi equals to DEFAULT_DIA_CHI
        defaultNhanVienShouldBeFound("diaChi.equals=" + DEFAULT_DIA_CHI);

        // Get all the nhanVienList where diaChi equals to UPDATED_DIA_CHI
        defaultNhanVienShouldNotBeFound("diaChi.equals=" + UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void getAllNhanViensByDiaChiIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where diaChi in DEFAULT_DIA_CHI or UPDATED_DIA_CHI
        defaultNhanVienShouldBeFound("diaChi.in=" + DEFAULT_DIA_CHI + "," + UPDATED_DIA_CHI);

        // Get all the nhanVienList where diaChi equals to UPDATED_DIA_CHI
        defaultNhanVienShouldNotBeFound("diaChi.in=" + UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void getAllNhanViensByDiaChiIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where diaChi is not null
        defaultNhanVienShouldBeFound("diaChi.specified=true");

        // Get all the nhanVienList where diaChi is null
        defaultNhanVienShouldNotBeFound("diaChi.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByDiaChiContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where diaChi contains DEFAULT_DIA_CHI
        defaultNhanVienShouldBeFound("diaChi.contains=" + DEFAULT_DIA_CHI);

        // Get all the nhanVienList where diaChi contains UPDATED_DIA_CHI
        defaultNhanVienShouldNotBeFound("diaChi.contains=" + UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void getAllNhanViensByDiaChiNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where diaChi does not contain DEFAULT_DIA_CHI
        defaultNhanVienShouldNotBeFound("diaChi.doesNotContain=" + DEFAULT_DIA_CHI);

        // Get all the nhanVienList where diaChi does not contain UPDATED_DIA_CHI
        defaultNhanVienShouldBeFound("diaChi.doesNotContain=" + UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void getAllNhanViensBySoCMNDIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where soCMND equals to DEFAULT_SO_CMND
        defaultNhanVienShouldBeFound("soCMND.equals=" + DEFAULT_SO_CMND);

        // Get all the nhanVienList where soCMND equals to UPDATED_SO_CMND
        defaultNhanVienShouldNotBeFound("soCMND.equals=" + UPDATED_SO_CMND);
    }

    @Test
    @Transactional
    void getAllNhanViensBySoCMNDIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where soCMND in DEFAULT_SO_CMND or UPDATED_SO_CMND
        defaultNhanVienShouldBeFound("soCMND.in=" + DEFAULT_SO_CMND + "," + UPDATED_SO_CMND);

        // Get all the nhanVienList where soCMND equals to UPDATED_SO_CMND
        defaultNhanVienShouldNotBeFound("soCMND.in=" + UPDATED_SO_CMND);
    }

    @Test
    @Transactional
    void getAllNhanViensBySoCMNDIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where soCMND is not null
        defaultNhanVienShouldBeFound("soCMND.specified=true");

        // Get all the nhanVienList where soCMND is null
        defaultNhanVienShouldNotBeFound("soCMND.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensBySoCMNDContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where soCMND contains DEFAULT_SO_CMND
        defaultNhanVienShouldBeFound("soCMND.contains=" + DEFAULT_SO_CMND);

        // Get all the nhanVienList where soCMND contains UPDATED_SO_CMND
        defaultNhanVienShouldNotBeFound("soCMND.contains=" + UPDATED_SO_CMND);
    }

    @Test
    @Transactional
    void getAllNhanViensBySoCMNDNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where soCMND does not contain DEFAULT_SO_CMND
        defaultNhanVienShouldNotBeFound("soCMND.doesNotContain=" + DEFAULT_SO_CMND);

        // Get all the nhanVienList where soCMND does not contain UPDATED_SO_CMND
        defaultNhanVienShouldBeFound("soCMND.doesNotContain=" + UPDATED_SO_CMND);
    }

    @Test
    @Transactional
    void getAllNhanViensBysDTIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where sDT equals to DEFAULT_S_DT
        defaultNhanVienShouldBeFound("sDT.equals=" + DEFAULT_S_DT);

        // Get all the nhanVienList where sDT equals to UPDATED_S_DT
        defaultNhanVienShouldNotBeFound("sDT.equals=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNhanViensBysDTIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where sDT in DEFAULT_S_DT or UPDATED_S_DT
        defaultNhanVienShouldBeFound("sDT.in=" + DEFAULT_S_DT + "," + UPDATED_S_DT);

        // Get all the nhanVienList where sDT equals to UPDATED_S_DT
        defaultNhanVienShouldNotBeFound("sDT.in=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNhanViensBysDTIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where sDT is not null
        defaultNhanVienShouldBeFound("sDT.specified=true");

        // Get all the nhanVienList where sDT is null
        defaultNhanVienShouldNotBeFound("sDT.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensBysDTContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where sDT contains DEFAULT_S_DT
        defaultNhanVienShouldBeFound("sDT.contains=" + DEFAULT_S_DT);

        // Get all the nhanVienList where sDT contains UPDATED_S_DT
        defaultNhanVienShouldNotBeFound("sDT.contains=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNhanViensBysDTNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where sDT does not contain DEFAULT_S_DT
        defaultNhanVienShouldNotBeFound("sDT.doesNotContain=" + DEFAULT_S_DT);

        // Get all the nhanVienList where sDT does not contain UPDATED_S_DT
        defaultNhanVienShouldBeFound("sDT.doesNotContain=" + UPDATED_S_DT);
    }

    @Test
    @Transactional
    void getAllNhanViensByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where email equals to DEFAULT_EMAIL
        defaultNhanVienShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the nhanVienList where email equals to UPDATED_EMAIL
        defaultNhanVienShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNhanViensByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultNhanVienShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the nhanVienList where email equals to UPDATED_EMAIL
        defaultNhanVienShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNhanViensByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where email is not null
        defaultNhanVienShouldBeFound("email.specified=true");

        // Get all the nhanVienList where email is null
        defaultNhanVienShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByEmailContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where email contains DEFAULT_EMAIL
        defaultNhanVienShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the nhanVienList where email contains UPDATED_EMAIL
        defaultNhanVienShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNhanViensByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where email does not contain DEFAULT_EMAIL
        defaultNhanVienShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the nhanVienList where email does not contain UPDATED_EMAIL
        defaultNhanVienShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNhanViensByHeSoLuongIsEqualToSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where heSoLuong equals to DEFAULT_HE_SO_LUONG
        defaultNhanVienShouldBeFound("heSoLuong.equals=" + DEFAULT_HE_SO_LUONG);

        // Get all the nhanVienList where heSoLuong equals to UPDATED_HE_SO_LUONG
        defaultNhanVienShouldNotBeFound("heSoLuong.equals=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllNhanViensByHeSoLuongIsInShouldWork() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where heSoLuong in DEFAULT_HE_SO_LUONG or UPDATED_HE_SO_LUONG
        defaultNhanVienShouldBeFound("heSoLuong.in=" + DEFAULT_HE_SO_LUONG + "," + UPDATED_HE_SO_LUONG);

        // Get all the nhanVienList where heSoLuong equals to UPDATED_HE_SO_LUONG
        defaultNhanVienShouldNotBeFound("heSoLuong.in=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllNhanViensByHeSoLuongIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where heSoLuong is not null
        defaultNhanVienShouldBeFound("heSoLuong.specified=true");

        // Get all the nhanVienList where heSoLuong is null
        defaultNhanVienShouldNotBeFound("heSoLuong.specified=false");
    }

    @Test
    @Transactional
    void getAllNhanViensByHeSoLuongContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where heSoLuong contains DEFAULT_HE_SO_LUONG
        defaultNhanVienShouldBeFound("heSoLuong.contains=" + DEFAULT_HE_SO_LUONG);

        // Get all the nhanVienList where heSoLuong contains UPDATED_HE_SO_LUONG
        defaultNhanVienShouldNotBeFound("heSoLuong.contains=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllNhanViensByHeSoLuongNotContainsSomething() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList where heSoLuong does not contain DEFAULT_HE_SO_LUONG
        defaultNhanVienShouldNotBeFound("heSoLuong.doesNotContain=" + DEFAULT_HE_SO_LUONG);

        // Get all the nhanVienList where heSoLuong does not contain UPDATED_HE_SO_LUONG
        defaultNhanVienShouldBeFound("heSoLuong.doesNotContain=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllNhanViensByNguoiThanIsEqualToSomething() throws Exception {
        NguoiThan nguoiThan;
        if (TestUtil.findAll(em, NguoiThan.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            nguoiThan = NguoiThanResourceIT.createEntity(em);
        } else {
            nguoiThan = TestUtil.findAll(em, NguoiThan.class).get(0);
        }
        em.persist(nguoiThan);
        em.flush();
        nhanVien.setNguoiThan(nguoiThan);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long nguoiThanId = nguoiThan.getId();

        // Get all the nhanVienList where nguoiThan equals to nguoiThanId
        defaultNhanVienShouldBeFound("nguoiThanId.equals=" + nguoiThanId);

        // Get all the nhanVienList where nguoiThan equals to (nguoiThanId + 1)
        defaultNhanVienShouldNotBeFound("nguoiThanId.equals=" + (nguoiThanId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByLuongIsEqualToSomething() throws Exception {
        Luong luong;
        if (TestUtil.findAll(em, Luong.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            luong = LuongResourceIT.createEntity(em);
        } else {
            luong = TestUtil.findAll(em, Luong.class).get(0);
        }
        em.persist(luong);
        em.flush();
        nhanVien.addLuong(luong);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long luongId = luong.getId();

        // Get all the nhanVienList where luong equals to luongId
        defaultNhanVienShouldBeFound("luongId.equals=" + luongId);

        // Get all the nhanVienList where luong equals to (luongId + 1)
        defaultNhanVienShouldNotBeFound("luongId.equals=" + (luongId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByKhenThuongIsEqualToSomething() throws Exception {
        KhenThuong khenThuong;
        if (TestUtil.findAll(em, KhenThuong.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            khenThuong = KhenThuongResourceIT.createEntity(em);
        } else {
            khenThuong = TestUtil.findAll(em, KhenThuong.class).get(0);
        }
        em.persist(khenThuong);
        em.flush();
        nhanVien.addKhenThuong(khenThuong);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long khenThuongId = khenThuong.getId();

        // Get all the nhanVienList where khenThuong equals to khenThuongId
        defaultNhanVienShouldBeFound("khenThuongId.equals=" + khenThuongId);

        // Get all the nhanVienList where khenThuong equals to (khenThuongId + 1)
        defaultNhanVienShouldNotBeFound("khenThuongId.equals=" + (khenThuongId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByChucVuIsEqualToSomething() throws Exception {
        ChucVu chucVu;
        if (TestUtil.findAll(em, ChucVu.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            chucVu = ChucVuResourceIT.createEntity(em);
        } else {
            chucVu = TestUtil.findAll(em, ChucVu.class).get(0);
        }
        em.persist(chucVu);
        em.flush();
        nhanVien.setChucVu(chucVu);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long chucVuId = chucVu.getId();

        // Get all the nhanVienList where chucVu equals to chucVuId
        defaultNhanVienShouldBeFound("chucVuId.equals=" + chucVuId);

        // Get all the nhanVienList where chucVu equals to (chucVuId + 1)
        defaultNhanVienShouldNotBeFound("chucVuId.equals=" + (chucVuId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByPhongBanIsEqualToSomething() throws Exception {
        PhongBan phongBan;
        if (TestUtil.findAll(em, PhongBan.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            phongBan = PhongBanResourceIT.createEntity(em);
        } else {
            phongBan = TestUtil.findAll(em, PhongBan.class).get(0);
        }
        em.persist(phongBan);
        em.flush();
        nhanVien.setPhongBan(phongBan);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long phongBanId = phongBan.getId();

        // Get all the nhanVienList where phongBan equals to phongBanId
        defaultNhanVienShouldBeFound("phongBanId.equals=" + phongBanId);

        // Get all the nhanVienList where phongBan equals to (phongBanId + 1)
        defaultNhanVienShouldNotBeFound("phongBanId.equals=" + (phongBanId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByChuyenMonIsEqualToSomething() throws Exception {
        ChuyenMon chuyenMon;
        if (TestUtil.findAll(em, ChuyenMon.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            chuyenMon = ChuyenMonResourceIT.createEntity(em);
        } else {
            chuyenMon = TestUtil.findAll(em, ChuyenMon.class).get(0);
        }
        em.persist(chuyenMon);
        em.flush();
        nhanVien.setChuyenMon(chuyenMon);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long chuyenMonId = chuyenMon.getId();

        // Get all the nhanVienList where chuyenMon equals to chuyenMonId
        defaultNhanVienShouldBeFound("chuyenMonId.equals=" + chuyenMonId);

        // Get all the nhanVienList where chuyenMon equals to (chuyenMonId + 1)
        defaultNhanVienShouldNotBeFound("chuyenMonId.equals=" + (chuyenMonId + 1));
    }

    @Test
    @Transactional
    void getAllNhanViensByTrinhDoHVIsEqualToSomething() throws Exception {
        TrinhDoHV trinhDoHV;
        if (TestUtil.findAll(em, TrinhDoHV.class).isEmpty()) {
            nhanVienRepository.saveAndFlush(nhanVien);
            trinhDoHV = TrinhDoHVResourceIT.createEntity(em);
        } else {
            trinhDoHV = TestUtil.findAll(em, TrinhDoHV.class).get(0);
        }
        em.persist(trinhDoHV);
        em.flush();
        nhanVien.setTrinhDoHV(trinhDoHV);
        nhanVienRepository.saveAndFlush(nhanVien);
        Long trinhDoHVId = trinhDoHV.getId();

        // Get all the nhanVienList where trinhDoHV equals to trinhDoHVId
        defaultNhanVienShouldBeFound("trinhDoHVId.equals=" + trinhDoHVId);

        // Get all the nhanVienList where trinhDoHV equals to (trinhDoHVId + 1)
        defaultNhanVienShouldNotBeFound("trinhDoHVId.equals=" + (trinhDoHVId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNhanVienShouldBeFound(String filter) throws Exception {
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNV").value(hasItem(DEFAULT_MA_NV)))
            .andExpect(jsonPath("$.[*].tenNV").value(hasItem(DEFAULT_TEN_NV)))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(sameInstant(DEFAULT_NGAY_SINH))))
            .andExpect(jsonPath("$.[*].gioiTinh").value(hasItem(DEFAULT_GIOI_TINH)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].soCMND").value(hasItem(DEFAULT_SO_CMND)))
            .andExpect(jsonPath("$.[*].sDT").value(hasItem(DEFAULT_S_DT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].heSoLuong").value(hasItem(DEFAULT_HE_SO_LUONG)));

        // Check, that the count call also returns 1
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNhanVienShouldNotBeFound(String filter) throws Exception {
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNhanVien() throws Exception {
        // Get the nhanVien
        restNhanVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien
        NhanVien updatedNhanVien = nhanVienRepository.findById(nhanVien.getId()).get();
        // Disconnect from session so that the updates on updatedNhanVien are not directly saved in db
        em.detach(updatedNhanVien);
        updatedNhanVien
            .maNV(UPDATED_MA_NV)
            .tenNV(UPDATED_TEN_NV)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioiTinh(UPDATED_GIOI_TINH)
            .diaChi(UPDATED_DIA_CHI)
            .soCMND(UPDATED_SO_CMND)
            .sDT(UPDATED_S_DT)
            .email(UPDATED_EMAIL)
            .heSoLuong(UPDATED_HE_SO_LUONG);

        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNV()).isEqualTo(UPDATED_MA_NV);
        assertThat(testNhanVien.getTenNV()).isEqualTo(UPDATED_TEN_NV);
        assertThat(testNhanVien.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testNhanVien.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoCMND()).isEqualTo(UPDATED_SO_CMND);
        assertThat(testNhanVien.getsDT()).isEqualTo(UPDATED_S_DT);
        assertThat(testNhanVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhanVien.getHeSoLuong()).isEqualTo(UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void putNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien
            .tenNV(UPDATED_TEN_NV)
            .gioiTinh(UPDATED_GIOI_TINH)
            .diaChi(UPDATED_DIA_CHI)
            .soCMND(UPDATED_SO_CMND)
            .sDT(UPDATED_S_DT);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNV()).isEqualTo(DEFAULT_MA_NV);
        assertThat(testNhanVien.getTenNV()).isEqualTo(UPDATED_TEN_NV);
        assertThat(testNhanVien.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
        assertThat(testNhanVien.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoCMND()).isEqualTo(UPDATED_SO_CMND);
        assertThat(testNhanVien.getsDT()).isEqualTo(UPDATED_S_DT);
        assertThat(testNhanVien.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testNhanVien.getHeSoLuong()).isEqualTo(DEFAULT_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void fullUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien
            .maNV(UPDATED_MA_NV)
            .tenNV(UPDATED_TEN_NV)
            .ngaySinh(UPDATED_NGAY_SINH)
            .gioiTinh(UPDATED_GIOI_TINH)
            .diaChi(UPDATED_DIA_CHI)
            .soCMND(UPDATED_SO_CMND)
            .sDT(UPDATED_S_DT)
            .email(UPDATED_EMAIL)
            .heSoLuong(UPDATED_HE_SO_LUONG);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNV()).isEqualTo(UPDATED_MA_NV);
        assertThat(testNhanVien.getTenNV()).isEqualTo(UPDATED_TEN_NV);
        assertThat(testNhanVien.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testNhanVien.getGioiTinh()).isEqualTo(UPDATED_GIOI_TINH);
        assertThat(testNhanVien.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhanVien.getSoCMND()).isEqualTo(UPDATED_SO_CMND);
        assertThat(testNhanVien.getsDT()).isEqualTo(UPDATED_S_DT);
        assertThat(testNhanVien.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testNhanVien.getHeSoLuong()).isEqualTo(UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void patchNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeDelete = nhanVienRepository.findAll().size();

        // Delete the nhanVien
        restNhanVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhanVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
