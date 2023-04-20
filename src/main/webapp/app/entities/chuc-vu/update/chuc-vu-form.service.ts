import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChucVu, NewChucVu } from '../chuc-vu.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChucVu for edit and NewChucVuFormGroupInput for create.
 */
type ChucVuFormGroupInput = IChucVu | PartialWithRequiredKeyOf<NewChucVu>;

type ChucVuFormDefaults = Pick<NewChucVu, 'id'>;

type ChucVuFormGroupContent = {
  id: FormControl<IChucVu['id'] | NewChucVu['id']>;
  maCV: FormControl<IChucVu['maCV']>;
  tenChucVu: FormControl<IChucVu['tenChucVu']>;
  phuCap: FormControl<IChucVu['phuCap']>;
  ghiChu: FormControl<IChucVu['ghiChu']>;
};

export type ChucVuFormGroup = FormGroup<ChucVuFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChucVuFormService {
  createChucVuFormGroup(chucVu: ChucVuFormGroupInput = { id: null }): ChucVuFormGroup {
    const chucVuRawValue = {
      ...this.getFormDefaults(),
      ...chucVu,
    };
    return new FormGroup<ChucVuFormGroupContent>({
      id: new FormControl(
        { value: chucVuRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maCV: new FormControl(chucVuRawValue.maCV, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      tenChucVu: new FormControl(chucVuRawValue.tenChucVu, {
        validators: [Validators.maxLength(50)],
      }),
      phuCap: new FormControl(chucVuRawValue.phuCap, {
        validators: [Validators.maxLength(10)],
      }),
      ghiChu: new FormControl(chucVuRawValue.ghiChu, {
        validators: [Validators.maxLength(100)],
      }),
    });
  }

  getChucVu(form: ChucVuFormGroup): IChucVu | NewChucVu {
    return form.getRawValue() as IChucVu | NewChucVu;
  }

  resetForm(form: ChucVuFormGroup, chucVu: ChucVuFormGroupInput): void {
    const chucVuRawValue = { ...this.getFormDefaults(), ...chucVu };
    form.reset(
      {
        ...chucVuRawValue,
        id: { value: chucVuRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ChucVuFormDefaults {
    return {
      id: null,
    };
  }
}
