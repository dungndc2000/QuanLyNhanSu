import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../phong-ban.test-samples';

import { PhongBanFormService } from './phong-ban-form.service';

describe('PhongBan Form Service', () => {
  let service: PhongBanFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhongBanFormService);
  });

  describe('Service methods', () => {
    describe('createPhongBanFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPhongBanFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maPB: expect.any(Object),
            tenPB: expect.any(Object),
            sDT: expect.any(Object),
          })
        );
      });

      it('passing IPhongBan should create a new form with FormGroup', () => {
        const formGroup = service.createPhongBanFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maPB: expect.any(Object),
            tenPB: expect.any(Object),
            sDT: expect.any(Object),
          })
        );
      });
    });

    describe('getPhongBan', () => {
      it('should return NewPhongBan for default PhongBan initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPhongBanFormGroup(sampleWithNewData);

        const phongBan = service.getPhongBan(formGroup) as any;

        expect(phongBan).toMatchObject(sampleWithNewData);
      });

      it('should return NewPhongBan for empty PhongBan initial value', () => {
        const formGroup = service.createPhongBanFormGroup();

        const phongBan = service.getPhongBan(formGroup) as any;

        expect(phongBan).toMatchObject({});
      });

      it('should return IPhongBan', () => {
        const formGroup = service.createPhongBanFormGroup(sampleWithRequiredData);

        const phongBan = service.getPhongBan(formGroup) as any;

        expect(phongBan).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPhongBan should not enable id FormControl', () => {
        const formGroup = service.createPhongBanFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPhongBan should disable id FormControl', () => {
        const formGroup = service.createPhongBanFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
