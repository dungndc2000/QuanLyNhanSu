import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKhenThuong } from '../khen-thuong.model';

@Component({
  selector: 'jhi-khen-thuong-detail',
  templateUrl: './khen-thuong-detail.component.html',
})
export class KhenThuongDetailComponent implements OnInit {
  khenThuong: IKhenThuong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ khenThuong }) => {
      this.khenThuong = khenThuong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
