import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../match-result.test-samples';

import { MatchResultFormService } from './match-result-form.service';

describe('MatchResult Form Service', () => {
  let service: MatchResultFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchResultFormService);
  });

  describe('Service methods', () => {
    describe('createMatchResultFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMatchResultFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            technicalMatch: expect.any(Object),
            aiTechnicalAffinity: expect.any(Object),
            aiMotivationalAffinity: expect.any(Object),
            aiExperienceRelevance: expect.any(Object),
            aiComment: expect.any(Object),
            createdAt: expect.any(Object),
            project: expect.any(Object),
            developer: expect.any(Object),
          }),
        );
      });

      it('passing IMatchResult should create a new form with FormGroup', () => {
        const formGroup = service.createMatchResultFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            technicalMatch: expect.any(Object),
            aiTechnicalAffinity: expect.any(Object),
            aiMotivationalAffinity: expect.any(Object),
            aiExperienceRelevance: expect.any(Object),
            aiComment: expect.any(Object),
            createdAt: expect.any(Object),
            project: expect.any(Object),
            developer: expect.any(Object),
          }),
        );
      });
    });

    describe('getMatchResult', () => {
      it('should return NewMatchResult for default MatchResult initial value', () => {
        const formGroup = service.createMatchResultFormGroup(sampleWithNewData);

        const matchResult = service.getMatchResult(formGroup) as any;

        expect(matchResult).toMatchObject(sampleWithNewData);
      });

      it('should return NewMatchResult for empty MatchResult initial value', () => {
        const formGroup = service.createMatchResultFormGroup();

        const matchResult = service.getMatchResult(formGroup) as any;

        expect(matchResult).toMatchObject({});
      });

      it('should return IMatchResult', () => {
        const formGroup = service.createMatchResultFormGroup(sampleWithRequiredData);

        const matchResult = service.getMatchResult(formGroup) as any;

        expect(matchResult).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMatchResult should not enable id FormControl', () => {
        const formGroup = service.createMatchResultFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMatchResult should disable id FormControl', () => {
        const formGroup = service.createMatchResultFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
