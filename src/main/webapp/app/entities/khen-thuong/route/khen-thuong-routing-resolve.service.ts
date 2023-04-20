import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKhenThuong } from '../khen-thuong.model';
import { KhenThuongService } from '../service/khen-thuong.service';

@Injectable({ providedIn: 'root' })
export class KhenThuongRoutingResolveService implements Resolve<IKhenThuong | null> {
  constructor(protected service: KhenThuongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKhenThuong | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((khenThuong: HttpResponse<IKhenThuong>) => {
          if (khenThuong.body) {
            return of(khenThuong.body);
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
