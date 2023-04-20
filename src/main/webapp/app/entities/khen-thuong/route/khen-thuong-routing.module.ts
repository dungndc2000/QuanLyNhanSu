import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KhenThuongComponent } from '../list/khen-thuong.component';
import { KhenThuongDetailComponent } from '../detail/khen-thuong-detail.component';
import { KhenThuongUpdateComponent } from '../update/khen-thuong-update.component';
import { KhenThuongRoutingResolveService } from './khen-thuong-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const khenThuongRoute: Routes = [
  {
    path: '',
    component: KhenThuongComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KhenThuongDetailComponent,
    resolve: {
      khenThuong: KhenThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KhenThuongUpdateComponent,
    resolve: {
      khenThuong: KhenThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KhenThuongUpdateComponent,
    resolve: {
      khenThuong: KhenThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(khenThuongRoute)],
  exports: [RouterModule],
})
export class KhenThuongRoutingModule {}
