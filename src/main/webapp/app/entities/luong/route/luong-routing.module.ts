import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LuongComponent } from '../list/luong.component';
import { LuongDetailComponent } from '../detail/luong-detail.component';
import { LuongUpdateComponent } from '../update/luong-update.component';
import { LuongRoutingResolveService } from './luong-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const luongRoute: Routes = [
  {
    path: '',
    component: LuongComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LuongDetailComponent,
    resolve: {
      luong: LuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LuongUpdateComponent,
    resolve: {
      luong: LuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LuongUpdateComponent,
    resolve: {
      luong: LuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(luongRoute)],
  exports: [RouterModule],
})
export class LuongRoutingModule {}
