import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../khen-thuong.test-samples';

import { KhenThuongFormService } from './khen-thuong-form.service';

describe('KhenThuong Form Service', () => {
  let service: KhenThuongFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KhenThuongFormService);
  });

  describe('Service methods', () => {
    describe('createKhenThuongFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKhenThuongFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            soqd: expect.any(Object),
            ngayQd: expect.any(Object),
            ten: expect.any(Object),
            loai: expect.any(Object),
            hinhThuc: expect.any(Object),
            soTien: expect.any(Object),
            noiDung: expect.any(Object),
            nhanVien: expect.any(Object),
          })
        );
      });

      it('passing IKhenThuong should create a new form with FormGroup', () => {
        const formGroup = service.createKhenThuongFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            soqd: expect.any(Object),
            ngayQd: expect.any(Object),
            ten: expect.any(Object),
            loai: expect.any(Object),
            hinhThuc: expect.any(Object),
            soTien: expect.any(Object),
            noiDung: expect.any(Object),
            nhanVien: expect.any(Object),
          })
        );
      });
    });

    describe('getKhenThuong', () => {
      it('should return NewKhenThuong for default KhenThuong initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKhenThuongFormGroup(sampleWithNewData);

        const khenThuong = service.getKhenThuong(formGroup) as any;

        expect(khenThuong).toMatchObject(sampleWithNewData);
      });

      it('should return NewKhenThuong for empty KhenThuong initial value', () => {
        const formGroup = service.createKhenThuongFormGroup();

        const khenThuong = service.getKhenThuong(formGroup) as any;

        expect(khenThuong).toMatchObject({});
      });

      it('should return IKhenThuong', () => {
        const formGroup = service.createKhenThuongFormGroup(sampleWithRequiredData);

        const khenThuong = service.getKhenThuong(formGroup) as any;

        expect(khenThuong).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKhenThuong should not enable id FormControl', () => {
        const formGroup = service.createKhenThuongFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKhenThuong should disable id FormControl', () => {
        const formGroup = service.createKhenThuongFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
