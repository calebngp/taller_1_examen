import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDeveloper } from '../developer.model';
import { DeveloperService } from '../service/developer.service';

@Component({
  templateUrl: './developer-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DeveloperDeleteDialogComponent {
  developer?: IDeveloper;

  protected developerService = inject(DeveloperService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.developerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
