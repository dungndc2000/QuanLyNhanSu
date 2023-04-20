import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';

@Injectable({ providedIn: 'root' })
export class NhanVienRoutingResolveService implements Resolve<INhanVien | null> {
  constructor(protected service: NhanVienService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INhanVien | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nhanVien: HttpResponse<INhanVien>) => {
          if (nhanVien.body) {
            return of(nhanVien.body);
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
