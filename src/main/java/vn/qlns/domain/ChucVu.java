package vn.qlns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChucVu.
 */
@Entity
@Table(name = "chuc_vu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChucVu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ma_cv", length = 10, nullable = false)
    private String maCV;

    @Size(max = 50)
    @Column(name = "ten_chuc_vu", length = 50)
    private String tenChucVu;

    @Size(max = 10)
    @Column(name = "phu_cap", length = 10)
    private String phuCap;

    @Size(max = 100)
    @Column(name = "ghi_chu", length = 100)
    private String ghiChu;

    @OneToMany(mappedBy = "chucVu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "nguoiThan", "luongs", "khenThuongs", "chucVu", "phongBan", "chuyenMon", "trinhDoHV" },
        allowSetters = true
    )
    private Set<NhanVien> nhanViens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChucVu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaCV() {
        return this.maCV;
    }

    public ChucVu maCV(String maCV) {
        this.setMaCV(maCV);
        return this;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenChucVu() {
        return this.tenChucVu;
    }

    public ChucVu tenChucVu(String tenChucVu) {
        this.setTenChucVu(tenChucVu);
        return this;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public String getPhuCap() {
        return this.phuCap;
    }

    public ChucVu phuCap(String phuCap) {
        this.setPhuCap(phuCap);
        return this;
    }

    public void setPhuCap(String phuCap) {
        this.phuCap = phuCap;
    }

    public String getGhiChu() {
        return this.ghiChu;
    }

    public ChucVu ghiChu(String ghiChu) {
        this.setGhiChu(ghiChu);
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<NhanVien> getNhanViens() {
        return this.nhanViens;
    }

    public void setNhanViens(Set<NhanVien> nhanViens) {
        if (this.nhanViens != null) {
            this.nhanViens.forEach(i -> i.setChucVu(null));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.setChucVu(this));
        }
        this.nhanViens = nhanViens;
    }

    public ChucVu nhanViens(Set<NhanVien> nhanViens) {
        this.setNhanViens(nhanViens);
        return this;
    }

    public ChucVu addNhanVien(NhanVien nhanVien) {
        this.nhanViens.add(nhanVien);
        nhanVien.setChucVu(this);
        return this;
    }

    public ChucVu removeNhanVien(NhanVien nhanVien) {
        this.nhanViens.remove(nhanVien);
        nhanVien.setChucVu(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChucVu)) {
            return false;
        }
        return id != null && id.equals(((ChucVu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChucVu{" +
            "id=" + getId() +
            ", maCV='" + getMaCV() + "'" +
            ", tenChucVu='" + getTenChucVu() + "'" +
            ", phuCap='" + getPhuCap() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
