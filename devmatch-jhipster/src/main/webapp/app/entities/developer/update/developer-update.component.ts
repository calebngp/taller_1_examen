import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ITechnology } from 'app/entities/technology/technology.model';
import { TechnologyService } from 'app/entities/technology/service/technology.service';
import { DeveloperService } from '../service/developer.service';
import { IDeveloper } from '../developer.model';
import { DeveloperFormGroup, DeveloperFormService } from './developer-form.service';

@Component({
  selector: 'jhi-developer-update',
  templateUrl: './developer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DeveloperUpdateComponent implements OnInit {
  isSaving = false;
  developer: IDeveloper | null = null;

  technologiesSharedCollection: ITechnology[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected developerService = inject(DeveloperService);
  protected developerFormService = inject(DeveloperFormService);
  protected technologyService = inject(TechnologyService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DeveloperFormGroup = this.developerFormService.createDeveloperFormGroup();

  compareTechnology = (o1: ITechnology | null, o2: ITechnology | null): boolean => this.technologyService.compareTechnology(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ developer }) => {
      this.developer = developer;
      if (developer) {
        this.updateForm(developer);
      }

      this.loadRelationshipsOptions();
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
    const developer = this.developerFormService.getDeveloper(this.editForm);
    if (developer.id !== null) {
      this.subscribeToSaveResponse(this.developerService.update(developer));
    } else {
      this.subscribeToSaveResponse(this.developerService.create(developer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeveloper>>): void {
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

  protected updateForm(developer: IDeveloper): void {
    this.developer = developer;
    this.developerFormService.resetForm(this.editForm, developer);

    this.technologiesSharedCollection = this.technologyService.addTechnologyToCollectionIfMissing<ITechnology>(
      this.technologiesSharedCollection,
      ...(developer.skills ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.technologyService
      .query()
      .pipe(map((res: HttpResponse<ITechnology[]>) => res.body ?? []))
      .pipe(
        map((technologies: ITechnology[]) =>
          this.technologyService.addTechnologyToCollectionIfMissing<ITechnology>(technologies, ...(this.developer?.skills ?? [])),
        ),
      )
      .subscribe((technologies: ITechnology[]) => (this.technologiesSharedCollection = technologies));
  }
}
