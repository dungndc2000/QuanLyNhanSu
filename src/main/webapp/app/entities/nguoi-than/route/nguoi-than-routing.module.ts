import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NguoiThanComponent } from '../list/nguoi-than.component';
import { NguoiThanDetailComponent } from '../detail/nguoi-than-detail.component';
import { NguoiThanUpdateComponent } from '../update/nguoi-than-update.component';
import { NguoiThanRoutingResolveService } from './nguoi-than-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nguoiThanRoute: Routes = [
  {
    path: '',
    component: NguoiThanComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NguoiThanDetailComponent,
    resolve: {
      nguoiThan: NguoiThanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NguoiThanUpdateComponent,
    resolve: {
      nguoiThan: NguoiThanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NguoiThanUpdateComponent,
    resolve: {
      nguoiThan: NguoiThanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nguoiThanRoute)],
  exports: [RouterModule],
})
export class NguoiThanRoutingModule {}
