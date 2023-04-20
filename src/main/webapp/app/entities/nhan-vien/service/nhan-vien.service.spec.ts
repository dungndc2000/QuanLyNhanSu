import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhanVien } from '../nhan-vien.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nhan-vien.test-samples';

import { NhanVienService, RestNhanVien } from './nhan-vien.service';

const requireRestSample: RestNhanVien = {
  ...sampleWithRequiredData,
  ngaySinh: sampleWithRequiredData.ngaySinh?.toJSON(),
};

describe('NhanVien Service', () => {
  let service: NhanVienService;
  let httpMock: HttpTestingController;
  let expectedResult: INhanVien | INhanVien[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NhanVienService);
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

    it('should create a NhanVien', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nhanVien = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nhanVien).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NhanVien', () => {
      const nhanVien = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nhanVien).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NhanVien', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NhanVien', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NhanVien', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNhanVienToCollectionIfMissing', () => {
      it('should add a NhanVien to an empty array', () => {
        const nhanVien: INhanVien = sampleWithRequiredData;
        expectedResult = service.addNhanVienToCollectionIfMissing([], nhanVien);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nhanVien);
      });

      it('should not add a NhanVien to an array that contains it', () => {
        const nhanVien: INhanVien = sampleWithRequiredData;
        const nhanVienCollection: INhanVien[] = [
          {
            ...nhanVien,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, nhanVien);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NhanVien to an array that doesn't contain it", () => {
        const nhanVien: INhanVien = sampleWithRequiredData;
        const nhanVienCollection: INhanVien[] = [sampleWithPartialData];
        expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, nhanVien);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nhanVien);
      });

      it('should add only unique NhanVien to an array', () => {
        const nhanVienArray: INhanVien[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nhanVienCollection: INhanVien[] = [sampleWithRequiredData];
        expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, ...nhanVienArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nhanVien: INhanVien = sampleWithRequiredData;
        const nhanVien2: INhanVien = sampleWithPartialData;
        expectedResult = service.addNhanVienToCollectionIfMissing([], nhanVien, nhanVien2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nhanVien);
        expect(expectedResult).toContain(nhanVien2);
      });

      it('should accept null and undefined values', () => {
        const nhanVien: INhanVien = sampleWithRequiredData;
        expectedResult = service.addNhanVienToCollectionIfMissing([], null, nhanVien, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nhanVien);
      });

      it('should return initial array if no NhanVien is added', () => {
        const nhanVienCollection: INhanVien[] = [sampleWithRequiredData];
        expectedResult = service.addNhanVienToCollectionIfMissing(nhanVienCollection, undefined, null);
        expect(expectedResult).toEqual(nhanVienCollection);
      });
    });

    describe('compareNhanVien', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNhanVien(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNhanVien(entity1, entity2);
        const compareResult2 = service.compareNhanVien(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNhanVien(entity1, entity2);
        const compareResult2 = service.compareNhanVien(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNhanVien(entity1, entity2);
        const compareResult2 = service.compareNhanVien(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
