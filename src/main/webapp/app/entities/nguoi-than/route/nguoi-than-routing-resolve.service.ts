import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INguoiThan } from '../nguoi-than.model';
import { NguoiThanService } from '../service/nguoi-than.service';

@Injectable({ providedIn: 'root' })
export class NguoiThanRoutingResolveService implements Resolve<INguoiThan | null> {
  constructor(protected service: NguoiThanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INguoiThan | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nguoiThan: HttpResponse<INguoiThan>) => {
          if (nguoiThan.body) {
            return of(nguoiThan.body);
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
