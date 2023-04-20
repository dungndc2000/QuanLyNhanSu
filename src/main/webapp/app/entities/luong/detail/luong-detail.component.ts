import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILuong } from '../luong.model';

@Component({
  selector: 'jhi-luong-detail',
  templateUrl: './luong-detail.component.html',
})
export class LuongDetailComponent implements OnInit {
  luong: ILuong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ luong }) => {
      this.luong = luong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
