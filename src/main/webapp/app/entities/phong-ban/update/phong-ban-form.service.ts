import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPhongBan, NewPhongBan } from '../phong-ban.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPhongBan for edit and NewPhongBanFormGroupInput for create.
 */
type PhongBanFormGroupInput = IPhongBan | PartialWithRequiredKeyOf<NewPhongBan>;

type PhongBanFormDefaults = Pick<NewPhongBan, 'id'>;

type PhongBanFormGroupContent = {
  id: FormControl<IPhongBan['id'] | NewPhongBan['id']>;
  maPB: FormControl<IPhongBan['maPB']>;
  tenPB: FormControl<IPhongBan['tenPB']>;
  sDT: FormControl<IPhongBan['sDT']>;
};

export type PhongBanFormGroup = FormGroup<PhongBanFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PhongBanFormService {
  createPhongBanFormGroup(phongBan: PhongBanFormGroupInput = { id: null }): PhongBanFormGroup {
    const phongBanRawValue = {
      ...this.getFormDefaults(),
      ...phongBan,
    };
    return new FormGroup<PhongBanFormGroupContent>({
      id: new FormControl(
        { value: phongBanRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maPB: new FormControl(phongBanRawValue.maPB, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      tenPB: new FormControl(phongBanRawValue.tenPB, {
        validators: [Validators.maxLength(50)],
      }),
      sDT: new FormControl(phongBanRawValue.sDT, {
        validators: [Validators.maxLength(20)],
      }),
    });
  }

  getPhongBan(form: PhongBanFormGroup): IPhongBan | NewPhongBan {
    return form.getRawValue() as IPhongBan | NewPhongBan;
  }

  resetForm(form: PhongBanFormGroup, phongBan: PhongBanFormGroupInput): void {
    const phongBanRawValue = { ...this.getFormDefaults(), ...phongBan };
    form.reset(
      {
        ...phongBanRawValue,
        id: { value: phongBanRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PhongBanFormDefaults {
    return {
      id: null,
    };
  }
}
