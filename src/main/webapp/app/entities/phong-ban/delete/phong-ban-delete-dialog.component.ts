import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongBan } from '../phong-ban.model';
import { PhongBanService } from '../service/phong-ban.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './phong-ban-delete-dialog.component.html',
})
export class PhongBanDeleteDialogComponent {
  phongBan?: IPhongBan;

  constructor(protected phongBanService: PhongBanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phongBanService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
