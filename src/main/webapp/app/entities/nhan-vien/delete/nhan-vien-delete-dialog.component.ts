import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nhan-vien-delete-dialog.component.html',
})
export class NhanVienDeleteDialogComponent {
  nhanVien?: INhanVien;

  constructor(protected nhanVienService: NhanVienService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhanVienService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
