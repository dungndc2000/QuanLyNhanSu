import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NguoiThanFormService, NguoiThanFormGroup } from './nguoi-than-form.service';
import { INguoiThan } from '../nguoi-than.model';
import { NguoiThanService } from '../service/nguoi-than.service';

@Component({
  selector: 'jhi-nguoi-than-update',
  templateUrl: './nguoi-than-update.component.html',
})
export class NguoiThanUpdateComponent implements OnInit {
  isSaving = false;
  nguoiThan: INguoiThan | null = null;

  editForm: NguoiThanFormGroup = this.nguoiThanFormService.createNguoiThanFormGroup();

  constructor(
    protected nguoiThanService: NguoiThanService,
    protected nguoiThanFormService: NguoiThanFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nguoiThan }) => {
      this.nguoiThan = nguoiThan;
      if (nguoiThan) {
        this.updateForm(nguoiThan);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nguoiThan = this.nguoiThanFormService.getNguoiThan(this.editForm);
    if (nguoiThan.id !== null) {
      this.subscribeToSaveResponse(this.nguoiThanService.update(nguoiThan));
    } else {
      this.subscribeToSaveResponse(this.nguoiThanService.create(nguoiThan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INguoiThan>>): void {
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

  protected updateForm(nguoiThan: INguoiThan): void {
    this.nguoiThan = nguoiThan;
    this.nguoiThanFormService.resetForm(this.editForm, nguoiThan);
  }
}
