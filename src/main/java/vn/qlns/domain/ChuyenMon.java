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
 * A ChuyenMon.
 */
@Entity
@Table(name = "chuyen_mon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChuyenMon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ma_chuyen_mon", length = 10, nullable = false)
    private String maChuyenMon;

    @Size(max = 50)
    @Column(name = "ten_chuyen_mon", length = 50)
    private String tenChuyenMon;

    @OneToMany(mappedBy = "chuyenMon")
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

    public ChuyenMon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaChuyenMon() {
        return this.maChuyenMon;
    }

    public ChuyenMon maChuyenMon(String maChuyenMon) {
        this.setMaChuyenMon(maChuyenMon);
        return this;
    }

    public void setMaChuyenMon(String maChuyenMon) {
        this.maChuyenMon = maChuyenMon;
    }

    public String getTenChuyenMon() {
        return this.tenChuyenMon;
    }

    public ChuyenMon tenChuyenMon(String tenChuyenMon) {
        this.setTenChuyenMon(tenChuyenMon);
        return this;
    }

    public void setTenChuyenMon(String tenChuyenMon) {
        this.tenChuyenMon = tenChuyenMon;
    }

    public Set<NhanVien> getNhanViens() {
        return this.nhanViens;
    }

    public void setNhanViens(Set<NhanVien> nhanViens) {
        if (this.nhanViens != null) {
            this.nhanViens.forEach(i -> i.setChuyenMon(null));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.setChuyenMon(this));
        }
        this.nhanViens = nhanViens;
    }

    public ChuyenMon nhanViens(Set<NhanVien> nhanViens) {
        this.setNhanViens(nhanViens);
        return this;
    }

    public ChuyenMon addNhanVien(NhanVien nhanVien) {
        this.nhanViens.add(nhanVien);
        nhanVien.setChuyenMon(this);
        return this;
    }

    public ChuyenMon removeNhanVien(NhanVien nhanVien) {
        this.nhanViens.remove(nhanVien);
        nhanVien.setChuyenMon(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChuyenMon)) {
            return false;
        }
        return id != null && id.equals(((ChuyenMon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChuyenMon{" +
            "id=" + getId() +
            ", maChuyenMon='" + getMaChuyenMon() + "'" +
            ", tenChuyenMon='" + getTenChuyenMon() + "'" +
            "}";
    }
}
