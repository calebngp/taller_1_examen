import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AuditHistoryService } from '../service/audit-history.service';
import { IAuditHistory } from '../audit-history.model';
import { AuditHistoryFormService } from './audit-history-form.service';

import { AuditHistoryUpdateComponent } from './audit-history-update.component';

describe('AuditHistory Management Update Component', () => {
  let comp: AuditHistoryUpdateComponent;
  let fixture: ComponentFixture<AuditHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let auditHistoryFormService: AuditHistoryFormService;
  let auditHistoryService: AuditHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AuditHistoryUpdateComponent],
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
      .overrideTemplate(AuditHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuditHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    auditHistoryFormService = TestBed.inject(AuditHistoryFormService);
    auditHistoryService = TestBed.inject(AuditHistoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const auditHistory: IAuditHistory = { id: 28908 };

      activatedRoute.data = of({ auditHistory });
      comp.ngOnInit();

      expect(comp.auditHistory).toEqual(auditHistory);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditHistory>>();
      const auditHistory = { id: 12697 };
      jest.spyOn(auditHistoryFormService, 'getAuditHistory').mockReturnValue(auditHistory);
      jest.spyOn(auditHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditHistory }));
      saveSubject.complete();

      // THEN
      expect(auditHistoryFormService.getAuditHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(auditHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(auditHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditHistory>>();
      const auditHistory = { id: 12697 };
      jest.spyOn(auditHistoryFormService, 'getAuditHistory').mockReturnValue({ id: null });
      jest.spyOn(auditHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditHistory }));
      saveSubject.complete();

      // THEN
      expect(auditHistoryFormService.getAuditHistory).toHaveBeenCalled();
      expect(auditHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditHistory>>();
      const auditHistory = { id: 12697 };
      jest.spyOn(auditHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(auditHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
