import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { AuditHistoryService } from '../service/audit-history.service';
import { IAuditHistory } from '../audit-history.model';
import { AuditHistoryFormGroup, AuditHistoryFormService } from './audit-history-form.service';

@Component({
  selector: 'jhi-audit-history-update',
  templateUrl: './audit-history-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AuditHistoryUpdateComponent implements OnInit {
  isSaving = false;
  auditHistory: IAuditHistory | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected auditHistoryService = inject(AuditHistoryService);
  protected auditHistoryFormService = inject(AuditHistoryFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AuditHistoryFormGroup = this.auditHistoryFormService.createAuditHistoryFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditHistory }) => {
      this.auditHistory = auditHistory;
      if (auditHistory) {
        this.updateForm(auditHistory);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('devmatchApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditHistory = this.auditHistoryFormService.getAuditHistory(this.editForm);
    if (auditHistory.id !== null) {
      this.subscribeToSaveResponse(this.auditHistoryService.update(auditHistory));
    } else {
      this.subscribeToSaveResponse(this.auditHistoryService.create(auditHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditHistory>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(auditHistory: IAuditHistory): void {
    this.auditHistory = auditHistory;
    this.auditHistoryFormService.resetForm(this.editForm, auditHistory);
  }
}
