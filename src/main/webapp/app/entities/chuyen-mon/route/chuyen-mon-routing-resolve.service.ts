import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChuyenMon } from '../chuyen-mon.model';
import { ChuyenMonService } from '../service/chuyen-mon.service';

@Injectable({ providedIn: 'root' })
export class ChuyenMonRoutingResolveService implements Resolve<IChuyenMon | null> {
  constructor(protected service: ChuyenMonService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChuyenMon | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chuyenMon: HttpResponse<IChuyenMon>) => {
          if (chuyenMon.body) {
            return of(chuyenMon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
