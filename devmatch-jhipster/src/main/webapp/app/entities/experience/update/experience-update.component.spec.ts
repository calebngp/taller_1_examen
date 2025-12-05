import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDeveloper } from 'app/entities/developer/developer.model';
import { DeveloperService } from 'app/entities/developer/service/developer.service';
import { ExperienceService } from '../service/experience.service';
import { IExperience } from '../experience.model';
import { ExperienceFormService } from './experience-form.service';

import { ExperienceUpdateComponent } from './experience-update.component';

describe('Experience Management Update Component', () => {
  let comp: ExperienceUpdateComponent;
  let fixture: ComponentFixture<ExperienceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let experienceFormService: ExperienceFormService;
  let experienceService: ExperienceService;
  let developerService: DeveloperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ExperienceUpdateComponent],
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
      .overrideTemplate(ExperienceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExperienceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    experienceFormService = TestBed.inject(ExperienceFormService);
    experienceService = TestBed.inject(ExperienceService);
    developerService = TestBed.inject(DeveloperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Developer query and add missing value', () => {
      const experience: IExperience = { id: 15015 };
      const developer: IDeveloper = { id: 22287 };
      experience.developer = developer;

      const developerCollection: IDeveloper[] = [{ id: 22287 }];
      jest.spyOn(developerService, 'query').mockReturnValue(of(new HttpResponse({ body: developerCollection })));
      const additionalDevelopers = [developer];
      const expectedCollection: IDeveloper[] = [...additionalDevelopers, ...developerCollection];
      jest.spyOn(developerService, 'addDeveloperToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ experience });
      comp.ngOnInit();

      expect(developerService.query).toHaveBeenCalled();
      expect(developerService.addDeveloperToCollectionIfMissing).toHaveBeenCalledWith(
        developerCollection,
        ...additionalDevelopers.map(expect.objectContaining),
      );
      expect(comp.developersSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const experience: IExperience = { id: 15015 };
      const developer: IDeveloper = { id: 22287 };
      experience.developer = developer;

      activatedRoute.data = of({ experience });
      comp.ngOnInit();

      expect(comp.developersSharedCollection).toContainEqual(developer);
      expect(comp.experience).toEqual(experience);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExperience>>();
      const experience = { id: 18889 };
      jest.spyOn(experienceFormService, 'getExperience').mockReturnValue(experience);
      jest.spyOn(experienceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ experience });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: experience }));
      saveSubject.complete();

      // THEN
      expect(experienceFormService.getExperience).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(experienceService.update).toHaveBeenCalledWith(expect.objectContaining(experience));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExperience>>();
      const experience = { id: 18889 };
      jest.spyOn(experienceFormService, 'getExperience').mockReturnValue({ id: null });
      jest.spyOn(experienceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ experience: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: experience }));
      saveSubject.complete();

      // THEN
      expect(experienceFormService.getExperience).toHaveBeenCalled();
      expect(experienceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExperience>>();
      const experience = { id: 18889 };
      jest.spyOn(experienceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ experience });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(experienceService.update).toHaveBeenCalled();
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
  });
});
