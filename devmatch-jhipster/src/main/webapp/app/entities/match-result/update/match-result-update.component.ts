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
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { MatchResultService } from '../service/match-result.service';
import { IMatchResult } from '../match-result.model';
import { MatchResultFormGroup, MatchResultFormService } from './match-result-form.service';

@Component({
  selector: 'jhi-match-result-update',
  templateUrl: './match-result-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MatchResultUpdateComponent implements OnInit {
  isSaving = false;
  matchResult: IMatchResult | null = null;

  projectsSharedCollection: IProject[] = [];
  developersSharedCollection: IDeveloper[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected matchResultService = inject(MatchResultService);
  protected matchResultFormService = inject(MatchResultFormService);
  protected projectService = inject(ProjectService);
  protected developerService = inject(DeveloperService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MatchResultFormGroup = this.matchResultFormService.createMatchResultFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDeveloper = (o1: IDeveloper | null, o2: IDeveloper | null): boolean => this.developerService.compareDeveloper(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchResult }) => {
      this.matchResult = matchResult;
      if (matchResult) {
        this.updateForm(matchResult);
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
    const matchResult = this.matchResultFormService.getMatchResult(this.editForm);
    if (matchResult.id !== null) {
      this.subscribeToSaveResponse(this.matchResultService.update(matchResult));
    } else {
      this.subscribeToSaveResponse(this.matchResultService.create(matchResult));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatchResult>>): void {
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

  protected updateForm(matchResult: IMatchResult): void {
    this.matchResult = matchResult;
    this.matchResultFormService.resetForm(this.editForm, matchResult);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      matchResult.project,
    );
    this.developersSharedCollection = this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(
      this.developersSharedCollection,
      matchResult.developer,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.matchResult?.project)),
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.developerService
      .query()
      .pipe(map((res: HttpResponse<IDeveloper[]>) => res.body ?? []))
      .pipe(
        map((developers: IDeveloper[]) =>
          this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(developers, this.matchResult?.developer),
        ),
      )
      .subscribe((developers: IDeveloper[]) => (this.developersSharedCollection = developers));
  }
}
