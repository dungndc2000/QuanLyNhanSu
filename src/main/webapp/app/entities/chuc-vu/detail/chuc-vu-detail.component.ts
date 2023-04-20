import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChucVu } from '../chuc-vu.model';

@Component({
  selector: 'jhi-chuc-vu-detail',
  templateUrl: './chuc-vu-detail.component.html',
})
export class ChucVuDetailComponent implements OnInit {
  chucVu: IChucVu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chucVu }) => {
      this.chucVu = chucVu;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
