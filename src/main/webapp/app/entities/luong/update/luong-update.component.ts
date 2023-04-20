import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LuongFormService, LuongFormGroup } from './luong-form.service';
import { ILuong } from '../luong.model';
import { LuongService } from '../service/luong.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

@Component({
  selector: 'jhi-luong-update',
  templateUrl: './luong-update.component.html',
})
export class LuongUpdateComponent implements OnInit {
  isSaving = false;
  luong: ILuong | null = null;

  nhanViensSharedCollection: INhanVien[] = [];

  editForm: LuongFormGroup = this.luongFormService.createLuongFormGroup();

  constructor(
    protected luongService: LuongService,
    protected luongFormService: LuongFormService,
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNhanVien = (o1: INhanVien | null, o2: INhanVien | null): boolean => this.nhanVienService.compareNhanVien(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ luong }) => {
      this.luong = luong;
      if (luong) {
        this.updateForm(luong);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const luong = this.luongFormService.getLuong(this.editForm);
    if (luong.id !== null) {
      this.subscribeToSaveResponse(this.luongService.update(luong));
    } else {
      this.subscribeToSaveResponse(this.luongService.create(luong));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILuong>>): void {
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

  protected updateForm(luong: ILuong): void {
    this.luong = luong;
    this.luongFormService.resetForm(this.editForm, luong);

    this.nhanViensSharedCollection = this.nhanVienService.addNhanVienToCollectionIfMissing<INhanVien>(
      this.nhanViensSharedCollection,
      luong.nhanVien
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nhanVienService
      .query()
      .pipe(map((res: HttpResponse<INhanVien[]>) => res.body ?? []))
      .pipe(
        map((nhanViens: INhanVien[]) => this.nhanVienService.addNhanVienToCollectionIfMissing<INhanVien>(nhanViens, this.luong?.nhanVien))
      )
      .subscribe((nhanViens: INhanVien[]) => (this.nhanViensSharedCollection = nhanViens));
  }
}
