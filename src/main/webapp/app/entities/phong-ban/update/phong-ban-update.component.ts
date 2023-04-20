import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PhongBanFormService, PhongBanFormGroup } from './phong-ban-form.service';
import { IPhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';

@Component({
  selector: 'jhi-phong-ban-update',
  templateUrl: './phong-ban-update.component.html',
})
export class PhongBanUpdateComponent implements OnInit {
  isSaving = false;
  phongBan: IPhongBan | null = null;

  editForm: PhongBanFormGroup = this.phongBanFormService.createPhongBanFormGroup();

  constructor(
    protected phongBanService: PhongBanService,
    protected phongBanFormService: PhongBanFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongBan }) => {
      this.phongBan = phongBan;
      if (phongBan) {
        this.updateForm(phongBan);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongBan = this.phongBanFormService.getPhongBan(this.editForm);
    if (phongBan.id !== null) {
      this.subscribeToSaveResponse(this.phongBanService.update(phongBan));
    } else {
      this.subscribeToSaveResponse(this.phongBanService.create(phongBan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongBan>>): void {
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

  protected updateForm(phongBan: IPhongBan): void {
    this.phongBan = phongBan;
    this.phongBanFormService.resetForm(this.editForm, phongBan);
  }
}
