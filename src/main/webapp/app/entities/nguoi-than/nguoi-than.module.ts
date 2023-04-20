import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NguoiThanComponent } from './list/nguoi-than.component';
import { NguoiThanDetailComponent } from './detail/nguoi-than-detail.component';
import { NguoiThanUpdateComponent } from './update/nguoi-than-update.component';
import { NguoiThanDeleteDialogComponent } from './delete/nguoi-than-delete-dialog.component';
import { NguoiThanRoutingModule } from './route/nguoi-than-routing.module';

@NgModule({
  imports: [SharedModule, NguoiThanRoutingModule],
  declarations: [NguoiThanComponent, NguoiThanDetailComponent, NguoiThanUpdateComponent, NguoiThanDeleteDialogComponent],
})
export class NguoiThanModule {}
