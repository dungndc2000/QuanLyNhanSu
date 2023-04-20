import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChuyenMonComponent } from '../list/chuyen-mon.component';
import { ChuyenMonDetailComponent } from '../detail/chuyen-mon-detail.component';
import { ChuyenMonUpdateComponent } from '../update/chuyen-mon-update.component';
import { ChuyenMonRoutingResolveService } from './chuyen-mon-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const chuyenMonRoute: Routes = [
  {
    path: '',
    component: ChuyenMonComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChuyenMonDetailComponent,
    resolve: {
      chuyenMon: ChuyenMonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChuyenMonUpdateComponent,
    resolve: {
      chuyenMon: ChuyenMonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChuyenMonUpdateComponent,
    resolve: {
      chuyenMon: ChuyenMonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chuyenMonRoute)],
  exports: [RouterModule],
})
export class ChuyenMonRoutingModule {}
