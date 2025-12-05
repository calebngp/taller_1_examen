import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDeveloper } from '../developer.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../developer.test-samples';

import { DeveloperService, RestDeveloper } from './developer.service';

const requireRestSample: RestDeveloper = {
  ...sampleWithRequiredData,
  fechaCreacion: sampleWithRequiredData.fechaCreacion?.toJSON(),
  fechaModificacion: sampleWithRequiredData.fechaModificacion?.toJSON(),
};

describe('Developer Service', () => {
  let service: DeveloperService;
  let httpMock: HttpTestingController;
  let expectedResult: IDeveloper | IDeveloper[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DeveloperService);
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

    it('should create a Developer', () => {
      const developer = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(developer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Developer', () => {
      const developer = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(developer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Developer', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Developer', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Developer', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDeveloperToCollectionIfMissing', () => {
      it('should add a Developer to an empty array', () => {
        const developer: IDeveloper = sampleWithRequiredData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], developer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(developer);
      });

      it('should not add a Developer to an array that contains it', () => {
        const developer: IDeveloper = sampleWithRequiredData;
        const developerCollection: IDeveloper[] = [
          {
            ...developer,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, developer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Developer to an array that doesn't contain it", () => {
        const developer: IDeveloper = sampleWithRequiredData;
        const developerCollection: IDeveloper[] = [sampleWithPartialData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, developer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(developer);
      });

      it('should add only unique Developer to an array', () => {
        const developerArray: IDeveloper[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const developerCollection: IDeveloper[] = [sampleWithRequiredData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, ...developerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const developer: IDeveloper = sampleWithRequiredData;
        const developer2: IDeveloper = sampleWithPartialData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], developer, developer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(developer);
        expect(expectedResult).toContain(developer2);
      });

      it('should accept null and undefined values', () => {
        const developer: IDeveloper = sampleWithRequiredData;
        expectedResult = service.addDeveloperToCollectionIfMissing([], null, developer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(developer);
      });

      it('should return initial array if no Developer is added', () => {
        const developerCollection: IDeveloper[] = [sampleWithRequiredData];
        expectedResult = service.addDeveloperToCollectionIfMissing(developerCollection, undefined, null);
        expect(expectedResult).toEqual(developerCollection);
      });
    });

    describe('compareDeveloper', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDeveloper(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 22287 };
        const entity2 = null;

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 22287 };
        const entity2 = { id: 11933 };

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 22287 };
        const entity2 = { id: 22287 };

        const compareResult1 = service.compareDeveloper(entity1, entity2);
        const compareResult2 = service.compareDeveloper(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
