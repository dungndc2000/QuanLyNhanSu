import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChuyenMon } from '../chuyen-mon.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../chuyen-mon.test-samples';

import { ChuyenMonService } from './chuyen-mon.service';

const requireRestSample: IChuyenMon = {
  ...sampleWithRequiredData,
};

describe('ChuyenMon Service', () => {
  let service: ChuyenMonService;
  let httpMock: HttpTestingController;
  let expectedResult: IChuyenMon | IChuyenMon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChuyenMonService);
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

    it('should create a ChuyenMon', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const chuyenMon = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(chuyenMon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChuyenMon', () => {
      const chuyenMon = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(chuyenMon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ChuyenMon', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ChuyenMon', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ChuyenMon', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addChuyenMonToCollectionIfMissing', () => {
      it('should add a ChuyenMon to an empty array', () => {
        const chuyenMon: IChuyenMon = sampleWithRequiredData;
        expectedResult = service.addChuyenMonToCollectionIfMissing([], chuyenMon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chuyenMon);
      });

      it('should not add a ChuyenMon to an array that contains it', () => {
        const chuyenMon: IChuyenMon = sampleWithRequiredData;
        const chuyenMonCollection: IChuyenMon[] = [
          {
            ...chuyenMon,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addChuyenMonToCollectionIfMissing(chuyenMonCollection, chuyenMon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChuyenMon to an array that doesn't contain it", () => {
        const chuyenMon: IChuyenMon = sampleWithRequiredData;
        const chuyenMonCollection: IChuyenMon[] = [sampleWithPartialData];
        expectedResult = service.addChuyenMonToCollectionIfMissing(chuyenMonCollection, chuyenMon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chuyenMon);
      });

      it('should add only unique ChuyenMon to an array', () => {
        const chuyenMonArray: IChuyenMon[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const chuyenMonCollection: IChuyenMon[] = [sampleWithRequiredData];
        expectedResult = service.addChuyenMonToCollectionIfMissing(chuyenMonCollection, ...chuyenMonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chuyenMon: IChuyenMon = sampleWithRequiredData;
        const chuyenMon2: IChuyenMon = sampleWithPartialData;
        expectedResult = service.addChuyenMonToCollectionIfMissing([], chuyenMon, chuyenMon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chuyenMon);
        expect(expectedResult).toContain(chuyenMon2);
      });

      it('should accept null and undefined values', () => {
        const chuyenMon: IChuyenMon = sampleWithRequiredData;
        expectedResult = service.addChuyenMonToCollectionIfMissing([], null, chuyenMon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chuyenMon);
      });

      it('should return initial array if no ChuyenMon is added', () => {
        const chuyenMonCollection: IChuyenMon[] = [sampleWithRequiredData];
        expectedResult = service.addChuyenMonToCollectionIfMissing(chuyenMonCollection, undefined, null);
        expect(expectedResult).toEqual(chuyenMonCollection);
      });
    });

    describe('compareChuyenMon', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareChuyenMon(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareChuyenMon(entity1, entity2);
        const compareResult2 = service.compareChuyenMon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareChuyenMon(entity1, entity2);
        const compareResult2 = service.compareChuyenMon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareChuyenMon(entity1, entity2);
        const compareResult2 = service.compareChuyenMon(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
