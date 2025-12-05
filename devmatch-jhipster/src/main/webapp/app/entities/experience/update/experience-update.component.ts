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
import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { ExperienceService } from '../service/experience.service';
import { IExperience } from '../experience.model';
import { ExperienceFormGroup, ExperienceFormService } from './experience-form.service';

@Component({
  selector: 'jhi-experience-update',
  templateUrl: './experience-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ExperienceUpdateComponent implements OnInit {
  isSaving = false;
  experience: IExperience | null = null;

  developersSharedCollection: IDeveloper[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected experienceService = inject(ExperienceService);
  protected experienceFormService = inject(ExperienceFormService);
  protected developerService = inject(DeveloperService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ExperienceFormGroup = this.experienceFormService.createExperienceFormGroup();

  compareDeveloper = (o1: IDeveloper | null, o2: IDeveloper | null): boolean => this.developerService.compareDeveloper(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ experience }) => {
      this.experience = experience;
      if (experience) {
        this.updateForm(experience);
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
    const experience = this.experienceFormService.getExperience(this.editForm);
    if (experience.id !== null) {
      this.subscribeToSaveResponse(this.experienceService.update(experience));
    } else {
      this.subscribeToSaveResponse(this.experienceService.create(experience));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperience>>): void {
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

  protected updateForm(experience: IExperience): void {
    this.experience = experience;
    this.experienceFormService.resetForm(this.editForm, experience);

    this.developersSharedCollection = this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(
      this.developersSharedCollection,
      experience.developer,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.developerService
      .query()
      .pipe(map((res: HttpResponse<IDeveloper[]>) => res.body ?? []))
      .pipe(
        map((developers: IDeveloper[]) =>
          this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(developers, this.experience?.developer),
        ),
      )
      .subscribe((developers: IDeveloper[]) => (this.developersSharedCollection = developers));
  }
}
