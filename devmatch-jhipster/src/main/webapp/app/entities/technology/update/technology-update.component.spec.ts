import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { ITechnology } from '../technology.model';
import { TechnologyService } from '../service/technology.service';
import { TechnologyFormService } from './technology-form.service';

import { TechnologyUpdateComponent } from './technology-update.component';

describe('Technology Management Update Component', () => {
  let comp: TechnologyUpdateComponent;
  let fixture: ComponentFixture<TechnologyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let technologyFormService: TechnologyFormService;
  let technologyService: TechnologyService;
  let developerService: DeveloperService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TechnologyUpdateComponent],
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
      .overrideTemplate(TechnologyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TechnologyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    technologyFormService = TestBed.inject(TechnologyFormService);
    technologyService = TestBed.inject(TechnologyService);
    developerService = TestBed.inject(DeveloperService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Developer query and add missing value', () => {
      const technology: ITechnology = { id: 11997 };
      const developers: IDeveloper[] = [{ id: 22287 }];
      technology.developers = developers;

      const developerCollection: IDeveloper[] = [{ id: 22287 }];
      jest.spyOn(developerService, 'query').mockReturnValue(of(new HttpResponse({ body: developerCollection })));
      const additionalDevelopers = [...developers];
      const expectedCollection: IDeveloper[] = [...additionalDevelopers, ...developerCollection];
      jest.spyOn(developerService, 'addDeveloperToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ technology });
      comp.ngOnInit();

      expect(developerService.query).toHaveBeenCalled();
      expect(developerService.addDeveloperToCollectionIfMissing).toHaveBeenCalledWith(
        developerCollection,
        ...additionalDevelopers.map(expect.objectContaining),
      );
      expect(comp.developersSharedCollection).toEqual(expectedCollection);
    });

    it('should call Project query and add missing value', () => {
      const technology: ITechnology = { id: 11997 };
      const projects: IProject[] = [{ id: 10300 }];
      technology.projects = projects;

      const projectCollection: IProject[] = [{ id: 10300 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [...projects];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ technology });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const technology: ITechnology = { id: 11997 };
      const developers: IDeveloper = { id: 22287 };
      technology.developers = [developers];
      const projects: IProject = { id: 10300 };
      technology.projects = [projects];

      activatedRoute.data = of({ technology });
      comp.ngOnInit();

      expect(comp.developersSharedCollection).toContainEqual(developers);
      expect(comp.projectsSharedCollection).toContainEqual(projects);
      expect(comp.technology).toEqual(technology);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITechnology>>();
      const technology = { id: 1643 };
      jest.spyOn(technologyFormService, 'getTechnology').mockReturnValue(technology);
      jest.spyOn(technologyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ technology });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: technology }));
      saveSubject.complete();

      // THEN
      expect(technologyFormService.getTechnology).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(technologyService.update).toHaveBeenCalledWith(expect.objectContaining(technology));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITechnology>>();
      const technology = { id: 1643 };
      jest.spyOn(technologyFormService, 'getTechnology').mockReturnValue({ id: null });
      jest.spyOn(technologyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ technology: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: technology }));
      saveSubject.complete();

      // THEN
      expect(technologyFormService.getTechnology).toHaveBeenCalled();
      expect(technologyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITechnology>>();
      const technology = { id: 1643 };
      jest.spyOn(technologyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ technology });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(technologyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDeveloper', () => {
      it('should forward to developerService', () => {
        const entity = { id: 22287 };
        const entity2 = { id: 11933 };
        jest.spyOn(developerService, 'compareDeveloper');
        comp.compareDeveloper(entity, entity2);
        expect(developerService.compareDeveloper).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProject', () => {
      it('should forward to projectService', () => {
        const entity = { id: 10300 };
        const entity2 = { id: 3319 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
