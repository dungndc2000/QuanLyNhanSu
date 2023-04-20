package vn.qlns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NguoiThan.
 */
@Entity
@Table(name = "nguoi_than")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NguoiThan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "ma_nt", length = 20, nullable = false)
    private String maNT;

    @Size(max = 50)
    @Column(name = "ten_nt", length = 50)
    private String tenNT;

    @Size(max = 20)
    @Column(name = "s_dt", length = 20)
    private String sDT;

    @JsonIgnoreProperties(
        value = { "nguoiThan", "luongs", "khenThuongs", "chucVu", "phongBan", "chuyenMon", "trinhDoHV" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "nguoiThan")
    private NhanVien nhanVien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NguoiThan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaNT() {
        return this.maNT;
    }

    public NguoiThan maNT(String maNT) {
        this.setMaNT(maNT);
        return this;
    }

    public void setMaNT(String maNT) {
        this.maNT = maNT;
    }

    public String getTenNT() {
        return this.tenNT;
    }

    public NguoiThan tenNT(String tenNT) {
        this.setTenNT(tenNT);
        return this;
    }

    public void setTenNT(String tenNT) {
        this.tenNT = tenNT;
    }

    public String getsDT() {
        return this.sDT;
    }

    public NguoiThan sDT(String sDT) {
        this.setsDT(sDT);
        return this;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public NhanVien getNhanVien() {
        return this.nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (this.nhanVien != null) {
            this.nhanVien.setNguoiThan(null);
        }
        if (nhanVien != null) {
            nhanVien.setNguoiThan(this);
        }
        this.nhanVien = nhanVien;
    }

    public NguoiThan nhanVien(NhanVien nhanVien) {
        this.setNhanVien(nhanVien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NguoiThan)) {
            return false;
        }
        return id != null && id.equals(((NguoiThan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NguoiThan{" +
            "id=" + getId() +
            ", maNT='" + getMaNT() + "'" +
            ", tenNT='" + getTenNT() + "'" +
            ", sDT='" + getsDT() + "'" +
            "}";
    }
}
