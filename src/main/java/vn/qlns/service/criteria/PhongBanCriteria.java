package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.PhongBan} entity. This class is used
 * in {@link vn.qlns.web.rest.PhongBanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /phong-bans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhongBanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maPB;

    private StringFilter tenPB;

    private StringFilter sDT;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public PhongBanCriteria() {}

    public PhongBanCriteria(PhongBanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maPB = other.maPB == null ? null : other.maPB.copy();
        this.tenPB = other.tenPB == null ? null : other.tenPB.copy();
        this.sDT = other.sDT == null ? null : other.sDT.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PhongBanCriteria copy() {
        return new PhongBanCriteria(this);
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

    public StringFilter getMaPB() {
        return maPB;
    }

    public StringFilter maPB() {
        if (maPB == null) {
            maPB = new StringFilter();
        }
        return maPB;
    }

    public void setMaPB(StringFilter maPB) {
        this.maPB = maPB;
    }

    public StringFilter getTenPB() {
        return tenPB;
    }

    public StringFilter tenPB() {
        if (tenPB == null) {
            tenPB = new StringFilter();
        }
        return tenPB;
    }

    public void setTenPB(StringFilter tenPB) {
        this.tenPB = tenPB;
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
        final PhongBanCriteria that = (PhongBanCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maPB, that.maPB) &&
            Objects.equals(tenPB, that.tenPB) &&
            Objects.equals(sDT, that.sDT) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maPB, tenPB, sDT, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhongBanCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maPB != null ? "maPB=" + maPB + ", " : "") +
            (tenPB != null ? "tenPB=" + tenPB + ", " : "") +
            (sDT != null ? "sDT=" + sDT + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
