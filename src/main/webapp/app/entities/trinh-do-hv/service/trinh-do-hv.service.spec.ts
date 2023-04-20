import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrinhDoHV } from '../trinh-do-hv.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../trinh-do-hv.test-samples';

import { TrinhDoHVService } from './trinh-do-hv.service';

const requireRestSample: ITrinhDoHV = {
  ...sampleWithRequiredData,
};

describe('TrinhDoHV Service', () => {
  let service: TrinhDoHVService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrinhDoHV | ITrinhDoHV[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrinhDoHVService);
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

    it('should create a TrinhDoHV', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trinhDoHV = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trinhDoHV).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrinhDoHV', () => {
      const trinhDoHV = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trinhDoHV).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrinhDoHV', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrinhDoHV', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrinhDoHV', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrinhDoHVToCollectionIfMissing', () => {
      it('should add a TrinhDoHV to an empty array', () => {
        const trinhDoHV: ITrinhDoHV = sampleWithRequiredData;
        expectedResult = service.addTrinhDoHVToCollectionIfMissing([], trinhDoHV);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trinhDoHV);
      });

      it('should not add a TrinhDoHV to an array that contains it', () => {
        const trinhDoHV: ITrinhDoHV = sampleWithRequiredData;
        const trinhDoHVCollection: ITrinhDoHV[] = [
          {
            ...trinhDoHV,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrinhDoHVToCollectionIfMissing(trinhDoHVCollection, trinhDoHV);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrinhDoHV to an array that doesn't contain it", () => {
        const trinhDoHV: ITrinhDoHV = sampleWithRequiredData;
        const trinhDoHVCollection: ITrinhDoHV[] = [sampleWithPartialData];
        expectedResult = service.addTrinhDoHVToCollectionIfMissing(trinhDoHVCollection, trinhDoHV);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trinhDoHV);
      });

      it('should add only unique TrinhDoHV to an array', () => {
        const trinhDoHVArray: ITrinhDoHV[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trinhDoHVCollection: ITrinhDoHV[] = [sampleWithRequiredData];
        expectedResult = service.addTrinhDoHVToCollectionIfMissing(trinhDoHVCollection, ...trinhDoHVArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trinhDoHV: ITrinhDoHV = sampleWithRequiredData;
        const trinhDoHV2: ITrinhDoHV = sampleWithPartialData;
        expectedResult = service.addTrinhDoHVToCollectionIfMissing([], trinhDoHV, trinhDoHV2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trinhDoHV);
        expect(expectedResult).toContain(trinhDoHV2);
      });

      it('should accept null and undefined values', () => {
        const trinhDoHV: ITrinhDoHV = sampleWithRequiredData;
        expectedResult = service.addTrinhDoHVToCollectionIfMissing([], null, trinhDoHV, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trinhDoHV);
      });

      it('should return initial array if no TrinhDoHV is added', () => {
        const trinhDoHVCollection: ITrinhDoHV[] = [sampleWithRequiredData];
        expectedResult = service.addTrinhDoHVToCollectionIfMissing(trinhDoHVCollection, undefined, null);
        expect(expectedResult).toEqual(trinhDoHVCollection);
      });
    });

    describe('compareTrinhDoHV', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrinhDoHV(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrinhDoHV(entity1, entity2);
        const compareResult2 = service.compareTrinhDoHV(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrinhDoHV(entity1, entity2);
        const compareResult2 = service.compareTrinhDoHV(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrinhDoHV(entity1, entity2);
        const compareResult2 = service.compareTrinhDoHV(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
