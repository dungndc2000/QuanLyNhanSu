import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrinhDoHVComponent } from './list/trinh-do-hv.component';
import { TrinhDoHVDetailComponent } from './detail/trinh-do-hv-detail.component';
import { TrinhDoHVUpdateComponent } from './update/trinh-do-hv-update.component';
import { TrinhDoHVDeleteDialogComponent } from './delete/trinh-do-hv-delete-dialog.component';
import { TrinhDoHVRoutingModule } from './route/trinh-do-hv-routing.module';

@NgModule({
  imports: [SharedModule, TrinhDoHVRoutingModule],
  declarations: [TrinhDoHVComponent, TrinhDoHVDetailComponent, TrinhDoHVUpdateComponent, TrinhDoHVDeleteDialogComponent],
})
export class TrinhDoHVModule {}
