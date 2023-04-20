package vn.qlns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NhanVien.
 */
@Entity
@Table(name = "nhan_vien")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "ma_nv", length = 50, nullable = false)
    private String maNV;

    @NotNull
    @Size(max = 256)
    @Column(name = "ten_nv", length = 256, nullable = false)
    private String tenNV;

    @NotNull
    @Column(name = "ngay_sinh", nullable = false)
    private ZonedDateTime ngaySinh;

    @NotNull
    @Column(name = "gioi_tinh", nullable = false)
    private String gioiTinh;

    @Size(max = 1024)
    @Column(name = "dia_chi", length = 1024)
    private String diaChi;

    @NotNull
    @Column(name = "so_cmnd", nullable = false)
    private String soCMND;

    @Size(max = 10)
    @Column(name = "s_dt", length = 10)
    private String sDT;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 50)
    @Column(name = "he_so_luong", length = 50)
    private String heSoLuong;

    @JsonIgnoreProperties(value = { "nhanVien" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private NguoiThan nguoiThan;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanVien")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhanVien" }, allowSetters = true)
    private Set<Luong> luongs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanVien")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhanVien" }, allowSetters = true)
    private Set<KhenThuong> khenThuongs = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "nhanViens" }, allowSetters = true)
    private ChucVu chucVu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "nhanViens" }, allowSetters = true)
    private PhongBan phongBan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "nhanViens" }, allowSetters = true)
    private ChuyenMon chuyenMon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "nhanViens" }, allowSetters = true)
    private TrinhDoHV trinhDoHV;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NhanVien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaNV() {
        return this.maNV;
    }

    public NhanVien maNV(String maNV) {
        this.setMaNV(maNV);
        return this;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return this.tenNV;
    }

    public NhanVien tenNV(String tenNV) {
        this.setTenNV(tenNV);
        return this;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public ZonedDateTime getNgaySinh() {
        return this.ngaySinh;
    }

    public NhanVien ngaySinh(ZonedDateTime ngaySinh) {
        this.setNgaySinh(ngaySinh);
        return this;
    }

    public void setNgaySinh(ZonedDateTime ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return this.gioiTinh;
    }

    public NhanVien gioiTinh(String gioiTinh) {
        this.setGioiTinh(gioiTinh);
        return this;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public NhanVien diaChi(String diaChi) {
        this.setDiaChi(diaChi);
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoCMND() {
        return this.soCMND;
    }

    public NhanVien soCMND(String soCMND) {
        this.setSoCMND(soCMND);
        return this;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getsDT() {
        return this.sDT;
    }

    public NhanVien sDT(String sDT) {
        this.setsDT(sDT);
        return this;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getEmail() {
        return this.email;
    }

    public NhanVien email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeSoLuong() {
        return this.heSoLuong;
    }

    public NhanVien heSoLuong(String heSoLuong) {
        this.setHeSoLuong(heSoLuong);
        return this;
    }

    public void setHeSoLuong(String heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public NguoiThan getNguoiThan() {
        return this.nguoiThan;
    }

    public void setNguoiThan(NguoiThan nguoiThan) {
        this.nguoiThan = nguoiThan;
    }

    public NhanVien nguoiThan(NguoiThan nguoiThan) {
        this.setNguoiThan(nguoiThan);
        return this;
    }

    public Set<Luong> getLuongs() {
        return this.luongs;
    }

    public void setLuongs(Set<Luong> luongs) {
        if (this.luongs != null) {
            this.luongs.forEach(i -> i.setNhanVien(null));
        }
        if (luongs != null) {
            luongs.forEach(i -> i.setNhanVien(this));
        }
        this.luongs = luongs;
    }

    public NhanVien luongs(Set<Luong> luongs) {
        this.setLuongs(luongs);
        return this;
    }

    public NhanVien addLuong(Luong luong) {
        this.luongs.add(luong);
        luong.setNhanVien(this);
        return this;
    }

    public NhanVien removeLuong(Luong luong) {
        this.luongs.remove(luong);
        luong.setNhanVien(null);
        return this;
    }

    public Set<KhenThuong> getKhenThuongs() {
        return this.khenThuongs;
    }

    public void setKhenThuongs(Set<KhenThuong> khenThuongs) {
        if (this.khenThuongs != null) {
            this.khenThuongs.forEach(i -> i.setNhanVien(null));
        }
        if (khenThuongs != null) {
            khenThuongs.forEach(i -> i.setNhanVien(this));
        }
        this.khenThuongs = khenThuongs;
    }

    public NhanVien khenThuongs(Set<KhenThuong> khenThuongs) {
        this.setKhenThuongs(khenThuongs);
        return this;
    }

    public NhanVien addKhenThuong(KhenThuong khenThuong) {
        this.khenThuongs.add(khenThuong);
        khenThuong.setNhanVien(this);
        return this;
    }

    public NhanVien removeKhenThuong(KhenThuong khenThuong) {
        this.khenThuongs.remove(khenThuong);
        khenThuong.setNhanVien(null);
        return this;
    }

    public ChucVu getChucVu() {
        return this.chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public NhanVien chucVu(ChucVu chucVu) {
        this.setChucVu(chucVu);
        return this;
    }

    public PhongBan getPhongBan() {
        return this.phongBan;
    }

    public void setPhongBan(PhongBan phongBan) {
        this.phongBan = phongBan;
    }

    public NhanVien phongBan(PhongBan phongBan) {
        this.setPhongBan(phongBan);
        return this;
    }

    public ChuyenMon getChuyenMon() {
        return this.chuyenMon;
    }

    public void setChuyenMon(ChuyenMon chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public NhanVien chuyenMon(ChuyenMon chuyenMon) {
        this.setChuyenMon(chuyenMon);
        return this;
    }

    public TrinhDoHV getTrinhDoHV() {
        return this.trinhDoHV;
    }

    public void setTrinhDoHV(TrinhDoHV trinhDoHV) {
        this.trinhDoHV = trinhDoHV;
    }

    public NhanVien trinhDoHV(TrinhDoHV trinhDoHV) {
        this.setTrinhDoHV(trinhDoHV);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhanVien)) {
            return false;
        }
        return id != null && id.equals(((NhanVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVien{" +
            "id=" + getId() +
            ", maNV='" + getMaNV() + "'" +
            ", tenNV='" + getTenNV() + "'" +
            ", ngaySinh='" + getNgaySinh() + "'" +
            ", gioiTinh='" + getGioiTinh() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", soCMND='" + getSoCMND() + "'" +
            ", sDT='" + getsDT() + "'" +
            ", email='" + getEmail() + "'" +
            ", heSoLuong='" + getHeSoLuong() + "'" +
            "}";
    }
}
