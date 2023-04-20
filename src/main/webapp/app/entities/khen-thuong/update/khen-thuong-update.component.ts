import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KhenThuongFormService, KhenThuongFormGroup } from './khen-thuong-form.service';
import { IKhenThuong } from '../khen-thuong.model';
import { KhenThuongService } from '../service/khen-thuong.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

@Component({
  selector: 'jhi-khen-thuong-update',
  templateUrl: './khen-thuong-update.component.html',
})
export class KhenThuongUpdateComponent implements OnInit {
  isSaving = false;
  khenThuong: IKhenThuong | null = null;

  nhanViensSharedCollection: INhanVien[] = [];

  editForm: KhenThuongFormGroup = this.khenThuongFormService.createKhenThuongFormGroup();

  constructor(
    protected khenThuongService: KhenThuongService,
    protected khenThuongFormService: KhenThuongFormService,
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNhanVien = (o1: INhanVien | null, o2: INhanVien | null): boolean => this.nhanVienService.compareNhanVien(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ khenThuong }) => {
      this.khenThuong = khenThuong;
      if (khenThuong) {
        this.updateForm(khenThuong);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const khenThuong = this.khenThuongFormService.getKhenThuong(this.editForm);
    if (khenThuong.id !== null) {
      this.subscribeToSaveResponse(this.khenThuongService.update(khenThuong));
    } else {
      this.subscribeToSaveResponse(this.khenThuongService.create(khenThuong));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKhenThuong>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(khenThuong: IKhenThuong): void {
    this.khenThuong = khenThuong;
    this.khenThuongFormService.resetForm(this.editForm, khenThuong);

    this.nhanViensSharedCollection = this.nhanVienService.addNhanVienToCollectionIfMissing<INhanVien>(
      this.nhanViensSharedCollection,
      khenThuong.nhanVien
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanVienService
      .query()
      .pipe(map((res: HttpResponse<INhanVien[]>) => res.body ?? []))
      .pipe(
        map((nhanViens: INhanVien[]) =>
          this.nhanVienService.addNhanVienToCollectionIfMissing<INhanVien>(nhanViens, this.khenThuong?.nhanVien)
        )
      )
      .subscribe((nhanViens: INhanVien[]) => (this.nhanViensSharedCollection = nhanViens));
  }
}
