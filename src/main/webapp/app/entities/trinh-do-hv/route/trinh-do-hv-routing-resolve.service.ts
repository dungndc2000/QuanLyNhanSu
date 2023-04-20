import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrinhDoHV } from '../trinh-do-hv.model';
import { TrinhDoHVService } from '../service/trinh-do-hv.service';

@Injectable({ providedIn: 'root' })
export class TrinhDoHVRoutingResolveService implements Resolve<ITrinhDoHV | null> {
  constructor(protected service: TrinhDoHVService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrinhDoHV | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trinhDoHV: HttpResponse<ITrinhDoHV>) => {
          if (trinhDoHV.body) {
            return of(trinhDoHV.body);
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
