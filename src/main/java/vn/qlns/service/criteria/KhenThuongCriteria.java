package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.KhenThuong} entity. This class is used
 * in {@link vn.qlns.web.rest.KhenThuongResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /khen-thuongs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KhenThuongCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter soqd;

    private ZonedDateTimeFilter ngayQd;

    private StringFilter ten;

    private StringFilter loai;

    private StringFilter hinhThuc;

    private StringFilter soTien;

    private StringFilter noiDung;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public KhenThuongCriteria() {}

    public KhenThuongCriteria(KhenThuongCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.soqd = other.soqd == null ? null : other.soqd.copy();
        this.ngayQd = other.ngayQd == null ? null : other.ngayQd.copy();
        this.ten = other.ten == null ? null : other.ten.copy();
        this.loai = other.loai == null ? null : other.loai.copy();
        this.hinhThuc = other.hinhThuc == null ? null : other.hinhThuc.copy();
        this.soTien = other.soTien == null ? null : other.soTien.copy();
        this.noiDung = other.noiDung == null ? null : other.noiDung.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KhenThuongCriteria copy() {
        return new KhenThuongCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSoqd() {
        return soqd;
    }

    public StringFilter soqd() {
        if (soqd == null) {
            soqd = new StringFilter();
        }
        return soqd;
    }

    public void setSoqd(StringFilter soqd) {
        this.soqd = soqd;
    }

    public ZonedDateTimeFilter getNgayQd() {
        return ngayQd;
    }

    public ZonedDateTimeFilter ngayQd() {
        if (ngayQd == null) {
            ngayQd = new ZonedDateTimeFilter();
        }
        return ngayQd;
    }

    public void setNgayQd(ZonedDateTimeFilter ngayQd) {
        this.ngayQd = ngayQd;
    }

    public StringFilter getTen() {
        return ten;
    }

    public StringFilter ten() {
        if (ten == null) {
            ten = new StringFilter();
        }
        return ten;
    }

    public void setTen(StringFilter ten) {
        this.ten = ten;
    }

    public StringFilter getLoai() {
        return loai;
    }

    public StringFilter loai() {
        if (loai == null) {
            loai = new StringFilter();
        }
        return loai;
    }

    public void setLoai(StringFilter loai) {
        this.loai = loai;
    }

    public StringFilter getHinhThuc() {
        return hinhThuc;
    }

    public StringFilter hinhThuc() {
        if (hinhThuc == null) {
            hinhThuc = new StringFilter();
        }
        return hinhThuc;
    }

    public void setHinhThuc(StringFilter hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public StringFilter getSoTien() {
        return soTien;
    }

    public StringFilter soTien() {
        if (soTien == null) {
            soTien = new StringFilter();
        }
        return soTien;
    }

    public void setSoTien(StringFilter soTien) {
        this.soTien = soTien;
    }

    public StringFilter getNoiDung() {
        return noiDung;
    }

    public StringFilter noiDung() {
        if (noiDung == null) {
            noiDung = new StringFilter();
        }
        return noiDung;
    }

    public void setNoiDung(StringFilter noiDung) {
        this.noiDung = noiDung;
    }

    public LongFilter getNhanVienId() {
        return nhanVienId;
    }

    public LongFilter nhanVienId() {
        if (nhanVienId == null) {
            nhanVienId = new LongFilter();
        }
        return nhanVienId;
    }

    public void setNhanVienId(LongFilter nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KhenThuongCriteria that = (KhenThuongCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(soqd, that.soqd) &&
            Objects.equals(ngayQd, that.ngayQd) &&
            Objects.equals(ten, that.ten) &&
            Objects.equals(loai, that.loai) &&
            Objects.equals(hinhThuc, that.hinhThuc) &&
            Objects.equals(soTien, that.soTien) &&
            Objects.equals(noiDung, that.noiDung) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, soqd, ngayQd, ten, loai, hinhThuc, soTien, noiDung, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhenThuongCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (soqd != null ? "soqd=" + soqd + ", " : "") +
            (ngayQd != null ? "ngayQd=" + ngayQd + ", " : "") +
            (ten != null ? "ten=" + ten + ", " : "") +
            (loai != null ? "loai=" + loai + ", " : "") +
            (hinhThuc != null ? "hinhThuc=" + hinhThuc + ", " : "") +
            (soTien != null ? "soTien=" + soTien + ", " : "") +
            (noiDung != null ? "noiDung=" + noiDung + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
