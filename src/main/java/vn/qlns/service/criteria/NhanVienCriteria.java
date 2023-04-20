package vn.qlns.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link vn.qlns.domain.NhanVien} entity. This class is used
 * in {@link vn.qlns.web.rest.NhanVienResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nhan-viens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhanVienCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter maNV;

    private StringFilter tenNV;

    private ZonedDateTimeFilter ngaySinh;

    private StringFilter gioiTinh;

    private StringFilter diaChi;

    private StringFilter soCMND;

    private StringFilter sDT;

    private StringFilter email;

    private StringFilter heSoLuong;

    private LongFilter nguoiThanId;

    private LongFilter luongId;

    private LongFilter khenThuongId;

    private LongFilter chucVuId;

    private LongFilter phongBanId;

    private LongFilter chuyenMonId;

    private LongFilter trinhDoHVId;

    private Boolean distinct;

    public NhanVienCriteria() {}

    public NhanVienCriteria(NhanVienCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.maNV = other.maNV == null ? null : other.maNV.copy();
        this.tenNV = other.tenNV == null ? null : other.tenNV.copy();
        this.ngaySinh = other.ngaySinh == null ? null : other.ngaySinh.copy();
        this.gioiTinh = other.gioiTinh == null ? null : other.gioiTinh.copy();
        this.diaChi = other.diaChi == null ? null : other.diaChi.copy();
        this.soCMND = other.soCMND == null ? null : other.soCMND.copy();
        this.sDT = other.sDT == null ? null : other.sDT.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.heSoLuong = other.heSoLuong == null ? null : other.heSoLuong.copy();
        this.nguoiThanId = other.nguoiThanId == null ? null : other.nguoiThanId.copy();
        this.luongId = other.luongId == null ? null : other.luongId.copy();
        this.khenThuongId = other.khenThuongId == null ? null : other.khenThuongId.copy();
        this.chucVuId = other.chucVuId == null ? null : other.chucVuId.copy();
        this.phongBanId = other.phongBanId == null ? null : other.phongBanId.copy();
        this.chuyenMonId = other.chuyenMonId == null ? null : other.chuyenMonId.copy();
        this.trinhDoHVId = other.trinhDoHVId == null ? null : other.trinhDoHVId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NhanVienCriteria copy() {
        return new NhanVienCriteria(this);
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

    public StringFilter getMaNV() {
        return maNV;
    }

    public StringFilter maNV() {
        if (maNV == null) {
            maNV = new StringFilter();
        }
        return maNV;
    }

    public void setMaNV(StringFilter maNV) {
        this.maNV = maNV;
    }

    public StringFilter getTenNV() {
        return tenNV;
    }

    public StringFilter tenNV() {
        if (tenNV == null) {
            tenNV = new StringFilter();
        }
        return tenNV;
    }

    public void setTenNV(StringFilter tenNV) {
        this.tenNV = tenNV;
    }

    public ZonedDateTimeFilter getNgaySinh() {
        return ngaySinh;
    }

    public ZonedDateTimeFilter ngaySinh() {
        if (ngaySinh == null) {
            ngaySinh = new ZonedDateTimeFilter();
        }
        return ngaySinh;
    }

    public void setNgaySinh(ZonedDateTimeFilter ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public StringFilter getGioiTinh() {
        return gioiTinh;
    }

    public StringFilter gioiTinh() {
        if (gioiTinh == null) {
            gioiTinh = new StringFilter();
        }
        return gioiTinh;
    }

    public void setGioiTinh(StringFilter gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public StringFilter getDiaChi() {
        return diaChi;
    }

    public StringFilter diaChi() {
        if (diaChi == null) {
            diaChi = new StringFilter();
        }
        return diaChi;
    }

    public void setDiaChi(StringFilter diaChi) {
        this.diaChi = diaChi;
    }

    public StringFilter getSoCMND() {
        return soCMND;
    }

    public StringFilter soCMND() {
        if (soCMND == null) {
            soCMND = new StringFilter();
        }
        return soCMND;
    }

    public void setSoCMND(StringFilter soCMND) {
        this.soCMND = soCMND;
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

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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

    public LongFilter getNguoiThanId() {
        return nguoiThanId;
    }

    public LongFilter nguoiThanId() {
        if (nguoiThanId == null) {
            nguoiThanId = new LongFilter();
        }
        return nguoiThanId;
    }

    public void setNguoiThanId(LongFilter nguoiThanId) {
        this.nguoiThanId = nguoiThanId;
    }

    public LongFilter getLuongId() {
        return luongId;
    }

    public LongFilter luongId() {
        if (luongId == null) {
            luongId = new LongFilter();
        }
        return luongId;
    }

    public void setLuongId(LongFilter luongId) {
        this.luongId = luongId;
    }

    public LongFilter getKhenThuongId() {
        return khenThuongId;
    }

    public LongFilter khenThuongId() {
        if (khenThuongId == null) {
            khenThuongId = new LongFilter();
        }
        return khenThuongId;
    }

    public void setKhenThuongId(LongFilter khenThuongId) {
        this.khenThuongId = khenThuongId;
    }

    public LongFilter getChucVuId() {
        return chucVuId;
    }

    public LongFilter chucVuId() {
        if (chucVuId == null) {
            chucVuId = new LongFilter();
        }
        return chucVuId;
    }

    public void setChucVuId(LongFilter chucVuId) {
        this.chucVuId = chucVuId;
    }

    public LongFilter getPhongBanId() {
        return phongBanId;
    }

    public LongFilter phongBanId() {
        if (phongBanId == null) {
            phongBanId = new LongFilter();
        }
        return phongBanId;
    }

    public void setPhongBanId(LongFilter phongBanId) {
        this.phongBanId = phongBanId;
    }

    public LongFilter getChuyenMonId() {
        return chuyenMonId;
    }

    public LongFilter chuyenMonId() {
        if (chuyenMonId == null) {
            chuyenMonId = new LongFilter();
        }
        return chuyenMonId;
    }

    public void setChuyenMonId(LongFilter chuyenMonId) {
        this.chuyenMonId = chuyenMonId;
    }

    public LongFilter getTrinhDoHVId() {
        return trinhDoHVId;
    }

    public LongFilter trinhDoHVId() {
        if (trinhDoHVId == null) {
            trinhDoHVId = new LongFilter();
        }
        return trinhDoHVId;
    }

    public void setTrinhDoHVId(LongFilter trinhDoHVId) {
        this.trinhDoHVId = trinhDoHVId;
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
        final NhanVienCriteria that = (NhanVienCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(maNV, that.maNV) &&
            Objects.equals(tenNV, that.tenNV) &&
            Objects.equals(ngaySinh, that.ngaySinh) &&
            Objects.equals(gioiTinh, that.gioiTinh) &&
            Objects.equals(diaChi, that.diaChi) &&
            Objects.equals(soCMND, that.soCMND) &&
            Objects.equals(sDT, that.sDT) &&
            Objects.equals(email, that.email) &&
            Objects.equals(heSoLuong, that.heSoLuong) &&
            Objects.equals(nguoiThanId, that.nguoiThanId) &&
            Objects.equals(luongId, that.luongId) &&
            Objects.equals(khenThuongId, that.khenThuongId) &&
            Objects.equals(chucVuId, that.chucVuId) &&
            Objects.equals(phongBanId, that.phongBanId) &&
            Objects.equals(chuyenMonId, that.chuyenMonId) &&
            Objects.equals(trinhDoHVId, that.trinhDoHVId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            maNV,
            tenNV,
            ngaySinh,
            gioiTinh,
            diaChi,
            soCMND,
            sDT,
            email,
            heSoLuong,
            nguoiThanId,
            luongId,
            khenThuongId,
            chucVuId,
            phongBanId,
            chuyenMonId,
            trinhDoHVId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVienCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (maNV != null ? "maNV=" + maNV + ", " : "") +
            (tenNV != null ? "tenNV=" + tenNV + ", " : "") +
            (ngaySinh != null ? "ngaySinh=" + ngaySinh + ", " : "") +
            (gioiTinh != null ? "gioiTinh=" + gioiTinh + ", " : "") +
            (diaChi != null ? "diaChi=" + diaChi + ", " : "") +
            (soCMND != null ? "soCMND=" + soCMND + ", " : "") +
            (sDT != null ? "sDT=" + sDT + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (heSoLuong != null ? "heSoLuong=" + heSoLuong + ", " : "") +
            (nguoiThanId != null ? "nguoiThanId=" + nguoiThanId + ", " : "") +
            (luongId != null ? "luongId=" + luongId + ", " : "") +
            (khenThuongId != null ? "khenThuongId=" + khenThuongId + ", " : "") +
            (chucVuId != null ? "chucVuId=" + chucVuId + ", " : "") +
            (phongBanId != null ? "phongBanId=" + phongBanId + ", " : "") +
            (chuyenMonId != null ? "chuyenMonId=" + chuyenMonId + ", " : "") +
            (trinhDoHVId != null ? "trinhDoHVId=" + trinhDoHVId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
