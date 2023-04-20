import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrinhDoHVComponent } from '../list/trinh-do-hv.component';
import { TrinhDoHVDetailComponent } from '../detail/trinh-do-hv-detail.component';
import { TrinhDoHVUpdateComponent } from '../update/trinh-do-hv-update.component';
import { TrinhDoHVRoutingResolveService } from './trinh-do-hv-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trinhDoHVRoute: Routes = [
  {
    path: '',
    component: TrinhDoHVComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrinhDoHVDetailComponent,
    resolve: {
      trinhDoHV: TrinhDoHVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrinhDoHVUpdateComponent,
    resolve: {
      trinhDoHV: TrinhDoHVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrinhDoHVUpdateComponent,
    resolve: {
      trinhDoHV: TrinhDoHVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trinhDoHVRoute)],
  exports: [RouterModule],
})
export class TrinhDoHVRoutingModule {}
