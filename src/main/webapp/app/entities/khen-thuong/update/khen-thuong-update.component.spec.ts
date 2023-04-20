import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KhenThuongFormService } from './khen-thuong-form.service';
import { KhenThuongService } from '../service/khen-thuong.service';
import { IKhenThuong } from '../khen-thuong.model';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

import { KhenThuongUpdateComponent } from './khen-thuong-update.component';

describe('KhenThuong Management Update Component', () => {
  let comp: KhenThuongUpdateComponent;
  let fixture: ComponentFixture<KhenThuongUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let khenThuongFormService: KhenThuongFormService;
  let khenThuongService: KhenThuongService;
  let nhanVienService: NhanVienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KhenThuongUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(KhenThuongUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KhenThuongUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    khenThuongFormService = TestBed.inject(KhenThuongFormService);
    khenThuongService = TestBed.inject(KhenThuongService);
    nhanVienService = TestBed.inject(NhanVienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NhanVien query and add missing value', () => {
      const khenThuong: IKhenThuong = { id: 456 };
      const nhanVien: INhanVien = { id: 82756 };
      khenThuong.nhanVien = nhanVien;

      const nhanVienCollection: INhanVien[] = [{ id: 43662 }];
      jest.spyOn(nhanVienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanVienCollection })));
      const additionalNhanViens = [nhanVien];
      const expectedCollection: INhanVien[] = [...additionalNhanViens, ...nhanVienCollection];
      jest.spyOn(nhanVienService, 'addNhanVienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ khenThuong });
      comp.ngOnInit();

      expect(nhanVienService.query).toHaveBeenCalled();
      expect(nhanVienService.addNhanVienToCollectionIfMissing).toHaveBeenCalledWith(
        nhanVienCollection,
        ...additionalNhanViens.map(expect.objectContaining)
      );
      expect(comp.nhanViensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const khenThuong: IKhenThuong = { id: 456 };
      const nhanVien: INhanVien = { id: 89361 };
      khenThuong.nhanVien = nhanVien;

      activatedRoute.data = of({ khenThuong });
      comp.ngOnInit();

      expect(comp.nhanViensSharedCollection).toContain(nhanVien);
      expect(comp.khenThuong).toEqual(khenThuong);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKhenThuong>>();
      const khenThuong = { id: 123 };
      jest.spyOn(khenThuongFormService, 'getKhenThuong').mockReturnValue(khenThuong);
      jest.spyOn(khenThuongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ khenThuong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: khenThuong }));
      saveSubject.complete();

      // THEN
      expect(khenThuongFormService.getKhenThuong).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(khenThuongService.update).toHaveBeenCalledWith(expect.objectContaining(khenThuong));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKhenThuong>>();
      const khenThuong = { id: 123 };
      jest.spyOn(khenThuongFormService, 'getKhenThuong').mockReturnValue({ id: null });
      jest.spyOn(khenThuongService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ khenThuong: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: khenThuong }));
      saveSubject.complete();

      // THEN
      expect(khenThuongFormService.getKhenThuong).toHaveBeenCalled();
      expect(khenThuongService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKhenThuong>>();
      const khenThuong = { id: 123 };
      jest.spyOn(khenThuongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ khenThuong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(khenThuongService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNhanVien', () => {
      it('Should forward to nhanVienService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nhanVienService, 'compareNhanVien');
        comp.compareNhanVien(entity, entity2);
        expect(nhanVienService.compareNhanVien).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
