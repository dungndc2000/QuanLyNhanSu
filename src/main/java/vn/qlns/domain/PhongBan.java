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
 * A PhongBan.
 */
@Entity
@Table(name = "phong_ban")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhongBan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "ma_pb", length = 10, nullable = false)
    private String maPB;

    @Size(max = 50)
    @Column(name = "ten_pb", length = 50)
    private String tenPB;

    @Size(max = 20)
    @Column(name = "s_dt", length = 20)
    private String sDT;

    @OneToMany(mappedBy = "phongBan")
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

    public PhongBan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaPB() {
        return this.maPB;
    }

    public PhongBan maPB(String maPB) {
        this.setMaPB(maPB);
        return this;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return this.tenPB;
    }

    public PhongBan tenPB(String tenPB) {
        this.setTenPB(tenPB);
        return this;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public String getsDT() {
        return this.sDT;
    }

    public PhongBan sDT(String sDT) {
        this.setsDT(sDT);
        return this;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public Set<NhanVien> getNhanViens() {
        return this.nhanViens;
    }

    public void setNhanViens(Set<NhanVien> nhanViens) {
        if (this.nhanViens != null) {
            this.nhanViens.forEach(i -> i.setPhongBan(null));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.setPhongBan(this));
        }
        this.nhanViens = nhanViens;
    }

    public PhongBan nhanViens(Set<NhanVien> nhanViens) {
        this.setNhanViens(nhanViens);
        return this;
    }

    public PhongBan addNhanVien(NhanVien nhanVien) {
        this.nhanViens.add(nhanVien);
        nhanVien.setPhongBan(this);
        return this;
    }

    public PhongBan removeNhanVien(NhanVien nhanVien) {
        this.nhanViens.remove(nhanVien);
        nhanVien.setPhongBan(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhongBan)) {
            return false;
        }
        return id != null && id.equals(((PhongBan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhongBan{" +
            "id=" + getId() +
            ", maPB='" + getMaPB() + "'" +
            ", tenPB='" + getTenPB() + "'" +
            ", sDT='" + getsDT() + "'" +
            "}";
    }
}
