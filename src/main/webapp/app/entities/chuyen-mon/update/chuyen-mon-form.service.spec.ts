import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chuyen-mon.test-samples';

import { ChuyenMonFormService } from './chuyen-mon-form.service';

describe('ChuyenMon Form Service', () => {
  let service: ChuyenMonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChuyenMonFormService);
  });

  describe('Service methods', () => {
    describe('createChuyenMonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChuyenMonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maChuyenMon: expect.any(Object),
            tenChuyenMon: expect.any(Object),
          })
        );
      });

      it('passing IChuyenMon should create a new form with FormGroup', () => {
        const formGroup = service.createChuyenMonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maChuyenMon: expect.any(Object),
            tenChuyenMon: expect.any(Object),
          })
        );
      });
    });

    describe('getChuyenMon', () => {
      it('should return NewChuyenMon for default ChuyenMon initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createChuyenMonFormGroup(sampleWithNewData);

        const chuyenMon = service.getChuyenMon(formGroup) as any;

        expect(chuyenMon).toMatchObject(sampleWithNewData);
      });

      it('should return NewChuyenMon for empty ChuyenMon initial value', () => {
        const formGroup = service.createChuyenMonFormGroup();

        const chuyenMon = service.getChuyenMon(formGroup) as any;

        expect(chuyenMon).toMatchObject({});
      });

      it('should return IChuyenMon', () => {
        const formGroup = service.createChuyenMonFormGroup(sampleWithRequiredData);

        const chuyenMon = service.getChuyenMon(formGroup) as any;

        expect(chuyenMon).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChuyenMon should not enable id FormControl', () => {
        const formGroup = service.createChuyenMonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChuyenMon should disable id FormControl', () => {
        const formGroup = service.createChuyenMonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
