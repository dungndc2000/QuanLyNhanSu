import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrinhDoHV, NewTrinhDoHV } from '../trinh-do-hv.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrinhDoHV for edit and NewTrinhDoHVFormGroupInput for create.
 */
type TrinhDoHVFormGroupInput = ITrinhDoHV | PartialWithRequiredKeyOf<NewTrinhDoHV>;

type TrinhDoHVFormDefaults = Pick<NewTrinhDoHV, 'id'>;

type TrinhDoHVFormGroupContent = {
  id: FormControl<ITrinhDoHV['id'] | NewTrinhDoHV['id']>;
  maTDHV: FormControl<ITrinhDoHV['maTDHV']>;
  tenTDHV: FormControl<ITrinhDoHV['tenTDHV']>;
};

export type TrinhDoHVFormGroup = FormGroup<TrinhDoHVFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrinhDoHVFormService {
  createTrinhDoHVFormGroup(trinhDoHV: TrinhDoHVFormGroupInput = { id: null }): TrinhDoHVFormGroup {
    const trinhDoHVRawValue = {
      ...this.getFormDefaults(),
      ...trinhDoHV,
    };
    return new FormGroup<TrinhDoHVFormGroupContent>({
      id: new FormControl(
        { value: trinhDoHVRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maTDHV: new FormControl(trinhDoHVRawValue.maTDHV, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      tenTDHV: new FormControl(trinhDoHVRawValue.tenTDHV, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getTrinhDoHV(form: TrinhDoHVFormGroup): ITrinhDoHV | NewTrinhDoHV {
    return form.getRawValue() as ITrinhDoHV | NewTrinhDoHV;
  }

  resetForm(form: TrinhDoHVFormGroup, trinhDoHV: TrinhDoHVFormGroupInput): void {
    const trinhDoHVRawValue = { ...this.getFormDefaults(), ...trinhDoHV };
    form.reset(
      {
        ...trinhDoHVRawValue,
        id: { value: trinhDoHVRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrinhDoHVFormDefaults {
    return {
      id: null,
    };
  }
}
