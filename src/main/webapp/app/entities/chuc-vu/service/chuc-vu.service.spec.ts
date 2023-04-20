import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChucVu } from '../chuc-vu.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../chuc-vu.test-samples';

import { ChucVuService } from './chuc-vu.service';

const requireRestSample: IChucVu = {
  ...sampleWithRequiredData,
};

describe('ChucVu Service', () => {
  let service: ChucVuService;
  let httpMock: HttpTestingController;
  let expectedResult: IChucVu | IChucVu[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChucVuService);
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

    it('should create a ChucVu', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const chucVu = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(chucVu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChucVu', () => {
      const chucVu = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(chucVu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ChucVu', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ChucVu', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ChucVu', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addChucVuToCollectionIfMissing', () => {
      it('should add a ChucVu to an empty array', () => {
        const chucVu: IChucVu = sampleWithRequiredData;
        expectedResult = service.addChucVuToCollectionIfMissing([], chucVu);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chucVu);
      });

      it('should not add a ChucVu to an array that contains it', () => {
        const chucVu: IChucVu = sampleWithRequiredData;
        const chucVuCollection: IChucVu[] = [
          {
            ...chucVu,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addChucVuToCollectionIfMissing(chucVuCollection, chucVu);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChucVu to an array that doesn't contain it", () => {
        const chucVu: IChucVu = sampleWithRequiredData;
        const chucVuCollection: IChucVu[] = [sampleWithPartialData];
        expectedResult = service.addChucVuToCollectionIfMissing(chucVuCollection, chucVu);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chucVu);
      });

      it('should add only unique ChucVu to an array', () => {
        const chucVuArray: IChucVu[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const chucVuCollection: IChucVu[] = [sampleWithRequiredData];
        expectedResult = service.addChucVuToCollectionIfMissing(chucVuCollection, ...chucVuArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chucVu: IChucVu = sampleWithRequiredData;
        const chucVu2: IChucVu = sampleWithPartialData;
        expectedResult = service.addChucVuToCollectionIfMissing([], chucVu, chucVu2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chucVu);
        expect(expectedResult).toContain(chucVu2);
      });

      it('should accept null and undefined values', () => {
        const chucVu: IChucVu = sampleWithRequiredData;
        expectedResult = service.addChucVuToCollectionIfMissing([], null, chucVu, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chucVu);
      });

      it('should return initial array if no ChucVu is added', () => {
        const chucVuCollection: IChucVu[] = [sampleWithRequiredData];
        expectedResult = service.addChucVuToCollectionIfMissing(chucVuCollection, undefined, null);
        expect(expectedResult).toEqual(chucVuCollection);
      });
    });

    describe('compareChucVu', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareChucVu(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareChucVu(entity1, entity2);
        const compareResult2 = service.compareChucVu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareChucVu(entity1, entity2);
        const compareResult2 = service.compareChucVu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareChucVu(entity1, entity2);
        const compareResult2 = service.compareChucVu(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
