import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../trinh-do-hv.test-samples';

import { TrinhDoHVFormService } from './trinh-do-hv-form.service';

describe('TrinhDoHV Form Service', () => {
  let service: TrinhDoHVFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrinhDoHVFormService);
  });

  describe('Service methods', () => {
    describe('createTrinhDoHVFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrinhDoHVFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maTDHV: expect.any(Object),
            tenTDHV: expect.any(Object),
          })
        );
      });

      it('passing ITrinhDoHV should create a new form with FormGroup', () => {
        const formGroup = service.createTrinhDoHVFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maTDHV: expect.any(Object),
            tenTDHV: expect.any(Object),
          })
        );
      });
    });

    describe('getTrinhDoHV', () => {
      it('should return NewTrinhDoHV for default TrinhDoHV initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrinhDoHVFormGroup(sampleWithNewData);

        const trinhDoHV = service.getTrinhDoHV(formGroup) as any;

        expect(trinhDoHV).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrinhDoHV for empty TrinhDoHV initial value', () => {
        const formGroup = service.createTrinhDoHVFormGroup();

        const trinhDoHV = service.getTrinhDoHV(formGroup) as any;

        expect(trinhDoHV).toMatchObject({});
      });

      it('should return ITrinhDoHV', () => {
        const formGroup = service.createTrinhDoHVFormGroup(sampleWithRequiredData);

        const trinhDoHV = service.getTrinhDoHV(formGroup) as any;

        expect(trinhDoHV).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrinhDoHV should not enable id FormControl', () => {
        const formGroup = service.createTrinhDoHVFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrinhDoHV should disable id FormControl', () => {
        const formGroup = service.createTrinhDoHVFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
