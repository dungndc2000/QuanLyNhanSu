import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INguoiThan } from '../nguoi-than.model';

@Component({
  selector: 'jhi-nguoi-than-detail',
  templateUrl: './nguoi-than-detail.component.html',
})
export class NguoiThanDetailComponent implements OnInit {
  nguoiThan: INguoiThan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nguoiThan }) => {
      this.nguoiThan = nguoiThan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
