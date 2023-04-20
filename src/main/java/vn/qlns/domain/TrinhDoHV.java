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
 * A TrinhDoHV.
 */
@Entity
@Table(name = "trinh_do_hv")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrinhDoHV implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "ma_tdhv", length = 20, nullable = false)
    private String maTDHV;

    @Size(max = 50)
    @Column(name = "ten_tdhv", length = 50)
    private String tenTDHV;

    @OneToMany(mappedBy = "trinhDoHV")
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

    public TrinhDoHV id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaTDHV() {
        return this.maTDHV;
    }

    public TrinhDoHV maTDHV(String maTDHV) {
        this.setMaTDHV(maTDHV);
        return this;
    }

    public void setMaTDHV(String maTDHV) {
        this.maTDHV = maTDHV;
    }

    public String getTenTDHV() {
        return this.tenTDHV;
    }

    public TrinhDoHV tenTDHV(String tenTDHV) {
        this.setTenTDHV(tenTDHV);
        return this;
    }

    public void setTenTDHV(String tenTDHV) {
        this.tenTDHV = tenTDHV;
    }

    public Set<NhanVien> getNhanViens() {
        return this.nhanViens;
    }

    public void setNhanViens(Set<NhanVien> nhanViens) {
        if (this.nhanViens != null) {
            this.nhanViens.forEach(i -> i.setTrinhDoHV(null));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.setTrinhDoHV(this));
        }
        this.nhanViens = nhanViens;
    }

    public TrinhDoHV nhanViens(Set<NhanVien> nhanViens) {
        this.setNhanViens(nhanViens);
        return this;
    }

    public TrinhDoHV addNhanVien(NhanVien nhanVien) {
        this.nhanViens.add(nhanVien);
        nhanVien.setTrinhDoHV(this);
        return this;
    }

    public TrinhDoHV removeNhanVien(NhanVien nhanVien) {
        this.nhanViens.remove(nhanVien);
        nhanVien.setTrinhDoHV(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrinhDoHV)) {
            return false;
        }
        return id != null && id.equals(((TrinhDoHV) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrinhDoHV{" +
            "id=" + getId() +
            ", maTDHV='" + getMaTDHV() + "'" +
            ", tenTDHV='" + getTenTDHV() + "'" +
            "}";
    }
}
