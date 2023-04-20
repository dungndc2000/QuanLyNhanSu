import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILuong } from '../luong.model';
import { LuongService } from '../service/luong.service';

@Injectable({ providedIn: 'root' })
export class LuongRoutingResolveService implements Resolve<ILuong | null> {
  constructor(protected service: LuongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILuong | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((luong: HttpResponse<ILuong>) => {
          if (luong.body) {
            return of(luong.body);
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
