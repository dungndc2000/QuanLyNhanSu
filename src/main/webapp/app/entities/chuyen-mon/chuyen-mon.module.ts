import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChuyenMonComponent } from './list/chuyen-mon.component';
import { ChuyenMonDetailComponent } from './detail/chuyen-mon-detail.component';
import { ChuyenMonUpdateComponent } from './update/chuyen-mon-update.component';
import { ChuyenMonDeleteDialogComponent } from './delete/chuyen-mon-delete-dialog.component';
import { ChuyenMonRoutingModule } from './route/chuyen-mon-routing.module';

@NgModule({
  imports: [SharedModule, ChuyenMonRoutingModule],
  declarations: [ChuyenMonComponent, ChuyenMonDetailComponent, ChuyenMonUpdateComponent, ChuyenMonDeleteDialogComponent],
})
export class ChuyenMonModule {}
