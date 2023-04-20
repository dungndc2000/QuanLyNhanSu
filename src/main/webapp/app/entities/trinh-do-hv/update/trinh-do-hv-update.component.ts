import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrinhDoHVFormService, TrinhDoHVFormGroup } from './trinh-do-hv-form.service';
import { ITrinhDoHV } from '../trinh-do-hv.model';
import { TrinhDoHVService } from '../service/trinh-do-hv.service';

@Component({
  selector: 'jhi-trinh-do-hv-update',
  templateUrl: './trinh-do-hv-update.component.html',
})
export class TrinhDoHVUpdateComponent implements OnInit {
  isSaving = false;
  trinhDoHV: ITrinhDoHV | null = null;

  editForm: TrinhDoHVFormGroup = this.trinhDoHVFormService.createTrinhDoHVFormGroup();

  constructor(
    protected trinhDoHVService: TrinhDoHVService,
    protected trinhDoHVFormService: TrinhDoHVFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trinhDoHV }) => {
      this.trinhDoHV = trinhDoHV;
      if (trinhDoHV) {
        this.updateForm(trinhDoHV);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trinhDoHV = this.trinhDoHVFormService.getTrinhDoHV(this.editForm);
    if (trinhDoHV.id !== null) {
      this.subscribeToSaveResponse(this.trinhDoHVService.update(trinhDoHV));
    } else {
      this.subscribeToSaveResponse(this.trinhDoHVService.create(trinhDoHV));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrinhDoHV>>): void {
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

  protected updateForm(trinhDoHV: ITrinhDoHV): void {
    this.trinhDoHV = trinhDoHV;
    this.trinhDoHVFormService.resetForm(this.editForm, trinhDoHV);
  }
}
