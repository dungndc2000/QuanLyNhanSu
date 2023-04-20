import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChuyenMon, NewChuyenMon } from '../chuyen-mon.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChuyenMon for edit and NewChuyenMonFormGroupInput for create.
 */
type ChuyenMonFormGroupInput = IChuyenMon | PartialWithRequiredKeyOf<NewChuyenMon>;

type ChuyenMonFormDefaults = Pick<NewChuyenMon, 'id'>;

type ChuyenMonFormGroupContent = {
  id: FormControl<IChuyenMon['id'] | NewChuyenMon['id']>;
  maChuyenMon: FormControl<IChuyenMon['maChuyenMon']>;
  tenChuyenMon: FormControl<IChuyenMon['tenChuyenMon']>;
};

export type ChuyenMonFormGroup = FormGroup<ChuyenMonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChuyenMonFormService {
  createChuyenMonFormGroup(chuyenMon: ChuyenMonFormGroupInput = { id: null }): ChuyenMonFormGroup {
    const chuyenMonRawValue = {
      ...this.getFormDefaults(),
      ...chuyenMon,
    };
    return new FormGroup<ChuyenMonFormGroupContent>({
      id: new FormControl(
        { value: chuyenMonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maChuyenMon: new FormControl(chuyenMonRawValue.maChuyenMon, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      tenChuyenMon: new FormControl(chuyenMonRawValue.tenChuyenMon, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getChuyenMon(form: ChuyenMonFormGroup): IChuyenMon | NewChuyenMon {
    return form.getRawValue() as IChuyenMon | NewChuyenMon;
  }

  resetForm(form: ChuyenMonFormGroup, chuyenMon: ChuyenMonFormGroupInput): void {
    const chuyenMonRawValue = { ...this.getFormDefaults(), ...chuyenMon };
    form.reset(
      {
        ...chuyenMonRawValue,
        id: { value: chuyenMonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ChuyenMonFormDefaults {
    return {
      id: null,
    };
  }
}
