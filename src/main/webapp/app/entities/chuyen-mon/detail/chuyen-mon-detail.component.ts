import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChuyenMon } from '../chuyen-mon.model';

@Component({
  selector: 'jhi-chuyen-mon-detail',
  templateUrl: './chuyen-mon-detail.component.html',
})
export class ChuyenMonDetailComponent implements OnInit {
  chuyenMon: IChuyenMon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chuyenMon }) => {
      this.chuyenMon = chuyenMon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
