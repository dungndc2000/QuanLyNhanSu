import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChucVuComponent } from './list/chuc-vu.component';
import { ChucVuDetailComponent } from './detail/chuc-vu-detail.component';
import { ChucVuUpdateComponent } from './update/chuc-vu-update.component';
import { ChucVuDeleteDialogComponent } from './delete/chuc-vu-delete-dialog.component';
import { ChucVuRoutingModule } from './route/chuc-vu-routing.module';

@NgModule({
  imports: [SharedModule, ChucVuRoutingModule],
  declarations: [ChucVuComponent, ChucVuDetailComponent, ChucVuUpdateComponent, ChucVuDeleteDialogComponent],
})
export class ChucVuModule {}
