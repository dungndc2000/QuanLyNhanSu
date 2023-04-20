import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INguoiThan } from '../nguoi-than.model';
import { NguoiThanService } from '../service/nguoi-than.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nguoi-than-delete-dialog.component.html',
})
export class NguoiThanDeleteDialogComponent {
  nguoiThan?: INguoiThan;

  constructor(protected nguoiThanService: NguoiThanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nguoiThanService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
