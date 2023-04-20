import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NhanVienFormService, NhanVienFormGroup } from './nhan-vien-form.service';
import { INhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';
import { INguoiThan } from 'app/entities/nguoi-than/nguoi-than.model';
import { NguoiThanService } from 'app/entities/nguoi-than/service/nguoi-than.service';
import { IChucVu } from 'app/entities/chuc-vu/chuc-vu.model';
import { ChucVuService } from 'app/entities/chuc-vu/service/chuc-vu.service';
import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { PhongBanService } from 'app/entities/phong-ban/service/phong-ban.service';
import { IChuyenMon } from 'app/entities/chuyen-mon/chuyen-mon.model';
import { ChuyenMonService } from 'app/entities/chuyen-mon/service/chuyen-mon.service';
import { ITrinhDoHV } from 'app/entities/trinh-do-hv/trinh-do-hv.model';
import { TrinhDoHVService } from 'app/entities/trinh-do-hv/service/trinh-do-hv.service';

@Component({
  selector: 'jhi-nhan-vien-update',
  templateUrl: './nhan-vien-update.component.html',
})
export class NhanVienUpdateComponent implements OnInit {
  isSaving = false;
  nhanVien: INhanVien | null = null;

  nguoiThansCollection: INguoiThan[] = [];
  chucVusSharedCollection: IChucVu[] = [];
  phongBansSharedCollection: IPhongBan[] = [];
  chuyenMonsSharedCollection: IChuyenMon[] = [];
  trinhDoHVSSharedCollection: ITrinhDoHV[] = [];

  editForm: NhanVienFormGroup = this.nhanVienFormService.createNhanVienFormGroup();

  constructor(
    protected nhanVienService: NhanVienService,
    protected nhanVienFormService: NhanVienFormService,
    protected nguoiThanService: NguoiThanService,
    protected chucVuService: ChucVuService,
    protected phongBanService: PhongBanService,
    protected chuyenMonService: ChuyenMonService,
    protected trinhDoHVService: TrinhDoHVService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNguoiThan = (o1: INguoiThan | null, o2: INguoiThan | null): boolean => this.nguoiThanService.compareNguoiThan(o1, o2);

  compareChucVu = (o1: IChucVu | null, o2: IChucVu | null): boolean => this.chucVuService.compareChucVu(o1, o2);

  comparePhongBan = (o1: IPhongBan | null, o2: IPhongBan | null): boolean => this.phongBanService.comparePhongBan(o1, o2);

  compareChuyenMon = (o1: IChuyenMon | null, o2: IChuyenMon | null): boolean => this.chuyenMonService.compareChuyenMon(o1, o2);

  compareTrinhDoHV = (o1: ITrinhDoHV | null, o2: ITrinhDoHV | null): boolean => this.trinhDoHVService.compareTrinhDoHV(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVien }) => {
      this.nhanVien = nhanVien;
      if (nhanVien) {
        this.updateForm(nhanVien);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhanVien = this.nhanVienFormService.getNhanVien(this.editForm);
    if (nhanVien.id !== null) {
      this.subscribeToSaveResponse(this.nhanVienService.update(nhanVien));
    } else {
      this.subscribeToSaveResponse(this.nhanVienService.create(nhanVien));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhanVien>>): void {
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

  protected updateForm(nhanVien: INhanVien): void {
    this.nhanVien = nhanVien;
    this.nhanVienFormService.resetForm(this.editForm, nhanVien);

    this.nguoiThansCollection = this.nguoiThanService.addNguoiThanToCollectionIfMissing<INguoiThan>(
      this.nguoiThansCollection,
      nhanVien.nguoiThan
    );
    this.chucVusSharedCollection = this.chucVuService.addChucVuToCollectionIfMissing<IChucVu>(
      this.chucVusSharedCollection,
      nhanVien.chucVu
    );
    this.phongBansSharedCollection = this.phongBanService.addPhongBanToCollectionIfMissing<IPhongBan>(
      this.phongBansSharedCollection,
      nhanVien.phongBan
    );
    this.chuyenMonsSharedCollection = this.chuyenMonService.addChuyenMonToCollectionIfMissing<IChuyenMon>(
      this.chuyenMonsSharedCollection,
      nhanVien.chuyenMon
    );
    this.trinhDoHVSSharedCollection = this.trinhDoHVService.addTrinhDoHVToCollectionIfMissing<ITrinhDoHV>(
      this.trinhDoHVSSharedCollection,
      nhanVien.trinhDoHV
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nguoiThanService
      .query({ 'nhanVienId.specified': 'false' })
      .pipe(map((res: HttpResponse<INguoiThan[]>) => res.body ?? []))
      .pipe(
        map((nguoiThans: INguoiThan[]) =>
          this.nguoiThanService.addNguoiThanToCollectionIfMissing<INguoiThan>(nguoiThans, this.nhanVien?.nguoiThan)
        )
      )
      .subscribe((nguoiThans: INguoiThan[]) => (this.nguoiThansCollection = nguoiThans));

    this.chucVuService
      .query()
      .pipe(map((res: HttpResponse<IChucVu[]>) => res.body ?? []))
      .pipe(map((chucVus: IChucVu[]) => this.chucVuService.addChucVuToCollectionIfMissing<IChucVu>(chucVus, this.nhanVien?.chucVu)))
      .subscribe((chucVus: IChucVu[]) => (this.chucVusSharedCollection = chucVus));

    this.phongBanService
      .query()
      .pipe(map((res: HttpResponse<IPhongBan[]>) => res.body ?? []))
      .pipe(
        map((phongBans: IPhongBan[]) =>
          this.phongBanService.addPhongBanToCollectionIfMissing<IPhongBan>(phongBans, this.nhanVien?.phongBan)
        )
      )
      .subscribe((phongBans: IPhongBan[]) => (this.phongBansSharedCollection = phongBans));

    this.chuyenMonService
      .query()
      .pipe(map((res: HttpResponse<IChuyenMon[]>) => res.body ?? []))
      .pipe(
        map((chuyenMons: IChuyenMon[]) =>
          this.chuyenMonService.addChuyenMonToCollectionIfMissing<IChuyenMon>(chuyenMons, this.nhanVien?.chuyenMon)
        )
      )
      .subscribe((chuyenMons: IChuyenMon[]) => (this.chuyenMonsSharedCollection = chuyenMons));

    this.trinhDoHVService
      .query()
      .pipe(map((res: HttpResponse<ITrinhDoHV[]>) => res.body ?? []))
      .pipe(
        map((trinhDoHVS: ITrinhDoHV[]) =>
          this.trinhDoHVService.addTrinhDoHVToCollectionIfMissing<ITrinhDoHV>(trinhDoHVS, this.nhanVien?.trinhDoHV)
        )
      )
      .subscribe((trinhDoHVS: ITrinhDoHV[]) => (this.trinhDoHVSSharedCollection = trinhDoHVS));
  }
}
