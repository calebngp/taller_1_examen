import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../audit-history.test-samples';

import { AuditHistoryFormService } from './audit-history-form.service';

describe('AuditHistory Form Service', () => {
  let service: AuditHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuditHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createAuditHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuditHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            entityType: expect.any(Object),
            entityId: expect.any(Object),
            fieldName: expect.any(Object),
            oldValue: expect.any(Object),
            newValue: expect.any(Object),
            usuario: expect.any(Object),
            fechaModificacion: expect.any(Object),
          }),
        );
      });

      it('passing IAuditHistory should create a new form with FormGroup', () => {
        const formGroup = service.createAuditHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            entityType: expect.any(Object),
            entityId: expect.any(Object),
            fieldName: expect.any(Object),
            oldValue: expect.any(Object),
            newValue: expect.any(Object),
            usuario: expect.any(Object),
            fechaModificacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getAuditHistory', () => {
      it('should return NewAuditHistory for default AuditHistory initial value', () => {
        const formGroup = service.createAuditHistoryFormGroup(sampleWithNewData);

        const auditHistory = service.getAuditHistory(formGroup) as any;

        expect(auditHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuditHistory for empty AuditHistory initial value', () => {
        const formGroup = service.createAuditHistoryFormGroup();

        const auditHistory = service.getAuditHistory(formGroup) as any;

        expect(auditHistory).toMatchObject({});
      });

      it('should return IAuditHistory', () => {
        const formGroup = service.createAuditHistoryFormGroup(sampleWithRequiredData);

        const auditHistory = service.getAuditHistory(formGroup) as any;

        expect(auditHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAuditHistory should not enable id FormControl', () => {
        const formGroup = service.createAuditHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAuditHistory should disable id FormControl', () => {
        const formGroup = service.createAuditHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
