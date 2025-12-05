import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMatchResult } from '../match-result.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../match-result.test-samples';

import { MatchResultService } from './match-result.service';

const requireRestSample: IMatchResult = {
  ...sampleWithRequiredData,
};

describe('MatchResult Service', () => {
  let service: MatchResultService;
  let httpMock: HttpTestingController;
  let expectedResult: IMatchResult | IMatchResult[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MatchResultService);
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

    it('should create a MatchResult', () => {
      const matchResult = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(matchResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MatchResult', () => {
      const matchResult = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(matchResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MatchResult', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MatchResult', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MatchResult', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMatchResultToCollectionIfMissing', () => {
      it('should add a MatchResult to an empty array', () => {
        const matchResult: IMatchResult = sampleWithRequiredData;
        expectedResult = service.addMatchResultToCollectionIfMissing([], matchResult);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matchResult);
      });

      it('should not add a MatchResult to an array that contains it', () => {
        const matchResult: IMatchResult = sampleWithRequiredData;
        const matchResultCollection: IMatchResult[] = [
          {
            ...matchResult,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMatchResultToCollectionIfMissing(matchResultCollection, matchResult);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MatchResult to an array that doesn't contain it", () => {
        const matchResult: IMatchResult = sampleWithRequiredData;
        const matchResultCollection: IMatchResult[] = [sampleWithPartialData];
        expectedResult = service.addMatchResultToCollectionIfMissing(matchResultCollection, matchResult);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matchResult);
      });

      it('should add only unique MatchResult to an array', () => {
        const matchResultArray: IMatchResult[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const matchResultCollection: IMatchResult[] = [sampleWithRequiredData];
        expectedResult = service.addMatchResultToCollectionIfMissing(matchResultCollection, ...matchResultArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const matchResult: IMatchResult = sampleWithRequiredData;
        const matchResult2: IMatchResult = sampleWithPartialData;
        expectedResult = service.addMatchResultToCollectionIfMissing([], matchResult, matchResult2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matchResult);
        expect(expectedResult).toContain(matchResult2);
      });

      it('should accept null and undefined values', () => {
        const matchResult: IMatchResult = sampleWithRequiredData;
        expectedResult = service.addMatchResultToCollectionIfMissing([], null, matchResult, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matchResult);
      });

      it('should return initial array if no MatchResult is added', () => {
        const matchResultCollection: IMatchResult[] = [sampleWithRequiredData];
        expectedResult = service.addMatchResultToCollectionIfMissing(matchResultCollection, undefined, null);
        expect(expectedResult).toEqual(matchResultCollection);
      });
    });

    describe('compareMatchResult', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMatchResult(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 4914 };
        const entity2 = null;

        const compareResult1 = service.compareMatchResult(entity1, entity2);
        const compareResult2 = service.compareMatchResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 4914 };
        const entity2 = { id: 31578 };

        const compareResult1 = service.compareMatchResult(entity1, entity2);
        const compareResult2 = service.compareMatchResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 4914 };
        const entity2 = { id: 4914 };

        const compareResult1 = service.compareMatchResult(entity1, entity2);
        const compareResult2 = service.compareMatchResult(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
