import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMatchResult } from '../match-result.model';
import { MatchResultService } from '../service/match-result.service';

@Component({
  templateUrl: './match-result-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MatchResultDeleteDialogComponent {
  matchResult?: IMatchResult;

  protected matchResultService = inject(MatchResultService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matchResultService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
