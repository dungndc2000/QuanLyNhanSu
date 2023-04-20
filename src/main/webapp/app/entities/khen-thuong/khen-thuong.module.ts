import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KhenThuongComponent } from './list/khen-thuong.component';
import { KhenThuongDetailComponent } from './detail/khen-thuong-detail.component';
import { KhenThuongUpdateComponent } from './update/khen-thuong-update.component';
import { KhenThuongDeleteDialogComponent } from './delete/khen-thuong-delete-dialog.component';
import { KhenThuongRoutingModule } from './route/khen-thuong-routing.module';

@NgModule({
  imports: [SharedModule, KhenThuongRoutingModule],
  declarations: [KhenThuongComponent, KhenThuongDetailComponent, KhenThuongUpdateComponent, KhenThuongDeleteDialogComponent],
})
export class KhenThuongModule {}
