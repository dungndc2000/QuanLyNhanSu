import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChucVu } from '../chuc-vu.model';
import { ChucVuService } from '../service/chuc-vu.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './chuc-vu-delete-dialog.component.html',
})
export class ChucVuDeleteDialogComponent {
  chucVu?: IChucVu;

  constructor(protected chucVuService: ChucVuService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chucVuService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
