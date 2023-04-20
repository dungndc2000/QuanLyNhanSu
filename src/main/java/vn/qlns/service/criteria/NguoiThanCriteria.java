package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.NguoiThan} entity. This class is used
 * in {@link vn.qlns.web.rest.NguoiThanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nguoi-thans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NguoiThanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maNT;

    private StringFilter tenNT;

    private StringFilter sDT;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public NguoiThanCriteria() {}

    public NguoiThanCriteria(NguoiThanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maNT = other.maNT == null ? null : other.maNT.copy();
        this.tenNT = other.tenNT == null ? null : other.tenNT.copy();
        this.sDT = other.sDT == null ? null : other.sDT.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NguoiThanCriteria copy() {
        return new NguoiThanCriteria(this);
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

    public StringFilter getMaNT() {
        return maNT;
    }

    public StringFilter maNT() {
        if (maNT == null) {
            maNT = new StringFilter();
        }
        return maNT;
    }

    public void setMaNT(StringFilter maNT) {
        this.maNT = maNT;
    }

    public StringFilter getTenNT() {
        return tenNT;
    }

    public StringFilter tenNT() {
        if (tenNT == null) {
            tenNT = new StringFilter();
        }
        return tenNT;
    }

    public void setTenNT(StringFilter tenNT) {
        this.tenNT = tenNT;
    }

    public StringFilter getsDT() {
        return sDT;
    }

    public StringFilter sDT() {
        if (sDT == null) {
            sDT = new StringFilter();
        }
        return sDT;
    }

    public void setsDT(StringFilter sDT) {
        this.sDT = sDT;
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
        final NguoiThanCriteria that = (NguoiThanCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maNT, that.maNT) &&
            Objects.equals(tenNT, that.tenNT) &&
            Objects.equals(sDT, that.sDT) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maNT, tenNT, sDT, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NguoiThanCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maNT != null ? "maNT=" + maNT + ", " : "") +
            (tenNT != null ? "tenNT=" + tenNT + ", " : "") +
            (sDT != null ? "sDT=" + sDT + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
