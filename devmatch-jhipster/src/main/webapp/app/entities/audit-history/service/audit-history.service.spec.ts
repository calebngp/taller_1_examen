import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAuditHistory } from '../audit-history.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../audit-history.test-samples';

import { AuditHistoryService, RestAuditHistory } from './audit-history.service';

const requireRestSample: RestAuditHistory = {
  ...sampleWithRequiredData,
  fechaModificacion: sampleWithRequiredData.fechaModificacion?.toJSON(),
};

describe('AuditHistory Service', () => {
  let service: AuditHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IAuditHistory | IAuditHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AuditHistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AuditHistory', () => {
      const auditHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(auditHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuditHistory', () => {
      const auditHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(auditHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuditHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuditHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AuditHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAuditHistoryToCollectionIfMissing', () => {
      it('should add a AuditHistory to an empty array', () => {
        const auditHistory: IAuditHistory = sampleWithRequiredData;
        expectedResult = service.addAuditHistoryToCollectionIfMissing([], auditHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditHistory);
      });

      it('should not add a AuditHistory to an array that contains it', () => {
        const auditHistory: IAuditHistory = sampleWithRequiredData;
        const auditHistoryCollection: IAuditHistory[] = [
          {
            ...auditHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAuditHistoryToCollectionIfMissing(auditHistoryCollection, auditHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuditHistory to an array that doesn't contain it", () => {
        const auditHistory: IAuditHistory = sampleWithRequiredData;
        const auditHistoryCollection: IAuditHistory[] = [sampleWithPartialData];
        expectedResult = service.addAuditHistoryToCollectionIfMissing(auditHistoryCollection, auditHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditHistory);
      });

      it('should add only unique AuditHistory to an array', () => {
        const auditHistoryArray: IAuditHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const auditHistoryCollection: IAuditHistory[] = [sampleWithRequiredData];
        expectedResult = service.addAuditHistoryToCollectionIfMissing(auditHistoryCollection, ...auditHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const auditHistory: IAuditHistory = sampleWithRequiredData;
        const auditHistory2: IAuditHistory = sampleWithPartialData;
        expectedResult = service.addAuditHistoryToCollectionIfMissing([], auditHistory, auditHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditHistory);
        expect(expectedResult).toContain(auditHistory2);
      });

      it('should accept null and undefined values', () => {
        const auditHistory: IAuditHistory = sampleWithRequiredData;
        expectedResult = service.addAuditHistoryToCollectionIfMissing([], null, auditHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditHistory);
      });

      it('should return initial array if no AuditHistory is added', () => {
        const auditHistoryCollection: IAuditHistory[] = [sampleWithRequiredData];
        expectedResult = service.addAuditHistoryToCollectionIfMissing(auditHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(auditHistoryCollection);
      });
    });

    describe('compareAuditHistory', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAuditHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 12697 };
        const entity2 = null;

        const compareResult1 = service.compareAuditHistory(entity1, entity2);
        const compareResult2 = service.compareAuditHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 12697 };
        const entity2 = { id: 28908 };

        const compareResult1 = service.compareAuditHistory(entity1, entity2);
        const compareResult2 = service.compareAuditHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 12697 };
        const entity2 = { id: 12697 };

        const compareResult1 = service.compareAuditHistory(entity1, entity2);
        const compareResult2 = service.compareAuditHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
