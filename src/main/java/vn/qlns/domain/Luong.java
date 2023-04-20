package vn.qlns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Luong.
 */
@Entity
@Table(name = "luong")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Luong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ma_luong", length = 10, nullable = false)
    private String maLuong;

    @Size(max = 10)
    @Column(name = "he_so_luong", length = 10)
    private String heSoLuong;

    @Size(max = 20)
    @Column(name = "luong_cb", length = 20)
    private String luongCb;

    @Size(max = 20)
    @Column(name = "he_so_phu_cap", length = 20)
    private String heSoPhuCap;

    @Size(max = 20)
    @Column(name = "khoan_cong_them", length = 20)
    private String khoanCongThem;

    @Size(max = 20)
    @Column(name = "khoan_tru", length = 20)
    private String khoanTru;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "nguoiThan", "luongs", "khenThuongs", "chucVu", "phongBan", "chuyenMon", "trinhDoHV" },
        allowSetters = true
    )
    private NhanVien nhanVien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Luong id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaLuong() {
        return this.maLuong;
    }

    public Luong maLuong(String maLuong) {
        this.setMaLuong(maLuong);
        return this;
    }

    public void setMaLuong(String maLuong) {
        this.maLuong = maLuong;
    }

    public String getHeSoLuong() {
        return this.heSoLuong;
    }

    public Luong heSoLuong(String heSoLuong) {
        this.setHeSoLuong(heSoLuong);
        return this;
    }

    public void setHeSoLuong(String heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public String getLuongCb() {
        return this.luongCb;
    }

    public Luong luongCb(String luongCb) {
        this.setLuongCb(luongCb);
        return this;
    }

    public void setLuongCb(String luongCb) {
        this.luongCb = luongCb;
    }

    public String getHeSoPhuCap() {
        return this.heSoPhuCap;
    }

    public Luong heSoPhuCap(String heSoPhuCap) {
        this.setHeSoPhuCap(heSoPhuCap);
        return this;
    }

    public void setHeSoPhuCap(String heSoPhuCap) {
        this.heSoPhuCap = heSoPhuCap;
    }

    public String getKhoanCongThem() {
        return this.khoanCongThem;
    }

    public Luong khoanCongThem(String khoanCongThem) {
        this.setKhoanCongThem(khoanCongThem);
        return this;
    }

    public void setKhoanCongThem(String khoanCongThem) {
        this.khoanCongThem = khoanCongThem;
    }

    public String getKhoanTru() {
        return this.khoanTru;
    }

    public Luong khoanTru(String khoanTru) {
        this.setKhoanTru(khoanTru);
        return this;
    }

    public void setKhoanTru(String khoanTru) {
        this.khoanTru = khoanTru;
    }

    public NhanVien getNhanVien() {
        return this.nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Luong nhanVien(NhanVien nhanVien) {
        this.setNhanVien(nhanVien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Luong)) {
            return false;
        }
        return id != null && id.equals(((Luong) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Luong{" +
            "id=" + getId() +
            ", maLuong='" + getMaLuong() + "'" +
            ", heSoLuong='" + getHeSoLuong() + "'" +
            ", luongCb='" + getLuongCb() + "'" +
            ", heSoPhuCap='" + getHeSoPhuCap() + "'" +
            ", khoanCongThem='" + getKhoanCongThem() + "'" +
            ", khoanTru='" + getKhoanTru() + "'" +
            "}";
    }
}
