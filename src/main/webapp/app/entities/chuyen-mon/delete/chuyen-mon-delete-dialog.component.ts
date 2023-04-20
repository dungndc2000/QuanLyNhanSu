import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChuyenMon } from '../chuyen-mon.model';
import { ChuyenMonService } from '../service/chuyen-mon.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './chuyen-mon-delete-dialog.component.html',
})
export class ChuyenMonDeleteDialogComponent {
  chuyenMon?: IChuyenMon;

  constructor(protected chuyenMonService: ChuyenMonService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chuyenMonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
