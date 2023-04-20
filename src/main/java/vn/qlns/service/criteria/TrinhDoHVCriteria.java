package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.TrinhDoHV} entity. This class is used
 * in {@link vn.qlns.web.rest.TrinhDoHVResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trinh-do-hvs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrinhDoHVCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maTDHV;

    private StringFilter tenTDHV;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public TrinhDoHVCriteria() {}

    public TrinhDoHVCriteria(TrinhDoHVCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maTDHV = other.maTDHV == null ? null : other.maTDHV.copy();
        this.tenTDHV = other.tenTDHV == null ? null : other.tenTDHV.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TrinhDoHVCriteria copy() {
        return new TrinhDoHVCriteria(this);
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

    public StringFilter getMaTDHV() {
        return maTDHV;
    }

    public StringFilter maTDHV() {
        if (maTDHV == null) {
            maTDHV = new StringFilter();
        }
        return maTDHV;
    }

    public void setMaTDHV(StringFilter maTDHV) {
        this.maTDHV = maTDHV;
    }

    public StringFilter getTenTDHV() {
        return tenTDHV;
    }

    public StringFilter tenTDHV() {
        if (tenTDHV == null) {
            tenTDHV = new StringFilter();
        }
        return tenTDHV;
    }

    public void setTenTDHV(StringFilter tenTDHV) {
        this.tenTDHV = tenTDHV;
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
        final TrinhDoHVCriteria that = (TrinhDoHVCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maTDHV, that.maTDHV) &&
            Objects.equals(tenTDHV, that.tenTDHV) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maTDHV, tenTDHV, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrinhDoHVCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maTDHV != null ? "maTDHV=" + maTDHV + ", " : "") +
            (tenTDHV != null ? "tenTDHV=" + tenTDHV + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
