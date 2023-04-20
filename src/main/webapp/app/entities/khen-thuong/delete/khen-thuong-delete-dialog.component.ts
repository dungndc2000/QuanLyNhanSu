import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKhenThuong } from '../khen-thuong.model';
import { KhenThuongService } from '../service/khen-thuong.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './khen-thuong-delete-dialog.component.html',
})
export class KhenThuongDeleteDialogComponent {
  khenThuong?: IKhenThuong;

  constructor(protected khenThuongService: KhenThuongService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.khenThuongService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
