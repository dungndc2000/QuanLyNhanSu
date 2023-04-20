import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chuc-vu.test-samples';

import { ChucVuFormService } from './chuc-vu-form.service';

describe('ChucVu Form Service', () => {
  let service: ChucVuFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChucVuFormService);
  });

  describe('Service methods', () => {
    describe('createChucVuFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChucVuFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maCV: expect.any(Object),
            tenChucVu: expect.any(Object),
            phuCap: expect.any(Object),
            ghiChu: expect.any(Object),
          })
        );
      });

      it('passing IChucVu should create a new form with FormGroup', () => {
        const formGroup = service.createChucVuFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maCV: expect.any(Object),
            tenChucVu: expect.any(Object),
            phuCap: expect.any(Object),
            ghiChu: expect.any(Object),
          })
        );
      });
    });

    describe('getChucVu', () => {
      it('should return NewChucVu for default ChucVu initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createChucVuFormGroup(sampleWithNewData);

        const chucVu = service.getChucVu(formGroup) as any;

        expect(chucVu).toMatchObject(sampleWithNewData);
      });

      it('should return NewChucVu for empty ChucVu initial value', () => {
        const formGroup = service.createChucVuFormGroup();

        const chucVu = service.getChucVu(formGroup) as any;

        expect(chucVu).toMatchObject({});
      });

      it('should return IChucVu', () => {
        const formGroup = service.createChucVuFormGroup(sampleWithRequiredData);

        const chucVu = service.getChucVu(formGroup) as any;

        expect(chucVu).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChucVu should not enable id FormControl', () => {
        const formGroup = service.createChucVuFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChucVu should disable id FormControl', () => {
        const formGroup = service.createChucVuFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
