import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INguoiThan } from '../nguoi-than.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nguoi-than.test-samples';

import { NguoiThanService } from './nguoi-than.service';

const requireRestSample: INguoiThan = {
  ...sampleWithRequiredData,
};

describe('NguoiThan Service', () => {
  let service: NguoiThanService;
  let httpMock: HttpTestingController;
  let expectedResult: INguoiThan | INguoiThan[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NguoiThanService);
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

    it('should create a NguoiThan', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nguoiThan = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nguoiThan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NguoiThan', () => {
      const nguoiThan = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nguoiThan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NguoiThan', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NguoiThan', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NguoiThan', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNguoiThanToCollectionIfMissing', () => {
      it('should add a NguoiThan to an empty array', () => {
        const nguoiThan: INguoiThan = sampleWithRequiredData;
        expectedResult = service.addNguoiThanToCollectionIfMissing([], nguoiThan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nguoiThan);
      });

      it('should not add a NguoiThan to an array that contains it', () => {
        const nguoiThan: INguoiThan = sampleWithRequiredData;
        const nguoiThanCollection: INguoiThan[] = [
          {
            ...nguoiThan,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNguoiThanToCollectionIfMissing(nguoiThanCollection, nguoiThan);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NguoiThan to an array that doesn't contain it", () => {
        const nguoiThan: INguoiThan = sampleWithRequiredData;
        const nguoiThanCollection: INguoiThan[] = [sampleWithPartialData];
        expectedResult = service.addNguoiThanToCollectionIfMissing(nguoiThanCollection, nguoiThan);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nguoiThan);
      });

      it('should add only unique NguoiThan to an array', () => {
        const nguoiThanArray: INguoiThan[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nguoiThanCollection: INguoiThan[] = [sampleWithRequiredData];
        expectedResult = service.addNguoiThanToCollectionIfMissing(nguoiThanCollection, ...nguoiThanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nguoiThan: INguoiThan = sampleWithRequiredData;
        const nguoiThan2: INguoiThan = sampleWithPartialData;
        expectedResult = service.addNguoiThanToCollectionIfMissing([], nguoiThan, nguoiThan2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nguoiThan);
        expect(expectedResult).toContain(nguoiThan2);
      });

      it('should accept null and undefined values', () => {
        const nguoiThan: INguoiThan = sampleWithRequiredData;
        expectedResult = service.addNguoiThanToCollectionIfMissing([], null, nguoiThan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nguoiThan);
      });

      it('should return initial array if no NguoiThan is added', () => {
        const nguoiThanCollection: INguoiThan[] = [sampleWithRequiredData];
        expectedResult = service.addNguoiThanToCollectionIfMissing(nguoiThanCollection, undefined, null);
        expect(expectedResult).toEqual(nguoiThanCollection);
      });
    });

    describe('compareNguoiThan', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNguoiThan(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNguoiThan(entity1, entity2);
        const compareResult2 = service.compareNguoiThan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNguoiThan(entity1, entity2);
        const compareResult2 = service.compareNguoiThan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNguoiThan(entity1, entity2);
        const compareResult2 = service.compareNguoiThan(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
