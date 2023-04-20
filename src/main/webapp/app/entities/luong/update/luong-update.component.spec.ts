import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LuongFormService } from './luong-form.service';
import { LuongService } from '../service/luong.service';
import { ILuong } from '../luong.model';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

import { LuongUpdateComponent } from './luong-update.component';

describe('Luong Management Update Component', () => {
  let comp: LuongUpdateComponent;
  let fixture: ComponentFixture<LuongUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let luongFormService: LuongFormService;
  let luongService: LuongService;
  let nhanVienService: NhanVienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LuongUpdateComponent],
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
      .overrideTemplate(LuongUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LuongUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    luongFormService = TestBed.inject(LuongFormService);
    luongService = TestBed.inject(LuongService);
    nhanVienService = TestBed.inject(NhanVienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NhanVien query and add missing value', () => {
      const luong: ILuong = { id: 456 };
      const nhanVien: INhanVien = { id: 81735 };
      luong.nhanVien = nhanVien;

      const nhanVienCollection: INhanVien[] = [{ id: 96533 }];
      jest.spyOn(nhanVienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanVienCollection })));
      const additionalNhanViens = [nhanVien];
      const expectedCollection: INhanVien[] = [...additionalNhanViens, ...nhanVienCollection];
      jest.spyOn(nhanVienService, 'addNhanVienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ luong });
      comp.ngOnInit();

      expect(nhanVienService.query).toHaveBeenCalled();
      expect(nhanVienService.addNhanVienToCollectionIfMissing).toHaveBeenCalledWith(
        nhanVienCollection,
        ...additionalNhanViens.map(expect.objectContaining)
      );
      expect(comp.nhanViensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const luong: ILuong = { id: 456 };
      const nhanVien: INhanVien = { id: 84013 };
      luong.nhanVien = nhanVien;

      activatedRoute.data = of({ luong });
      comp.ngOnInit();

      expect(comp.nhanViensSharedCollection).toContain(nhanVien);
      expect(comp.luong).toEqual(luong);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILuong>>();
      const luong = { id: 123 };
      jest.spyOn(luongFormService, 'getLuong').mockReturnValue(luong);
      jest.spyOn(luongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ luong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: luong }));
      saveSubject.complete();

      // THEN
      expect(luongFormService.getLuong).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(luongService.update).toHaveBeenCalledWith(expect.objectContaining(luong));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILuong>>();
      const luong = { id: 123 };
      jest.spyOn(luongFormService, 'getLuong').mockReturnValue({ id: null });
      jest.spyOn(luongService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ luong: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: luong }));
      saveSubject.complete();

      // THEN
      expect(luongFormService.getLuong).toHaveBeenCalled();
      expect(luongService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILuong>>();
      const luong = { id: 123 };
      jest.spyOn(luongService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ luong });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(luongService.update).toHaveBeenCalled();
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
