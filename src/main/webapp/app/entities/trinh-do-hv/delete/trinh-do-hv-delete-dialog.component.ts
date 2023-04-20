import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrinhDoHV } from '../trinh-do-hv.model';
import { TrinhDoHVService } from '../service/trinh-do-hv.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './trinh-do-hv-delete-dialog.component.html',
})
export class TrinhDoHVDeleteDialogComponent {
  trinhDoHV?: ITrinhDoHV;

  constructor(protected trinhDoHVService: TrinhDoHVService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trinhDoHVService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
