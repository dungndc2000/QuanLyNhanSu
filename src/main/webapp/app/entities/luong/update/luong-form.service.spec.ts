import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../luong.test-samples';

import { LuongFormService } from './luong-form.service';

describe('Luong Form Service', () => {
  let service: LuongFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LuongFormService);
  });

  describe('Service methods', () => {
    describe('createLuongFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLuongFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maLuong: expect.any(Object),
            heSoLuong: expect.any(Object),
            luongCb: expect.any(Object),
            heSoPhuCap: expect.any(Object),
            khoanCongThem: expect.any(Object),
            khoanTru: expect.any(Object),
            nhanVien: expect.any(Object),
          })
        );
      });

      it('passing ILuong should create a new form with FormGroup', () => {
        const formGroup = service.createLuongFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maLuong: expect.any(Object),
            heSoLuong: expect.any(Object),
            luongCb: expect.any(Object),
            heSoPhuCap: expect.any(Object),
            khoanCongThem: expect.any(Object),
            khoanTru: expect.any(Object),
            nhanVien: expect.any(Object),
          })
        );
      });
    });

    describe('getLuong', () => {
      it('should return NewLuong for default Luong initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLuongFormGroup(sampleWithNewData);

        const luong = service.getLuong(formGroup) as any;

        expect(luong).toMatchObject(sampleWithNewData);
      });

      it('should return NewLuong for empty Luong initial value', () => {
        const formGroup = service.createLuongFormGroup();

        const luong = service.getLuong(formGroup) as any;

        expect(luong).toMatchObject({});
      });

      it('should return ILuong', () => {
        const formGroup = service.createLuongFormGroup(sampleWithRequiredData);

        const luong = service.getLuong(formGroup) as any;

        expect(luong).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILuong should not enable id FormControl', () => {
        const formGroup = service.createLuongFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLuong should disable id FormControl', () => {
        const formGroup = service.createLuongFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
