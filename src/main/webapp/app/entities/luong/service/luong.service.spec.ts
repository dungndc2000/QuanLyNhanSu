import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILuong } from '../luong.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../luong.test-samples';

import { LuongService } from './luong.service';

const requireRestSample: ILuong = {
  ...sampleWithRequiredData,
};

describe('Luong Service', () => {
  let service: LuongService;
  let httpMock: HttpTestingController;
  let expectedResult: ILuong | ILuong[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LuongService);
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

    it('should create a Luong', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const luong = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(luong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Luong', () => {
      const luong = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(luong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Luong', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Luong', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Luong', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLuongToCollectionIfMissing', () => {
      it('should add a Luong to an empty array', () => {
        const luong: ILuong = sampleWithRequiredData;
        expectedResult = service.addLuongToCollectionIfMissing([], luong);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(luong);
      });

      it('should not add a Luong to an array that contains it', () => {
        const luong: ILuong = sampleWithRequiredData;
        const luongCollection: ILuong[] = [
          {
            ...luong,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLuongToCollectionIfMissing(luongCollection, luong);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Luong to an array that doesn't contain it", () => {
        const luong: ILuong = sampleWithRequiredData;
        const luongCollection: ILuong[] = [sampleWithPartialData];
        expectedResult = service.addLuongToCollectionIfMissing(luongCollection, luong);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(luong);
      });

      it('should add only unique Luong to an array', () => {
        const luongArray: ILuong[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const luongCollection: ILuong[] = [sampleWithRequiredData];
        expectedResult = service.addLuongToCollectionIfMissing(luongCollection, ...luongArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const luong: ILuong = sampleWithRequiredData;
        const luong2: ILuong = sampleWithPartialData;
        expectedResult = service.addLuongToCollectionIfMissing([], luong, luong2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(luong);
        expect(expectedResult).toContain(luong2);
      });

      it('should accept null and undefined values', () => {
        const luong: ILuong = sampleWithRequiredData;
        expectedResult = service.addLuongToCollectionIfMissing([], null, luong, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(luong);
      });

      it('should return initial array if no Luong is added', () => {
        const luongCollection: ILuong[] = [sampleWithRequiredData];
        expectedResult = service.addLuongToCollectionIfMissing(luongCollection, undefined, null);
        expect(expectedResult).toEqual(luongCollection);
      });
    });

    describe('compareLuong', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLuong(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLuong(entity1, entity2);
        const compareResult2 = service.compareLuong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLuong(entity1, entity2);
        const compareResult2 = service.compareLuong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLuong(entity1, entity2);
        const compareResult2 = service.compareLuong(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
