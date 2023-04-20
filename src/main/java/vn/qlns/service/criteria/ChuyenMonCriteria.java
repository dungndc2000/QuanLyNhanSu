package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.ChuyenMon} entity. This class is used
 * in {@link vn.qlns.web.rest.ChuyenMonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chuyen-mons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChuyenMonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maChuyenMon;

    private StringFilter tenChuyenMon;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public ChuyenMonCriteria() {}

    public ChuyenMonCriteria(ChuyenMonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maChuyenMon = other.maChuyenMon == null ? null : other.maChuyenMon.copy();
        this.tenChuyenMon = other.tenChuyenMon == null ? null : other.tenChuyenMon.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ChuyenMonCriteria copy() {
        return new ChuyenMonCriteria(this);
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

    public StringFilter getMaChuyenMon() {
        return maChuyenMon;
    }

    public StringFilter maChuyenMon() {
        if (maChuyenMon == null) {
            maChuyenMon = new StringFilter();
        }
        return maChuyenMon;
    }

    public void setMaChuyenMon(StringFilter maChuyenMon) {
        this.maChuyenMon = maChuyenMon;
    }

    public StringFilter getTenChuyenMon() {
        return tenChuyenMon;
    }

    public StringFilter tenChuyenMon() {
        if (tenChuyenMon == null) {
            tenChuyenMon = new StringFilter();
        }
        return tenChuyenMon;
    }

    public void setTenChuyenMon(StringFilter tenChuyenMon) {
        this.tenChuyenMon = tenChuyenMon;
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
        final ChuyenMonCriteria that = (ChuyenMonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maChuyenMon, that.maChuyenMon) &&
            Objects.equals(tenChuyenMon, that.tenChuyenMon) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maChuyenMon, tenChuyenMon, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChuyenMonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maChuyenMon != null ? "maChuyenMon=" + maChuyenMon + ", " : "") +
            (tenChuyenMon != null ? "tenChuyenMon=" + tenChuyenMon + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
