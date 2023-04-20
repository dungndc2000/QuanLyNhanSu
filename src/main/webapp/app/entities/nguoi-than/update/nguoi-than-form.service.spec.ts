import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nguoi-than.test-samples';

import { NguoiThanFormService } from './nguoi-than-form.service';

describe('NguoiThan Form Service', () => {
  let service: NguoiThanFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NguoiThanFormService);
  });

  describe('Service methods', () => {
    describe('createNguoiThanFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNguoiThanFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maNT: expect.any(Object),
            tenNT: expect.any(Object),
            sDT: expect.any(Object),
          })
        );
      });

      it('passing INguoiThan should create a new form with FormGroup', () => {
        const formGroup = service.createNguoiThanFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            maNT: expect.any(Object),
            tenNT: expect.any(Object),
            sDT: expect.any(Object),
          })
        );
      });
    });

    describe('getNguoiThan', () => {
      it('should return NewNguoiThan for default NguoiThan initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNguoiThanFormGroup(sampleWithNewData);

        const nguoiThan = service.getNguoiThan(formGroup) as any;

        expect(nguoiThan).toMatchObject(sampleWithNewData);
      });

      it('should return NewNguoiThan for empty NguoiThan initial value', () => {
        const formGroup = service.createNguoiThanFormGroup();

        const nguoiThan = service.getNguoiThan(formGroup) as any;

        expect(nguoiThan).toMatchObject({});
      });

      it('should return INguoiThan', () => {
        const formGroup = service.createNguoiThanFormGroup(sampleWithRequiredData);

        const nguoiThan = service.getNguoiThan(formGroup) as any;

        expect(nguoiThan).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INguoiThan should not enable id FormControl', () => {
        const formGroup = service.createNguoiThanFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNguoiThan should disable id FormControl', () => {
        const formGroup = service.createNguoiThanFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
