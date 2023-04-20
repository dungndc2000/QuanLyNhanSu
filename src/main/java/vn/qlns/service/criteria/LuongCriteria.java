package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.Luong} entity. This class is used
 * in {@link vn.qlns.web.rest.LuongResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /luongs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LuongCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maLuong;

    private StringFilter heSoLuong;

    private StringFilter luongCb;

    private StringFilter heSoPhuCap;

    private StringFilter khoanCongThem;

    private StringFilter khoanTru;

    private LongFilter nhanVienId;

    private Boolean distinct;

    public LuongCriteria() {}

    public LuongCriteria(LuongCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maLuong = other.maLuong == null ? null : other.maLuong.copy();
        this.heSoLuong = other.heSoLuong == null ? null : other.heSoLuong.copy();
        this.luongCb = other.luongCb == null ? null : other.luongCb.copy();
        this.heSoPhuCap = other.heSoPhuCap == null ? null : other.heSoPhuCap.copy();
        this.khoanCongThem = other.khoanCongThem == null ? null : other.khoanCongThem.copy();
        this.khoanTru = other.khoanTru == null ? null : other.khoanTru.copy();
        this.nhanVienId = other.nhanVienId == null ? null : other.nhanVienId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LuongCriteria copy() {
        return new LuongCriteria(this);
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

    public StringFilter getMaLuong() {
        return maLuong;
    }

    public StringFilter maLuong() {
        if (maLuong == null) {
            maLuong = new StringFilter();
        }
        return maLuong;
    }

    public void setMaLuong(StringFilter maLuong) {
        this.maLuong = maLuong;
    }

    public StringFilter getHeSoLuong() {
        return heSoLuong;
    }

    public StringFilter heSoLuong() {
        if (heSoLuong == null) {
            heSoLuong = new StringFilter();
        }
        return heSoLuong;
    }

    public void setHeSoLuong(StringFilter heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public StringFilter getLuongCb() {
        return luongCb;
    }

    public StringFilter luongCb() {
        if (luongCb == null) {
            luongCb = new StringFilter();
        }
        return luongCb;
    }

    public void setLuongCb(StringFilter luongCb) {
        this.luongCb = luongCb;
    }

    public StringFilter getHeSoPhuCap() {
        return heSoPhuCap;
    }

    public StringFilter heSoPhuCap() {
        if (heSoPhuCap == null) {
            heSoPhuCap = new StringFilter();
        }
        return heSoPhuCap;
    }

    public void setHeSoPhuCap(StringFilter heSoPhuCap) {
        this.heSoPhuCap = heSoPhuCap;
    }

    public StringFilter getKhoanCongThem() {
        return khoanCongThem;
    }

    public StringFilter khoanCongThem() {
        if (khoanCongThem == null) {
            khoanCongThem = new StringFilter();
        }
        return khoanCongThem;
    }

    public void setKhoanCongThem(StringFilter khoanCongThem) {
        this.khoanCongThem = khoanCongThem;
    }

    public StringFilter getKhoanTru() {
        return khoanTru;
    }

    public StringFilter khoanTru() {
        if (khoanTru == null) {
            khoanTru = new StringFilter();
        }
        return khoanTru;
    }

    public void setKhoanTru(StringFilter khoanTru) {
        this.khoanTru = khoanTru;
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
        final LuongCriteria that = (LuongCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maLuong, that.maLuong) &&
            Objects.equals(heSoLuong, that.heSoLuong) &&
            Objects.equals(luongCb, that.luongCb) &&
            Objects.equals(heSoPhuCap, that.heSoPhuCap) &&
            Objects.equals(khoanCongThem, that.khoanCongThem) &&
            Objects.equals(khoanTru, that.khoanTru) &&
            Objects.equals(nhanVienId, that.nhanVienId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maLuong, heSoLuong, luongCb, heSoPhuCap, khoanCongThem, khoanTru, nhanVienId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LuongCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maLuong != null ? "maLuong=" + maLuong + ", " : "") +
            (heSoLuong != null ? "heSoLuong=" + heSoLuong + ", " : "") +
            (luongCb != null ? "luongCb=" + luongCb + ", " : "") +
            (heSoPhuCap != null ? "heSoPhuCap=" + heSoPhuCap + ", " : "") +
            (khoanCongThem != null ? "khoanCongThem=" + khoanCongThem + ", " : "") +
            (khoanTru != null ? "khoanTru=" + khoanTru + ", " : "") +
            (nhanVienId != null ? "nhanVienId=" + nhanVienId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
