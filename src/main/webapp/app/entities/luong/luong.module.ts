import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LuongComponent } from './list/luong.component';
import { LuongDetailComponent } from './detail/luong-detail.component';
import { LuongUpdateComponent } from './update/luong-update.component';
import { LuongDeleteDialogComponent } from './delete/luong-delete-dialog.component';
import { LuongRoutingModule } from './route/luong-routing.module';

@NgModule({
  imports: [SharedModule, LuongRoutingModule],
  declarations: [LuongComponent, LuongDetailComponent, LuongUpdateComponent, LuongDeleteDialogComponent],
})
export class LuongModule {}
