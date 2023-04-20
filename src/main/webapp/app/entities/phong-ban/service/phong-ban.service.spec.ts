import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongBan } from '../phong-ban.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../phong-ban.test-samples';

import { PhongBanService } from './phong-ban.service';

const requireRestSample: IPhongBan = {
  ...sampleWithRequiredData,
};

describe('PhongBan Service', () => {
  let service: PhongBanService;
  let httpMock: HttpTestingController;
  let expectedResult: IPhongBan | IPhongBan[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PhongBanService);
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

    it('should create a PhongBan', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const phongBan = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(phongBan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PhongBan', () => {
      const phongBan = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(phongBan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PhongBan', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PhongBan', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PhongBan', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPhongBanToCollectionIfMissing', () => {
      it('should add a PhongBan to an empty array', () => {
        const phongBan: IPhongBan = sampleWithRequiredData;
        expectedResult = service.addPhongBanToCollectionIfMissing([], phongBan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phongBan);
      });

      it('should not add a PhongBan to an array that contains it', () => {
        const phongBan: IPhongBan = sampleWithRequiredData;
        const phongBanCollection: IPhongBan[] = [
          {
            ...phongBan,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, phongBan);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PhongBan to an array that doesn't contain it", () => {
        const phongBan: IPhongBan = sampleWithRequiredData;
        const phongBanCollection: IPhongBan[] = [sampleWithPartialData];
        expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, phongBan);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phongBan);
      });

      it('should add only unique PhongBan to an array', () => {
        const phongBanArray: IPhongBan[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const phongBanCollection: IPhongBan[] = [sampleWithRequiredData];
        expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, ...phongBanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const phongBan: IPhongBan = sampleWithRequiredData;
        const phongBan2: IPhongBan = sampleWithPartialData;
        expectedResult = service.addPhongBanToCollectionIfMissing([], phongBan, phongBan2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phongBan);
        expect(expectedResult).toContain(phongBan2);
      });

      it('should accept null and undefined values', () => {
        const phongBan: IPhongBan = sampleWithRequiredData;
        expectedResult = service.addPhongBanToCollectionIfMissing([], null, phongBan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phongBan);
      });

      it('should return initial array if no PhongBan is added', () => {
        const phongBanCollection: IPhongBan[] = [sampleWithRequiredData];
        expectedResult = service.addPhongBanToCollectionIfMissing(phongBanCollection, undefined, null);
        expect(expectedResult).toEqual(phongBanCollection);
      });
    });

    describe('comparePhongBan', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePhongBan(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePhongBan(entity1, entity2);
        const compareResult2 = service.comparePhongBan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePhongBan(entity1, entity2);
        const compareResult2 = service.comparePhongBan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePhongBan(entity1, entity2);
        const compareResult2 = service.comparePhongBan(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
