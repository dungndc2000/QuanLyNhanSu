import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nhan-vien.test-samples';

import { NhanVienFormService } from './nhan-vien-form.service';

describe('NhanVien Form Service', () => {
  let service: NhanVienFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NhanVienFormService);
  });

  describe('Service methods', () => {
    describe('createNhanVienFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNhanVienFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maNV: expect.any(Object),
            tenNV: expect.any(Object),
            ngaySinh: expect.any(Object),
            gioiTinh: expect.any(Object),
            diaChi: expect.any(Object),
            soCMND: expect.any(Object),
            sDT: expect.any(Object),
            email: expect.any(Object),
            heSoLuong: expect.any(Object),
            nguoiThan: expect.any(Object),
            chucVu: expect.any(Object),
            phongBan: expect.any(Object),
            chuyenMon: expect.any(Object),
            trinhDoHV: expect.any(Object),
          })
        );
      });

      it('passing INhanVien should create a new form with FormGroup', () => {
        const formGroup = service.createNhanVienFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maNV: expect.any(Object),
            tenNV: expect.any(Object),
            ngaySinh: expect.any(Object),
            gioiTinh: expect.any(Object),
            diaChi: expect.any(Object),
            soCMND: expect.any(Object),
            sDT: expect.any(Object),
            email: expect.any(Object),
            heSoLuong: expect.any(Object),
            nguoiThan: expect.any(Object),
            chucVu: expect.any(Object),
            phongBan: expect.any(Object),
            chuyenMon: expect.any(Object),
            trinhDoHV: expect.any(Object),
          })
        );
      });
    });

    describe('getNhanVien', () => {
      it('should return NewNhanVien for default NhanVien initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNhanVienFormGroup(sampleWithNewData);

        const nhanVien = service.getNhanVien(formGroup) as any;

        expect(nhanVien).toMatchObject(sampleWithNewData);
      });

      it('should return NewNhanVien for empty NhanVien initial value', () => {
        const formGroup = service.createNhanVienFormGroup();

        const nhanVien = service.getNhanVien(formGroup) as any;

        expect(nhanVien).toMatchObject({});
      });

      it('should return INhanVien', () => {
        const formGroup = service.createNhanVienFormGroup(sampleWithRequiredData);

        const nhanVien = service.getNhanVien(formGroup) as any;

        expect(nhanVien).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INhanVien should not enable id FormControl', () => {
        const formGroup = service.createNhanVienFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNhanVien should disable id FormControl', () => {
        const formGroup = service.createNhanVienFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
