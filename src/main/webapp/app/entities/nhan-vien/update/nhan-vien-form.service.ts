import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INhanVien, NewNhanVien } from '../nhan-vien.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INhanVien for edit and NewNhanVienFormGroupInput for create.
 */
type NhanVienFormGroupInput = INhanVien | PartialWithRequiredKeyOf<NewNhanVien>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INhanVien | NewNhanVien> = Omit<T, 'ngaySinh'> & {
  ngaySinh?: string | null;
};

type NhanVienFormRawValue = FormValueOf<INhanVien>;

type NewNhanVienFormRawValue = FormValueOf<NewNhanVien>;

type NhanVienFormDefaults = Pick<NewNhanVien, 'id' | 'ngaySinh'>;

type NhanVienFormGroupContent = {
  id: FormControl<NhanVienFormRawValue['id'] | NewNhanVien['id']>;
  maNV: FormControl<NhanVienFormRawValue['maNV']>;
  tenNV: FormControl<NhanVienFormRawValue['tenNV']>;
  ngaySinh: FormControl<NhanVienFormRawValue['ngaySinh']>;
  gioiTinh: FormControl<NhanVienFormRawValue['gioiTinh']>;
  diaChi: FormControl<NhanVienFormRawValue['diaChi']>;
  soCMND: FormControl<NhanVienFormRawValue['soCMND']>;
  sDT: FormControl<NhanVienFormRawValue['sDT']>;
  email: FormControl<NhanVienFormRawValue['email']>;
  heSoLuong: FormControl<NhanVienFormRawValue['heSoLuong']>;
  nguoiThan: FormControl<NhanVienFormRawValue['nguoiThan']>;
  chucVu: FormControl<NhanVienFormRawValue['chucVu']>;
  phongBan: FormControl<NhanVienFormRawValue['phongBan']>;
  chuyenMon: FormControl<NhanVienFormRawValue['chuyenMon']>;
  trinhDoHV: FormControl<NhanVienFormRawValue['trinhDoHV']>;
};

export type NhanVienFormGroup = FormGroup<NhanVienFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NhanVienFormService {
  createNhanVienFormGroup(nhanVien: NhanVienFormGroupInput = { id: null }): NhanVienFormGroup {
    const nhanVienRawValue = this.convertNhanVienToNhanVienRawValue({
      ...this.getFormDefaults(),
      ...nhanVien,
    });
    return new FormGroup<NhanVienFormGroupContent>({
      id: new FormControl(
        { value: nhanVienRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      maNV: new FormControl(nhanVienRawValue.maNV, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      tenNV: new FormControl(nhanVienRawValue.tenNV, {
        validators: [Validators.required, Validators.maxLength(256)],
      }),
      ngaySinh: new FormControl(nhanVienRawValue.ngaySinh, {
        validators: [Validators.required],
      }),
      gioiTinh: new FormControl(nhanVienRawValue.gioiTinh, {
        validators: [Validators.required],
      }),
      diaChi: new FormControl(nhanVienRawValue.diaChi, {
        validators: [Validators.maxLength(1024)],
      }),
      soCMND: new FormControl(nhanVienRawValue.soCMND, {
        validators: [Validators.required],
      }),
      sDT: new FormControl(nhanVienRawValue.sDT, {
        validators: [Validators.maxLength(10)],
      }),
      email: new FormControl(nhanVienRawValue.email, {
        validators: [Validators.maxLength(50)],
      }),
      heSoLuong: new FormControl(nhanVienRawValue.heSoLuong, {
        validators: [Validators.maxLength(50)],
      }),
      nguoiThan: new FormControl(nhanVienRawValue.nguoiThan),
      chucVu: new FormControl(nhanVienRawValue.chucVu),
      phongBan: new FormControl(nhanVienRawValue.phongBan),
      chuyenMon: new FormControl(nhanVienRawValue.chuyenMon),
      trinhDoHV: new FormControl(nhanVienRawValue.trinhDoHV),
    });
  }

  getNhanVien(form: NhanVienFormGroup): INhanVien | NewNhanVien {
    return this.convertNhanVienRawValueToNhanVien(form.getRawValue() as NhanVienFormRawValue | NewNhanVienFormRawValue);
  }

  resetForm(form: NhanVienFormGroup, nhanVien: NhanVienFormGroupInput): void {
    const nhanVienRawValue = this.convertNhanVienToNhanVienRawValue({ ...this.getFormDefaults(), ...nhanVien });
    form.reset(
      {
        ...nhanVienRawValue,
        id: { value: nhanVienRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NhanVienFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ngaySinh: currentTime,
    };
  }

  private convertNhanVienRawValueToNhanVien(rawNhanVien: NhanVienFormRawValue | NewNhanVienFormRawValue): INhanVien | NewNhanVien {
    return {
      ...rawNhanVien,
      ngaySinh: dayjs(rawNhanVien.ngaySinh, DATE_TIME_FORMAT),
    };
  }

  private convertNhanVienToNhanVienRawValue(
    nhanVien: INhanVien | (Partial<NewNhanVien> & NhanVienFormDefaults)
  ): NhanVienFormRawValue | PartialWithRequiredKeyOf<NewNhanVienFormRawValue> {
    return {
      ...nhanVien,
      ngaySinh: nhanVien.ngaySinh ? nhanVien.ngaySinh.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
