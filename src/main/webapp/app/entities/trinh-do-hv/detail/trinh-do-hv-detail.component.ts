import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrinhDoHV } from '../trinh-do-hv.model';

@Component({
  selector: 'jhi-trinh-do-hv-detail',
  templateUrl: './trinh-do-hv-detail.component.html',
})
export class TrinhDoHVDetailComponent implements OnInit {
  trinhDoHV: ITrinhDoHV | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trinhDoHV }) => {
      this.trinhDoHV = trinhDoHV;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
