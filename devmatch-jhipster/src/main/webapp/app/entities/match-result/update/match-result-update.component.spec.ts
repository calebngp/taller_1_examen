import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { IMatchResult } from '../match-result.model';
import { MatchResultService } from '../service/match-result.service';
import { MatchResultFormService } from './match-result-form.service';

import { MatchResultUpdateComponent } from './match-result-update.component';

describe('MatchResult Management Update Component', () => {
  let comp: MatchResultUpdateComponent;
  let fixture: ComponentFixture<MatchResultUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matchResultFormService: MatchResultFormService;
  let matchResultService: MatchResultService;
  let projectService: ProjectService;
  let developerService: DeveloperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MatchResultUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MatchResultUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatchResultUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matchResultFormService = TestBed.inject(MatchResultFormService);
    matchResultService = TestBed.inject(MatchResultService);
    projectService = TestBed.inject(ProjectService);
    developerService = TestBed.inject(DeveloperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Project query and add missing value', () => {
      const matchResult: IMatchResult = { id: 31578 };
      const project: IProject = { id: 10300 };
      matchResult.project = project;

      const projectCollection: IProject[] = [{ id: 10300 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matchResult });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('should call Developer query and add missing value', () => {
      const matchResult: IMatchResult = { id: 31578 };
      const developer: IDeveloper = { id: 22287 };
      matchResult.developer = developer;

      const developerCollection: IDeveloper[] = [{ id: 22287 }];
      jest.spyOn(developerService, 'query').mockReturnValue(of(new HttpResponse({ body: developerCollection })));
      const additionalDevelopers = [developer];
      const expectedCollection: IDeveloper[] = [...additionalDevelopers, ...developerCollection];
      jest.spyOn(developerService, 'addDeveloperToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matchResult });
      comp.ngOnInit();

      expect(developerService.query).toHaveBeenCalled();
      expect(developerService.addDeveloperToCollectionIfMissing).toHaveBeenCalledWith(
        developerCollection,
        ...additionalDevelopers.map(expect.objectContaining),
      );
      expect(comp.developersSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const matchResult: IMatchResult = { id: 31578 };
      const project: IProject = { id: 10300 };
      matchResult.project = project;
      const developer: IDeveloper = { id: 22287 };
      matchResult.developer = developer;

      activatedRoute.data = of({ matchResult });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContainEqual(project);
      expect(comp.developersSharedCollection).toContainEqual(developer);
      expect(comp.matchResult).toEqual(matchResult);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatchResult>>();
      const matchResult = { id: 4914 };
      jest.spyOn(matchResultFormService, 'getMatchResult').mockReturnValue(matchResult);
      jest.spyOn(matchResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matchResult }));
      saveSubject.complete();

      // THEN
      expect(matchResultFormService.getMatchResult).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(matchResultService.update).toHaveBeenCalledWith(expect.objectContaining(matchResult));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatchResult>>();
      const matchResult = { id: 4914 };
      jest.spyOn(matchResultFormService, 'getMatchResult').mockReturnValue({ id: null });
      jest.spyOn(matchResultService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchResult: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matchResult }));
      saveSubject.complete();

      // THEN
      expect(matchResultFormService.getMatchResult).toHaveBeenCalled();
      expect(matchResultService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMatchResult>>();
      const matchResult = { id: 4914 };
      jest.spyOn(matchResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matchResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matchResultService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProject', () => {
      it('should forward to projectService', () => {
        const entity = { id: 10300 };
        const entity2 = { id: 3319 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDeveloper', () => {
      it('should forward to developerService', () => {
        const entity = { id: 22287 };
        const entity2 = { id: 11933 };
        jest.spyOn(developerService, 'compareDeveloper');
        comp.compareDeveloper(entity, entity2);
        expect(developerService.compareDeveloper).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
