import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILuong, NewLuong } from '../luong.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILuong for edit and NewLuongFormGroupInput for create.
 */
type LuongFormGroupInput = ILuong | PartialWithRequiredKeyOf<NewLuong>;

type LuongFormDefaults = Pick<NewLuong, 'id'>;

type LuongFormGroupContent = {
  id: FormControl<ILuong['id'] | NewLuong['id']>;
  maLuong: FormControl<ILuong['maLuong']>;
  heSoLuong: FormControl<ILuong['heSoLuong']>;
  luongCb: FormControl<ILuong['luongCb']>;
  heSoPhuCap: FormControl<ILuong['heSoPhuCap']>;
  khoanCongThem: FormControl<ILuong['khoanCongThem']>;
  khoanTru: FormControl<ILuong['khoanTru']>;
  nhanVien: FormControl<ILuong['nhanVien']>;
};

export type LuongFormGroup = FormGroup<LuongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LuongFormService {
  createLuongFormGroup(luong: LuongFormGroupInput = { id: null }): LuongFormGroup {
    const luongRawValue = {
      ...this.getFormDefaults(),
      ...luong,
    };
    return new FormGroup<LuongFormGroupContent>({
      id: new FormControl(
        { value: luongRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maLuong: new FormControl(luongRawValue.maLuong, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      heSoLuong: new FormControl(luongRawValue.heSoLuong, {
        validators: [Validators.maxLength(10)],
      }),
      luongCb: new FormControl(luongRawValue.luongCb, {
        validators: [Validators.maxLength(20)],
      }),
      heSoPhuCap: new FormControl(luongRawValue.heSoPhuCap, {
        validators: [Validators.maxLength(20)],
      }),
      khoanCongThem: new FormControl(luongRawValue.khoanCongThem, {
        validators: [Validators.maxLength(20)],
      }),
      khoanTru: new FormControl(luongRawValue.khoanTru, {
        validators: [Validators.maxLength(20)],
      }),
      nhanVien: new FormControl(luongRawValue.nhanVien),
    });
  }

  getLuong(form: LuongFormGroup): ILuong | NewLuong {
    return form.getRawValue() as ILuong | NewLuong;
  }

  resetForm(form: LuongFormGroup, luong: LuongFormGroupInput): void {
    const luongRawValue = { ...this.getFormDefaults(), ...luong };
    form.reset(
      {
        ...luongRawValue,
        id: { value: luongRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LuongFormDefaults {
    return {
      id: null,
    };
  }
}
