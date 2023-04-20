import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChucVu } from '../chuc-vu.model';
import { ChucVuService } from '../service/chuc-vu.service';

@Injectable({ providedIn: 'root' })
export class ChucVuRoutingResolveService implements Resolve<IChucVu | null> {
  constructor(protected service: ChucVuService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChucVu | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chucVu: HttpResponse<IChucVu>) => {
          if (chucVu.body) {
            return of(chucVu.body);
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
