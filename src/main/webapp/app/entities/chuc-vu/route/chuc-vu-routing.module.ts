import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChucVuComponent } from '../list/chuc-vu.component';
import { ChucVuDetailComponent } from '../detail/chuc-vu-detail.component';
import { ChucVuUpdateComponent } from '../update/chuc-vu-update.component';
import { ChucVuRoutingResolveService } from './chuc-vu-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const chucVuRoute: Routes = [
  {
    path: '',
    component: ChucVuComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChucVuDetailComponent,
    resolve: {
      chucVu: ChucVuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChucVuUpdateComponent,
    resolve: {
      chucVu: ChucVuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChucVuUpdateComponent,
    resolve: {
      chucVu: ChucVuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chucVuRoute)],
  exports: [RouterModule],
})
export class ChucVuRoutingModule {}
