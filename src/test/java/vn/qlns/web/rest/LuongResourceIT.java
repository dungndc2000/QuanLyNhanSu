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
import vn.qlns.domain.Luong;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.LuongRepository;
import vn.qlns.service.criteria.LuongCriteria;

/**
 * Integration tests for the {@link LuongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LuongResourceIT {

    private static final String DEFAULT_MA_LUONG = "AAAAAAAAAA";
    private static final String UPDATED_MA_LUONG = "BBBBBBBBBB";

    private static final String DEFAULT_HE_SO_LUONG = "AAAAAAAAAA";
    private static final String UPDATED_HE_SO_LUONG = "BBBBBBBBBB";

    private static final String DEFAULT_LUONG_CB = "AAAAAAAAAA";
    private static final String UPDATED_LUONG_CB = "BBBBBBBBBB";

    private static final String DEFAULT_HE_SO_PHU_CAP = "AAAAAAAAAA";
    private static final String UPDATED_HE_SO_PHU_CAP = "BBBBBBBBBB";

    private static final String DEFAULT_KHOAN_CONG_THEM = "AAAAAAAAAA";
    private static final String UPDATED_KHOAN_CONG_THEM = "BBBBBBBBBB";

    private static final String DEFAULT_KHOAN_TRU = "AAAAAAAAAA";
    private static final String UPDATED_KHOAN_TRU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/luongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LuongRepository luongRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLuongMockMvc;

    private Luong luong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Luong createEntity(EntityManager em) {
        Luong luong = new Luong()
            .maLuong(DEFAULT_MA_LUONG)
            .heSoLuong(DEFAULT_HE_SO_LUONG)
            .luongCb(DEFAULT_LUONG_CB)
            .heSoPhuCap(DEFAULT_HE_SO_PHU_CAP)
            .khoanCongThem(DEFAULT_KHOAN_CONG_THEM)
            .khoanTru(DEFAULT_KHOAN_TRU);
        return luong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Luong createUpdatedEntity(EntityManager em) {
        Luong luong = new Luong()
            .maLuong(UPDATED_MA_LUONG)
            .heSoLuong(UPDATED_HE_SO_LUONG)
            .luongCb(UPDATED_LUONG_CB)
            .heSoPhuCap(UPDATED_HE_SO_PHU_CAP)
            .khoanCongThem(UPDATED_KHOAN_CONG_THEM)
            .khoanTru(UPDATED_KHOAN_TRU);
        return luong;
    }

    @BeforeEach
    public void initTest() {
        luong = createEntity(em);
    }

    @Test
    @Transactional
    void createLuong() throws Exception {
        int databaseSizeBeforeCreate = luongRepository.findAll().size();
        // Create the Luong
        restLuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(luong)))
            .andExpect(status().isCreated());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeCreate + 1);
        Luong testLuong = luongList.get(luongList.size() - 1);
        assertThat(testLuong.getMaLuong()).isEqualTo(DEFAULT_MA_LUONG);
        assertThat(testLuong.getHeSoLuong()).isEqualTo(DEFAULT_HE_SO_LUONG);
        assertThat(testLuong.getLuongCb()).isEqualTo(DEFAULT_LUONG_CB);
        assertThat(testLuong.getHeSoPhuCap()).isEqualTo(DEFAULT_HE_SO_PHU_CAP);
        assertThat(testLuong.getKhoanCongThem()).isEqualTo(DEFAULT_KHOAN_CONG_THEM);
        assertThat(testLuong.getKhoanTru()).isEqualTo(DEFAULT_KHOAN_TRU);
    }

    @Test
    @Transactional
    void createLuongWithExistingId() throws Exception {
        // Create the Luong with an existing ID
        luong.setId(1L);

        int databaseSizeBeforeCreate = luongRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(luong)))
            .andExpect(status().isBadRequest());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaLuongIsRequired() throws Exception {
        int databaseSizeBeforeTest = luongRepository.findAll().size();
        // set the field null
        luong.setMaLuong(null);

        // Create the Luong, which fails.

        restLuongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(luong)))
            .andExpect(status().isBadRequest());

        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLuongs() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList
        restLuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(luong.getId().intValue())))
            .andExpect(jsonPath("$.[*].maLuong").value(hasItem(DEFAULT_MA_LUONG)))
            .andExpect(jsonPath("$.[*].heSoLuong").value(hasItem(DEFAULT_HE_SO_LUONG)))
            .andExpect(jsonPath("$.[*].luongCb").value(hasItem(DEFAULT_LUONG_CB)))
            .andExpect(jsonPath("$.[*].heSoPhuCap").value(hasItem(DEFAULT_HE_SO_PHU_CAP)))
            .andExpect(jsonPath("$.[*].khoanCongThem").value(hasItem(DEFAULT_KHOAN_CONG_THEM)))
            .andExpect(jsonPath("$.[*].khoanTru").value(hasItem(DEFAULT_KHOAN_TRU)));
    }

    @Test
    @Transactional
    void getLuong() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get the luong
        restLuongMockMvc
            .perform(get(ENTITY_API_URL_ID, luong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(luong.getId().intValue()))
            .andExpect(jsonPath("$.maLuong").value(DEFAULT_MA_LUONG))
            .andExpect(jsonPath("$.heSoLuong").value(DEFAULT_HE_SO_LUONG))
            .andExpect(jsonPath("$.luongCb").value(DEFAULT_LUONG_CB))
            .andExpect(jsonPath("$.heSoPhuCap").value(DEFAULT_HE_SO_PHU_CAP))
            .andExpect(jsonPath("$.khoanCongThem").value(DEFAULT_KHOAN_CONG_THEM))
            .andExpect(jsonPath("$.khoanTru").value(DEFAULT_KHOAN_TRU));
    }

    @Test
    @Transactional
    void getLuongsByIdFiltering() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        Long id = luong.getId();

        defaultLuongShouldBeFound("id.equals=" + id);
        defaultLuongShouldNotBeFound("id.notEquals=" + id);

        defaultLuongShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLuongShouldNotBeFound("id.greaterThan=" + id);

        defaultLuongShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLuongShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLuongsByMaLuongIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where maLuong equals to DEFAULT_MA_LUONG
        defaultLuongShouldBeFound("maLuong.equals=" + DEFAULT_MA_LUONG);

        // Get all the luongList where maLuong equals to UPDATED_MA_LUONG
        defaultLuongShouldNotBeFound("maLuong.equals=" + UPDATED_MA_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByMaLuongIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where maLuong in DEFAULT_MA_LUONG or UPDATED_MA_LUONG
        defaultLuongShouldBeFound("maLuong.in=" + DEFAULT_MA_LUONG + "," + UPDATED_MA_LUONG);

        // Get all the luongList where maLuong equals to UPDATED_MA_LUONG
        defaultLuongShouldNotBeFound("maLuong.in=" + UPDATED_MA_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByMaLuongIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where maLuong is not null
        defaultLuongShouldBeFound("maLuong.specified=true");

        // Get all the luongList where maLuong is null
        defaultLuongShouldNotBeFound("maLuong.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByMaLuongContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where maLuong contains DEFAULT_MA_LUONG
        defaultLuongShouldBeFound("maLuong.contains=" + DEFAULT_MA_LUONG);

        // Get all the luongList where maLuong contains UPDATED_MA_LUONG
        defaultLuongShouldNotBeFound("maLuong.contains=" + UPDATED_MA_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByMaLuongNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where maLuong does not contain DEFAULT_MA_LUONG
        defaultLuongShouldNotBeFound("maLuong.doesNotContain=" + DEFAULT_MA_LUONG);

        // Get all the luongList where maLuong does not contain UPDATED_MA_LUONG
        defaultLuongShouldBeFound("maLuong.doesNotContain=" + UPDATED_MA_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoLuongIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoLuong equals to DEFAULT_HE_SO_LUONG
        defaultLuongShouldBeFound("heSoLuong.equals=" + DEFAULT_HE_SO_LUONG);

        // Get all the luongList where heSoLuong equals to UPDATED_HE_SO_LUONG
        defaultLuongShouldNotBeFound("heSoLuong.equals=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoLuongIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoLuong in DEFAULT_HE_SO_LUONG or UPDATED_HE_SO_LUONG
        defaultLuongShouldBeFound("heSoLuong.in=" + DEFAULT_HE_SO_LUONG + "," + UPDATED_HE_SO_LUONG);

        // Get all the luongList where heSoLuong equals to UPDATED_HE_SO_LUONG
        defaultLuongShouldNotBeFound("heSoLuong.in=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoLuongIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoLuong is not null
        defaultLuongShouldBeFound("heSoLuong.specified=true");

        // Get all the luongList where heSoLuong is null
        defaultLuongShouldNotBeFound("heSoLuong.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoLuongContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoLuong contains DEFAULT_HE_SO_LUONG
        defaultLuongShouldBeFound("heSoLuong.contains=" + DEFAULT_HE_SO_LUONG);

        // Get all the luongList where heSoLuong contains UPDATED_HE_SO_LUONG
        defaultLuongShouldNotBeFound("heSoLuong.contains=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoLuongNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoLuong does not contain DEFAULT_HE_SO_LUONG
        defaultLuongShouldNotBeFound("heSoLuong.doesNotContain=" + DEFAULT_HE_SO_LUONG);

        // Get all the luongList where heSoLuong does not contain UPDATED_HE_SO_LUONG
        defaultLuongShouldBeFound("heSoLuong.doesNotContain=" + UPDATED_HE_SO_LUONG);
    }

    @Test
    @Transactional
    void getAllLuongsByLuongCbIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where luongCb equals to DEFAULT_LUONG_CB
        defaultLuongShouldBeFound("luongCb.equals=" + DEFAULT_LUONG_CB);

        // Get all the luongList where luongCb equals to UPDATED_LUONG_CB
        defaultLuongShouldNotBeFound("luongCb.equals=" + UPDATED_LUONG_CB);
    }

    @Test
    @Transactional
    void getAllLuongsByLuongCbIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where luongCb in DEFAULT_LUONG_CB or UPDATED_LUONG_CB
        defaultLuongShouldBeFound("luongCb.in=" + DEFAULT_LUONG_CB + "," + UPDATED_LUONG_CB);

        // Get all the luongList where luongCb equals to UPDATED_LUONG_CB
        defaultLuongShouldNotBeFound("luongCb.in=" + UPDATED_LUONG_CB);
    }

    @Test
    @Transactional
    void getAllLuongsByLuongCbIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where luongCb is not null
        defaultLuongShouldBeFound("luongCb.specified=true");

        // Get all the luongList where luongCb is null
        defaultLuongShouldNotBeFound("luongCb.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByLuongCbContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where luongCb contains DEFAULT_LUONG_CB
        defaultLuongShouldBeFound("luongCb.contains=" + DEFAULT_LUONG_CB);

        // Get all the luongList where luongCb contains UPDATED_LUONG_CB
        defaultLuongShouldNotBeFound("luongCb.contains=" + UPDATED_LUONG_CB);
    }

    @Test
    @Transactional
    void getAllLuongsByLuongCbNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where luongCb does not contain DEFAULT_LUONG_CB
        defaultLuongShouldNotBeFound("luongCb.doesNotContain=" + DEFAULT_LUONG_CB);

        // Get all the luongList where luongCb does not contain UPDATED_LUONG_CB
        defaultLuongShouldBeFound("luongCb.doesNotContain=" + UPDATED_LUONG_CB);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoPhuCapIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoPhuCap equals to DEFAULT_HE_SO_PHU_CAP
        defaultLuongShouldBeFound("heSoPhuCap.equals=" + DEFAULT_HE_SO_PHU_CAP);

        // Get all the luongList where heSoPhuCap equals to UPDATED_HE_SO_PHU_CAP
        defaultLuongShouldNotBeFound("heSoPhuCap.equals=" + UPDATED_HE_SO_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoPhuCapIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoPhuCap in DEFAULT_HE_SO_PHU_CAP or UPDATED_HE_SO_PHU_CAP
        defaultLuongShouldBeFound("heSoPhuCap.in=" + DEFAULT_HE_SO_PHU_CAP + "," + UPDATED_HE_SO_PHU_CAP);

        // Get all the luongList where heSoPhuCap equals to UPDATED_HE_SO_PHU_CAP
        defaultLuongShouldNotBeFound("heSoPhuCap.in=" + UPDATED_HE_SO_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoPhuCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoPhuCap is not null
        defaultLuongShouldBeFound("heSoPhuCap.specified=true");

        // Get all the luongList where heSoPhuCap is null
        defaultLuongShouldNotBeFound("heSoPhuCap.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoPhuCapContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoPhuCap contains DEFAULT_HE_SO_PHU_CAP
        defaultLuongShouldBeFound("heSoPhuCap.contains=" + DEFAULT_HE_SO_PHU_CAP);

        // Get all the luongList where heSoPhuCap contains UPDATED_HE_SO_PHU_CAP
        defaultLuongShouldNotBeFound("heSoPhuCap.contains=" + UPDATED_HE_SO_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllLuongsByHeSoPhuCapNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where heSoPhuCap does not contain DEFAULT_HE_SO_PHU_CAP
        defaultLuongShouldNotBeFound("heSoPhuCap.doesNotContain=" + DEFAULT_HE_SO_PHU_CAP);

        // Get all the luongList where heSoPhuCap does not contain UPDATED_HE_SO_PHU_CAP
        defaultLuongShouldBeFound("heSoPhuCap.doesNotContain=" + UPDATED_HE_SO_PHU_CAP);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanCongThemIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanCongThem equals to DEFAULT_KHOAN_CONG_THEM
        defaultLuongShouldBeFound("khoanCongThem.equals=" + DEFAULT_KHOAN_CONG_THEM);

        // Get all the luongList where khoanCongThem equals to UPDATED_KHOAN_CONG_THEM
        defaultLuongShouldNotBeFound("khoanCongThem.equals=" + UPDATED_KHOAN_CONG_THEM);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanCongThemIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanCongThem in DEFAULT_KHOAN_CONG_THEM or UPDATED_KHOAN_CONG_THEM
        defaultLuongShouldBeFound("khoanCongThem.in=" + DEFAULT_KHOAN_CONG_THEM + "," + UPDATED_KHOAN_CONG_THEM);

        // Get all the luongList where khoanCongThem equals to UPDATED_KHOAN_CONG_THEM
        defaultLuongShouldNotBeFound("khoanCongThem.in=" + UPDATED_KHOAN_CONG_THEM);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanCongThemIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanCongThem is not null
        defaultLuongShouldBeFound("khoanCongThem.specified=true");

        // Get all the luongList where khoanCongThem is null
        defaultLuongShouldNotBeFound("khoanCongThem.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanCongThemContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanCongThem contains DEFAULT_KHOAN_CONG_THEM
        defaultLuongShouldBeFound("khoanCongThem.contains=" + DEFAULT_KHOAN_CONG_THEM);

        // Get all the luongList where khoanCongThem contains UPDATED_KHOAN_CONG_THEM
        defaultLuongShouldNotBeFound("khoanCongThem.contains=" + UPDATED_KHOAN_CONG_THEM);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanCongThemNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanCongThem does not contain DEFAULT_KHOAN_CONG_THEM
        defaultLuongShouldNotBeFound("khoanCongThem.doesNotContain=" + DEFAULT_KHOAN_CONG_THEM);

        // Get all the luongList where khoanCongThem does not contain UPDATED_KHOAN_CONG_THEM
        defaultLuongShouldBeFound("khoanCongThem.doesNotContain=" + UPDATED_KHOAN_CONG_THEM);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanTruIsEqualToSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanTru equals to DEFAULT_KHOAN_TRU
        defaultLuongShouldBeFound("khoanTru.equals=" + DEFAULT_KHOAN_TRU);

        // Get all the luongList where khoanTru equals to UPDATED_KHOAN_TRU
        defaultLuongShouldNotBeFound("khoanTru.equals=" + UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanTruIsInShouldWork() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanTru in DEFAULT_KHOAN_TRU or UPDATED_KHOAN_TRU
        defaultLuongShouldBeFound("khoanTru.in=" + DEFAULT_KHOAN_TRU + "," + UPDATED_KHOAN_TRU);

        // Get all the luongList where khoanTru equals to UPDATED_KHOAN_TRU
        defaultLuongShouldNotBeFound("khoanTru.in=" + UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanTruIsNullOrNotNull() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanTru is not null
        defaultLuongShouldBeFound("khoanTru.specified=true");

        // Get all the luongList where khoanTru is null
        defaultLuongShouldNotBeFound("khoanTru.specified=false");
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanTruContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanTru contains DEFAULT_KHOAN_TRU
        defaultLuongShouldBeFound("khoanTru.contains=" + DEFAULT_KHOAN_TRU);

        // Get all the luongList where khoanTru contains UPDATED_KHOAN_TRU
        defaultLuongShouldNotBeFound("khoanTru.contains=" + UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void getAllLuongsByKhoanTruNotContainsSomething() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        // Get all the luongList where khoanTru does not contain DEFAULT_KHOAN_TRU
        defaultLuongShouldNotBeFound("khoanTru.doesNotContain=" + DEFAULT_KHOAN_TRU);

        // Get all the luongList where khoanTru does not contain UPDATED_KHOAN_TRU
        defaultLuongShouldBeFound("khoanTru.doesNotContain=" + UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void getAllLuongsByNhanVienIsEqualToSomething() throws Exception {
        NhanVien nhanVien;
        if (TestUtil.findAll(em, NhanVien.class).isEmpty()) {
            luongRepository.saveAndFlush(luong);
            nhanVien = NhanVienResourceIT.createEntity(em);
        } else {
            nhanVien = TestUtil.findAll(em, NhanVien.class).get(0);
        }
        em.persist(nhanVien);
        em.flush();
        luong.setNhanVien(nhanVien);
        luongRepository.saveAndFlush(luong);
        Long nhanVienId = nhanVien.getId();

        // Get all the luongList where nhanVien equals to nhanVienId
        defaultLuongShouldBeFound("nhanVienId.equals=" + nhanVienId);

        // Get all the luongList where nhanVien equals to (nhanVienId + 1)
        defaultLuongShouldNotBeFound("nhanVienId.equals=" + (nhanVienId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLuongShouldBeFound(String filter) throws Exception {
        restLuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(luong.getId().intValue())))
            .andExpect(jsonPath("$.[*].maLuong").value(hasItem(DEFAULT_MA_LUONG)))
            .andExpect(jsonPath("$.[*].heSoLuong").value(hasItem(DEFAULT_HE_SO_LUONG)))
            .andExpect(jsonPath("$.[*].luongCb").value(hasItem(DEFAULT_LUONG_CB)))
            .andExpect(jsonPath("$.[*].heSoPhuCap").value(hasItem(DEFAULT_HE_SO_PHU_CAP)))
            .andExpect(jsonPath("$.[*].khoanCongThem").value(hasItem(DEFAULT_KHOAN_CONG_THEM)))
            .andExpect(jsonPath("$.[*].khoanTru").value(hasItem(DEFAULT_KHOAN_TRU)));

        // Check, that the count call also returns 1
        restLuongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLuongShouldNotBeFound(String filter) throws Exception {
        restLuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLuongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLuong() throws Exception {
        // Get the luong
        restLuongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLuong() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        int databaseSizeBeforeUpdate = luongRepository.findAll().size();

        // Update the luong
        Luong updatedLuong = luongRepository.findById(luong.getId()).get();
        // Disconnect from session so that the updates on updatedLuong are not directly saved in db
        em.detach(updatedLuong);
        updatedLuong
            .maLuong(UPDATED_MA_LUONG)
            .heSoLuong(UPDATED_HE_SO_LUONG)
            .luongCb(UPDATED_LUONG_CB)
            .heSoPhuCap(UPDATED_HE_SO_PHU_CAP)
            .khoanCongThem(UPDATED_KHOAN_CONG_THEM)
            .khoanTru(UPDATED_KHOAN_TRU);

        restLuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLuong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLuong))
            )
            .andExpect(status().isOk());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
        Luong testLuong = luongList.get(luongList.size() - 1);
        assertThat(testLuong.getMaLuong()).isEqualTo(UPDATED_MA_LUONG);
        assertThat(testLuong.getHeSoLuong()).isEqualTo(UPDATED_HE_SO_LUONG);
        assertThat(testLuong.getLuongCb()).isEqualTo(UPDATED_LUONG_CB);
        assertThat(testLuong.getHeSoPhuCap()).isEqualTo(UPDATED_HE_SO_PHU_CAP);
        assertThat(testLuong.getKhoanCongThem()).isEqualTo(UPDATED_KHOAN_CONG_THEM);
        assertThat(testLuong.getKhoanTru()).isEqualTo(UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void putNonExistingLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, luong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(luong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(luong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(luong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLuongWithPatch() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        int databaseSizeBeforeUpdate = luongRepository.findAll().size();

        // Update the luong using partial update
        Luong partialUpdatedLuong = new Luong();
        partialUpdatedLuong.setId(luong.getId());

        partialUpdatedLuong.maLuong(UPDATED_MA_LUONG).khoanCongThem(UPDATED_KHOAN_CONG_THEM);

        restLuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLuong))
            )
            .andExpect(status().isOk());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
        Luong testLuong = luongList.get(luongList.size() - 1);
        assertThat(testLuong.getMaLuong()).isEqualTo(UPDATED_MA_LUONG);
        assertThat(testLuong.getHeSoLuong()).isEqualTo(DEFAULT_HE_SO_LUONG);
        assertThat(testLuong.getLuongCb()).isEqualTo(DEFAULT_LUONG_CB);
        assertThat(testLuong.getHeSoPhuCap()).isEqualTo(DEFAULT_HE_SO_PHU_CAP);
        assertThat(testLuong.getKhoanCongThem()).isEqualTo(UPDATED_KHOAN_CONG_THEM);
        assertThat(testLuong.getKhoanTru()).isEqualTo(DEFAULT_KHOAN_TRU);
    }

    @Test
    @Transactional
    void fullUpdateLuongWithPatch() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        int databaseSizeBeforeUpdate = luongRepository.findAll().size();

        // Update the luong using partial update
        Luong partialUpdatedLuong = new Luong();
        partialUpdatedLuong.setId(luong.getId());

        partialUpdatedLuong
            .maLuong(UPDATED_MA_LUONG)
            .heSoLuong(UPDATED_HE_SO_LUONG)
            .luongCb(UPDATED_LUONG_CB)
            .heSoPhuCap(UPDATED_HE_SO_PHU_CAP)
            .khoanCongThem(UPDATED_KHOAN_CONG_THEM)
            .khoanTru(UPDATED_KHOAN_TRU);

        restLuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLuong))
            )
            .andExpect(status().isOk());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
        Luong testLuong = luongList.get(luongList.size() - 1);
        assertThat(testLuong.getMaLuong()).isEqualTo(UPDATED_MA_LUONG);
        assertThat(testLuong.getHeSoLuong()).isEqualTo(UPDATED_HE_SO_LUONG);
        assertThat(testLuong.getLuongCb()).isEqualTo(UPDATED_LUONG_CB);
        assertThat(testLuong.getHeSoPhuCap()).isEqualTo(UPDATED_HE_SO_PHU_CAP);
        assertThat(testLuong.getKhoanCongThem()).isEqualTo(UPDATED_KHOAN_CONG_THEM);
        assertThat(testLuong.getKhoanTru()).isEqualTo(UPDATED_KHOAN_TRU);
    }

    @Test
    @Transactional
    void patchNonExistingLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, luong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(luong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(luong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLuong() throws Exception {
        int databaseSizeBeforeUpdate = luongRepository.findAll().size();
        luong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLuongMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(luong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Luong in the database
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLuong() throws Exception {
        // Initialize the database
        luongRepository.saveAndFlush(luong);

        int databaseSizeBeforeDelete = luongRepository.findAll().size();

        // Delete the luong
        restLuongMockMvc
            .perform(delete(ENTITY_API_URL_ID, luong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Luong> luongList = luongRepository.findAll();
        assertThat(luongList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
