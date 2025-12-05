import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAuditHistory } from '../audit-history.model';
import { AuditHistoryService } from '../service/audit-history.service';

@Component({
  templateUrl: './audit-history-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AuditHistoryDeleteDialogComponent {
  auditHistory?: IAuditHistory;

  protected auditHistoryService = inject(AuditHistoryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
