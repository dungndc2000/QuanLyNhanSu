import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ChuyenMonFormService, ChuyenMonFormGroup } from './chuyen-mon-form.service';
import { IChuyenMon } from '../chuyen-mon.model';
import { ChuyenMonService } from '../service/chuyen-mon.service';

@Component({
  selector: 'jhi-chuyen-mon-update',
  templateUrl: './chuyen-mon-update.component.html',
})
export class ChuyenMonUpdateComponent implements OnInit {
  isSaving = false;
  chuyenMon: IChuyenMon | null = null;

  editForm: ChuyenMonFormGroup = this.chuyenMonFormService.createChuyenMonFormGroup();

  constructor(
    protected chuyenMonService: ChuyenMonService,
    protected chuyenMonFormService: ChuyenMonFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chuyenMon }) => {
      this.chuyenMon = chuyenMon;
      if (chuyenMon) {
        this.updateForm(chuyenMon);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chuyenMon = this.chuyenMonFormService.getChuyenMon(this.editForm);
    if (chuyenMon.id !== null) {
      this.subscribeToSaveResponse(this.chuyenMonService.update(chuyenMon));
    } else {
      this.subscribeToSaveResponse(this.chuyenMonService.create(chuyenMon));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChuyenMon>>): void {
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

  protected updateForm(chuyenMon: IChuyenMon): void {
    this.chuyenMon = chuyenMon;
    this.chuyenMonFormService.resetForm(this.editForm, chuyenMon);
  }
}
