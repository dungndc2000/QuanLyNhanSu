import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKhenThuong, NewKhenThuong } from '../khen-thuong.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKhenThuong for edit and NewKhenThuongFormGroupInput for create.
 */
type KhenThuongFormGroupInput = IKhenThuong | PartialWithRequiredKeyOf<NewKhenThuong>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKhenThuong | NewKhenThuong> = Omit<T, 'ngayQd'> & {
  ngayQd?: string | null;
};

type KhenThuongFormRawValue = FormValueOf<IKhenThuong>;

type NewKhenThuongFormRawValue = FormValueOf<NewKhenThuong>;

type KhenThuongFormDefaults = Pick<NewKhenThuong, 'id' | 'ngayQd'>;

type KhenThuongFormGroupContent = {
  id: FormControl<KhenThuongFormRawValue['id'] | NewKhenThuong['id']>;
  soqd: FormControl<KhenThuongFormRawValue['soqd']>;
  ngayQd: FormControl<KhenThuongFormRawValue['ngayQd']>;
  ten: FormControl<KhenThuongFormRawValue['ten']>;
  loai: FormControl<KhenThuongFormRawValue['loai']>;
  hinhThuc: FormControl<KhenThuongFormRawValue['hinhThuc']>;
  soTien: FormControl<KhenThuongFormRawValue['soTien']>;
  noiDung: FormControl<KhenThuongFormRawValue['noiDung']>;
  nhanVien: FormControl<KhenThuongFormRawValue['nhanVien']>;
};

export type KhenThuongFormGroup = FormGroup<KhenThuongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KhenThuongFormService {
  createKhenThuongFormGroup(khenThuong: KhenThuongFormGroupInput = { id: null }): KhenThuongFormGroup {
    const khenThuongRawValue = this.convertKhenThuongToKhenThuongRawValue({
      ...this.getFormDefaults(),
      ...khenThuong,
    });
    return new FormGroup<KhenThuongFormGroupContent>({
      id: new FormControl(
        { value: khenThuongRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      soqd: new FormControl(khenThuongRawValue.soqd, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      ngayQd: new FormControl(khenThuongRawValue.ngayQd, {
        validators: [Validators.required],
      }),
      ten: new FormControl(khenThuongRawValue.ten, {
        validators: [Validators.maxLength(20)],
      }),
      loai: new FormControl(khenThuongRawValue.loai, {
        validators: [Validators.maxLength(20)],
      }),
      hinhThuc: new FormControl(khenThuongRawValue.hinhThuc, {
        validators: [Validators.maxLength(20)],
      }),
      soTien: new FormControl(khenThuongRawValue.soTien, {
        validators: [Validators.maxLength(20)],
      }),
      noiDung: new FormControl(khenThuongRawValue.noiDung, {
        validators: [Validators.maxLength(20)],
      }),
      nhanVien: new FormControl(khenThuongRawValue.nhanVien),
    });
  }

  getKhenThuong(form: KhenThuongFormGroup): IKhenThuong | NewKhenThuong {
    return this.convertKhenThuongRawValueToKhenThuong(form.getRawValue() as KhenThuongFormRawValue | NewKhenThuongFormRawValue);
  }

  resetForm(form: KhenThuongFormGroup, khenThuong: KhenThuongFormGroupInput): void {
    const khenThuongRawValue = this.convertKhenThuongToKhenThuongRawValue({ ...this.getFormDefaults(), ...khenThuong });
    form.reset(
      {
        ...khenThuongRawValue,
        id: { value: khenThuongRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KhenThuongFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ngayQd: currentTime,
    };
  }

  private convertKhenThuongRawValueToKhenThuong(
    rawKhenThuong: KhenThuongFormRawValue | NewKhenThuongFormRawValue
  ): IKhenThuong | NewKhenThuong {
    return {
      ...rawKhenThuong,
      ngayQd: dayjs(rawKhenThuong.ngayQd, DATE_TIME_FORMAT),
    };
  }

  private convertKhenThuongToKhenThuongRawValue(
    khenThuong: IKhenThuong | (Partial<NewKhenThuong> & KhenThuongFormDefaults)
  ): KhenThuongFormRawValue | PartialWithRequiredKeyOf<NewKhenThuongFormRawValue> {
    return {
      ...khenThuong,
      ngayQd: khenThuong.ngayQd ? khenThuong.ngayQd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
