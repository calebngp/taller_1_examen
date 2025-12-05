import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ITechnology } from 'app/entities/technology/technology.model';
import { TechnologyService } from 'app/entities/technology/service/technology.service';
import { DeveloperService } from '../service/developer.service';
import { IDeveloper } from '../developer.model';
import { DeveloperFormService } from './developer-form.service';

import { DeveloperUpdateComponent } from './developer-update.component';

describe('Developer Management Update Component', () => {
  let comp: DeveloperUpdateComponent;
  let fixture: ComponentFixture<DeveloperUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let developerFormService: DeveloperFormService;
  let developerService: DeveloperService;
  let technologyService: TechnologyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DeveloperUpdateComponent],
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
      .overrideTemplate(DeveloperUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeveloperUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    developerFormService = TestBed.inject(DeveloperFormService);
    developerService = TestBed.inject(DeveloperService);
    technologyService = TestBed.inject(TechnologyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Technology query and add missing value', () => {
      const developer: IDeveloper = { id: 11933 };
      const skills: ITechnology[] = [{ id: 1643 }];
      developer.skills = skills;

      const technologyCollection: ITechnology[] = [{ id: 1643 }];
      jest.spyOn(technologyService, 'query').mockReturnValue(of(new HttpResponse({ body: technologyCollection })));
      const additionalTechnologies = [...skills];
      const expectedCollection: ITechnology[] = [...additionalTechnologies, ...technologyCollection];
      jest.spyOn(technologyService, 'addTechnologyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      expect(technologyService.query).toHaveBeenCalled();
      expect(technologyService.addTechnologyToCollectionIfMissing).toHaveBeenCalledWith(
        technologyCollection,
        ...additionalTechnologies.map(expect.objectContaining),
      );
      expect(comp.technologiesSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const developer: IDeveloper = { id: 11933 };
      const skills: ITechnology = { id: 1643 };
      developer.skills = [skills];

      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      expect(comp.technologiesSharedCollection).toContainEqual(skills);
      expect(comp.developer).toEqual(developer);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 22287 };
      jest.spyOn(developerFormService, 'getDeveloper').mockReturnValue(developer);
      jest.spyOn(developerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: developer }));
      saveSubject.complete();

      // THEN
      expect(developerFormService.getDeveloper).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(developerService.update).toHaveBeenCalledWith(expect.objectContaining(developer));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 22287 };
      jest.spyOn(developerFormService, 'getDeveloper').mockReturnValue({ id: null });
      jest.spyOn(developerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: developer }));
      saveSubject.complete();

      // THEN
      expect(developerFormService.getDeveloper).toHaveBeenCalled();
      expect(developerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 22287 };
      jest.spyOn(developerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(developerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTechnology', () => {
      it('should forward to technologyService', () => {
        const entity = { id: 1643 };
        const entity2 = { id: 11997 };
        jest.spyOn(technologyService, 'compareTechnology');
        comp.compareTechnology(entity, entity2);
        expect(technologyService.compareTechnology).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
