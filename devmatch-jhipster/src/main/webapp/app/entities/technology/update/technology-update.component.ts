import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { TechnologyService } from '../service/technology.service';
import { ITechnology } from '../technology.model';
import { TechnologyFormGroup, TechnologyFormService } from './technology-form.service';

@Component({
  selector: 'jhi-technology-update',
  templateUrl: './technology-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TechnologyUpdateComponent implements OnInit {
  isSaving = false;
  technology: ITechnology | null = null;

  developersSharedCollection: IDeveloper[] = [];
  projectsSharedCollection: IProject[] = [];

  protected technologyService = inject(TechnologyService);
  protected technologyFormService = inject(TechnologyFormService);
  protected developerService = inject(DeveloperService);
  protected projectService = inject(ProjectService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TechnologyFormGroup = this.technologyFormService.createTechnologyFormGroup();

  compareDeveloper = (o1: IDeveloper | null, o2: IDeveloper | null): boolean => this.developerService.compareDeveloper(o1, o2);

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ technology }) => {
      this.technology = technology;
      if (technology) {
        this.updateForm(technology);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const technology = this.technologyFormService.getTechnology(this.editForm);
    if (technology.id !== null) {
      this.subscribeToSaveResponse(this.technologyService.update(technology));
    } else {
      this.subscribeToSaveResponse(this.technologyService.create(technology));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITechnology>>): void {
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

  protected updateForm(technology: ITechnology): void {
    this.technology = technology;
    this.technologyFormService.resetForm(this.editForm, technology);

    this.developersSharedCollection = this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(
      this.developersSharedCollection,
      ...(technology.developers ?? []),
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      ...(technology.projects ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.developerService
      .query()
      .pipe(map((res: HttpResponse<IDeveloper[]>) => res.body ?? []))
      .pipe(
        map((developers: IDeveloper[]) =>
          this.developerService.addDeveloperToCollectionIfMissing<IDeveloper>(developers, ...(this.technology?.developers ?? [])),
        ),
      )
      .subscribe((developers: IDeveloper[]) => (this.developersSharedCollection = developers));

    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, ...(this.technology?.projects ?? [])),
        ),
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}
