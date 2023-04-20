package vn.qlns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A KhenThuong.
 */
@Entity
@Table(name = "khen_thuong")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KhenThuong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "soqd", length = 10, nullable = false)
    private String soqd;

    @NotNull
    @Column(name = "ngay_qd", nullable = false)
    private ZonedDateTime ngayQd;

    @Size(max = 20)
    @Column(name = "ten", length = 20)
    private String ten;

    @Size(max = 20)
    @Column(name = "loai", length = 20)
    private String loai;

    @Size(max = 20)
    @Column(name = "hinh_thuc", length = 20)
    private String hinhThuc;

    @Size(max = 20)
    @Column(name = "so_tien", length = 20)
    private String soTien;

    @Size(max = 20)
    @Column(name = "noi_dung", length = 20)
    private String noiDung;

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

    public KhenThuong id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoqd() {
        return this.soqd;
    }

    public KhenThuong soqd(String soqd) {
        this.setSoqd(soqd);
        return this;
    }

    public void setSoqd(String soqd) {
        this.soqd = soqd;
    }

    public ZonedDateTime getNgayQd() {
        return this.ngayQd;
    }

    public KhenThuong ngayQd(ZonedDateTime ngayQd) {
        this.setNgayQd(ngayQd);
        return this;
    }

    public void setNgayQd(ZonedDateTime ngayQd) {
        this.ngayQd = ngayQd;
    }

    public String getTen() {
        return this.ten;
    }

    public KhenThuong ten(String ten) {
        this.setTen(ten);
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return this.loai;
    }

    public KhenThuong loai(String loai) {
        this.setLoai(loai);
        return this;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getHinhThuc() {
        return this.hinhThuc;
    }

    public KhenThuong hinhThuc(String hinhThuc) {
        this.setHinhThuc(hinhThuc);
        return this;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public String getSoTien() {
        return this.soTien;
    }

    public KhenThuong soTien(String soTien) {
        this.setSoTien(soTien);
        return this;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getNoiDung() {
        return this.noiDung;
    }

    public KhenThuong noiDung(String noiDung) {
        this.setNoiDung(noiDung);
        return this;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public NhanVien getNhanVien() {
        return this.nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhenThuong nhanVien(NhanVien nhanVien) {
        this.setNhanVien(nhanVien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KhenThuong)) {
            return false;
        }
        return id != null && id.equals(((KhenThuong) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhenThuong{" +
            "id=" + getId() +
            ", soqd='" + getSoqd() + "'" +
            ", ngayQd='" + getNgayQd() + "'" +
            ", ten='" + getTen() + "'" +
            ", loai='" + getLoai() + "'" +
            ", hinhThuc='" + getHinhThuc() + "'" +
            ", soTien='" + getSoTien() + "'" +
            ", noiDung='" + getNoiDung() + "'" +
            "}";
    }
}
