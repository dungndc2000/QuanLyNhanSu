import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ChucVuFormService, ChucVuFormGroup } from './chuc-vu-form.service';
import { IChucVu } from '../chuc-vu.model';
import { ChucVuService } from '../service/chuc-vu.service';

@Component({
  selector: 'jhi-chuc-vu-update',
  templateUrl: './chuc-vu-update.component.html',
})
export class ChucVuUpdateComponent implements OnInit {
  isSaving = false;
  chucVu: IChucVu | null = null;

  editForm: ChucVuFormGroup = this.chucVuFormService.createChucVuFormGroup();

  constructor(
    protected chucVuService: ChucVuService,
    protected chucVuFormService: ChucVuFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chucVu }) => {
      this.chucVu = chucVu;
      if (chucVu) {
        this.updateForm(chucVu);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chucVu = this.chucVuFormService.getChucVu(this.editForm);
    if (chucVu.id !== null) {
      this.subscribeToSaveResponse(this.chucVuService.update(chucVu));
    } else {
      this.subscribeToSaveResponse(this.chucVuService.create(chucVu));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChucVu>>): void {
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

  protected updateForm(chucVu: IChucVu): void {
    this.chucVu = chucVu;
    this.chucVuFormService.resetForm(this.editForm, chucVu);
  }
}
