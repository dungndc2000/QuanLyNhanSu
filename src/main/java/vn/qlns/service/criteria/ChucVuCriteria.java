package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.ChucVu} entity. This class is used
 * in {@link vn.qlns.web.rest.ChucVuResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chuc-vus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChucVuCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maCV;

    private StringFilter tenChucVu;

    private StringFilter phuCap;

    private StringFilter ghiChu;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public ChucVuCriteria() {}

    public ChucVuCriteria(ChucVuCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maCV = other.maCV == null ? null : other.maCV.copy();
        this.tenChucVu = other.tenChucVu == null ? null : other.tenChucVu.copy();
        this.phuCap = other.phuCap == null ? null : other.phuCap.copy();
        this.ghiChu = other.ghiChu == null ? null : other.ghiChu.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ChucVuCriteria copy() {
        return new ChucVuCriteria(this);
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

    public StringFilter getMaCV() {
        return maCV;
    }

    public StringFilter maCV() {
        if (maCV == null) {
            maCV = new StringFilter();
        }
        return maCV;
    }

    public void setMaCV(StringFilter maCV) {
        this.maCV = maCV;
    }

    public StringFilter getTenChucVu() {
        return tenChucVu;
    }

    public StringFilter tenChucVu() {
        if (tenChucVu == null) {
            tenChucVu = new StringFilter();
        }
        return tenChucVu;
    }

    public void setTenChucVu(StringFilter tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public StringFilter getPhuCap() {
        return phuCap;
    }

    public StringFilter phuCap() {
        if (phuCap == null) {
            phuCap = new StringFilter();
        }
        return phuCap;
    }

    public void setPhuCap(StringFilter phuCap) {
        this.phuCap = phuCap;
    }

    public StringFilter getGhiChu() {
        return ghiChu;
    }

    public StringFilter ghiChu() {
        if (ghiChu == null) {
            ghiChu = new StringFilter();
        }
        return ghiChu;
    }

    public void setGhiChu(StringFilter ghiChu) {
        this.ghiChu = ghiChu;
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
        final ChucVuCriteria that = (ChucVuCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maCV, that.maCV) &&
            Objects.equals(tenChucVu, that.tenChucVu) &&
            Objects.equals(phuCap, that.phuCap) &&
            Objects.equals(ghiChu, that.ghiChu) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maCV, tenChucVu, phuCap, ghiChu, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChucVuCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maCV != null ? "maCV=" + maCV + ", " : "") +
            (tenChucVu != null ? "tenChucVu=" + tenChucVu + ", " : "") +
            (phuCap != null ? "phuCap=" + phuCap + ", " : "") +
            (ghiChu != null ? "ghiChu=" + ghiChu + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
