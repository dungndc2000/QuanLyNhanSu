import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKhenThuong } from '../khen-thuong.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../khen-thuong.test-samples';

import { KhenThuongService, RestKhenThuong } from './khen-thuong.service';

const requireRestSample: RestKhenThuong = {
  ...sampleWithRequiredData,
  ngayQd: sampleWithRequiredData.ngayQd?.toJSON(),
};

describe('KhenThuong Service', () => {
  let service: KhenThuongService;
  let httpMock: HttpTestingController;
  let expectedResult: IKhenThuong | IKhenThuong[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KhenThuongService);
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

    it('should create a KhenThuong', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const khenThuong = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(khenThuong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KhenThuong', () => {
      const khenThuong = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(khenThuong).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KhenThuong', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KhenThuong', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KhenThuong', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKhenThuongToCollectionIfMissing', () => {
      it('should add a KhenThuong to an empty array', () => {
        const khenThuong: IKhenThuong = sampleWithRequiredData;
        expectedResult = service.addKhenThuongToCollectionIfMissing([], khenThuong);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(khenThuong);
      });

      it('should not add a KhenThuong to an array that contains it', () => {
        const khenThuong: IKhenThuong = sampleWithRequiredData;
        const khenThuongCollection: IKhenThuong[] = [
          {
            ...khenThuong,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKhenThuongToCollectionIfMissing(khenThuongCollection, khenThuong);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KhenThuong to an array that doesn't contain it", () => {
        const khenThuong: IKhenThuong = sampleWithRequiredData;
        const khenThuongCollection: IKhenThuong[] = [sampleWithPartialData];
        expectedResult = service.addKhenThuongToCollectionIfMissing(khenThuongCollection, khenThuong);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(khenThuong);
      });

      it('should add only unique KhenThuong to an array', () => {
        const khenThuongArray: IKhenThuong[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const khenThuongCollection: IKhenThuong[] = [sampleWithRequiredData];
        expectedResult = service.addKhenThuongToCollectionIfMissing(khenThuongCollection, ...khenThuongArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const khenThuong: IKhenThuong = sampleWithRequiredData;
        const khenThuong2: IKhenThuong = sampleWithPartialData;
        expectedResult = service.addKhenThuongToCollectionIfMissing([], khenThuong, khenThuong2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(khenThuong);
        expect(expectedResult).toContain(khenThuong2);
      });

      it('should accept null and undefined values', () => {
        const khenThuong: IKhenThuong = sampleWithRequiredData;
        expectedResult = service.addKhenThuongToCollectionIfMissing([], null, khenThuong, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(khenThuong);
      });

      it('should return initial array if no KhenThuong is added', () => {
        const khenThuongCollection: IKhenThuong[] = [sampleWithRequiredData];
        expectedResult = service.addKhenThuongToCollectionIfMissing(khenThuongCollection, undefined, null);
        expect(expectedResult).toEqual(khenThuongCollection);
      });
    });

    describe('compareKhenThuong', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKhenThuong(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKhenThuong(entity1, entity2);
        const compareResult2 = service.compareKhenThuong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKhenThuong(entity1, entity2);
        const compareResult2 = service.compareKhenThuong(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKhenThuong(entity1, entity2);
        const compareResult2 = service.compareKhenThuong(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
