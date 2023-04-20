import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INguoiThan, NewNguoiThan } from '../nguoi-than.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INguoiThan for edit and NewNguoiThanFormGroupInput for create.
 */
type NguoiThanFormGroupInput = INguoiThan | PartialWithRequiredKeyOf<NewNguoiThan>;

type NguoiThanFormDefaults = Pick<NewNguoiThan, 'id'>;

type NguoiThanFormGroupContent = {
  id: FormControl<INguoiThan['id'] | NewNguoiThan['id']>;
  maNT: FormControl<INguoiThan['maNT']>;
  tenNT: FormControl<INguoiThan['tenNT']>;
  sDT: FormControl<INguoiThan['sDT']>;
};

export type NguoiThanFormGroup = FormGroup<NguoiThanFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NguoiThanFormService {
  createNguoiThanFormGroup(nguoiThan: NguoiThanFormGroupInput = { id: null }): NguoiThanFormGroup {
    const nguoiThanRawValue = {
      ...this.getFormDefaults(),
      ...nguoiThan,
    };
    return new FormGroup<NguoiThanFormGroupContent>({
      id: new FormControl(
        { value: nguoiThanRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maNT: new FormControl(nguoiThanRawValue.maNT, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      tenNT: new FormControl(nguoiThanRawValue.tenNT, {
        validators: [Validators.maxLength(50)],
      }),
      sDT: new FormControl(nguoiThanRawValue.sDT, {
        validators: [Validators.maxLength(20)],
      }),
    });
  }

  getNguoiThan(form: NguoiThanFormGroup): INguoiThan | NewNguoiThan {
    return form.getRawValue() as INguoiThan | NewNguoiThan;
  }

  resetForm(form: NguoiThanFormGroup, nguoiThan: NguoiThanFormGroupInput): void {
    const nguoiThanRawValue = { ...this.getFormDefaults(), ...nguoiThan };
    form.reset(
      {
        ...nguoiThanRawValue,
        id: { value: nguoiThanRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NguoiThanFormDefaults {
    return {
      id: null,
    };
  }
}
