import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NhanVienFormService } from './nhan-vien-form.service';
import { NhanVienService } from '../service/nhan-vien.service';
import { INhanVien } from '../nhan-vien.model';
import { INguoiThan } from 'app/entities/nguoi-than/nguoi-than.model';
import { NguoiThanService } from 'app/entities/nguoi-than/service/nguoi-than.service';
import { IChucVu } from 'app/entities/chuc-vu/chuc-vu.model';
import { ChucVuService } from 'app/entities/chuc-vu/service/chuc-vu.service';
import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { PhongBanService } from 'app/entities/phong-ban/service/phong-ban.service';
import { IChuyenMon } from 'app/entities/chuyen-mon/chuyen-mon.model';
import { ChuyenMonService } from 'app/entities/chuyen-mon/service/chuyen-mon.service';
import { ITrinhDoHV } from 'app/entities/trinh-do-hv/trinh-do-hv.model';
import { TrinhDoHVService } from 'app/entities/trinh-do-hv/service/trinh-do-hv.service';

import { NhanVienUpdateComponent } from './nhan-vien-update.component';

describe('NhanVien Management Update Component', () => {
  let comp: NhanVienUpdateComponent;
  let fixture: ComponentFixture<NhanVienUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nhanVienFormService: NhanVienFormService;
  let nhanVienService: NhanVienService;
  let nguoiThanService: NguoiThanService;
  let chucVuService: ChucVuService;
  let phongBanService: PhongBanService;
  let chuyenMonService: ChuyenMonService;
  let trinhDoHVService: TrinhDoHVService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NhanVienUpdateComponent],
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
      .overrideTemplate(NhanVienUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NhanVienUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nhanVienFormService = TestBed.inject(NhanVienFormService);
    nhanVienService = TestBed.inject(NhanVienService);
    nguoiThanService = TestBed.inject(NguoiThanService);
    chucVuService = TestBed.inject(ChucVuService);
    phongBanService = TestBed.inject(PhongBanService);
    chuyenMonService = TestBed.inject(ChuyenMonService);
    trinhDoHVService = TestBed.inject(TrinhDoHVService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call nguoiThan query and add missing value', () => {
      const nhanVien: INhanVien = { id: 456 };
      const nguoiThan: INguoiThan = { id: 22207 };
      nhanVien.nguoiThan = nguoiThan;

      const nguoiThanCollection: INguoiThan[] = [{ id: 6366 }];
      jest.spyOn(nguoiThanService, 'query').mockReturnValue(of(new HttpResponse({ body: nguoiThanCollection })));
      const expectedCollection: INguoiThan[] = [nguoiThan, ...nguoiThanCollection];
      jest.spyOn(nguoiThanService, 'addNguoiThanToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(nguoiThanService.query).toHaveBeenCalled();
      expect(nguoiThanService.addNguoiThanToCollectionIfMissing).toHaveBeenCalledWith(nguoiThanCollection, nguoiThan);
      expect(comp.nguoiThansCollection).toEqual(expectedCollection);
    });

    it('Should call ChucVu query and add missing value', () => {
      const nhanVien: INhanVien = { id: 456 };
      const chucVu: IChucVu = { id: 14662 };
      nhanVien.chucVu = chucVu;

      const chucVuCollection: IChucVu[] = [{ id: 52585 }];
      jest.spyOn(chucVuService, 'query').mockReturnValue(of(new HttpResponse({ body: chucVuCollection })));
      const additionalChucVus = [chucVu];
      const expectedCollection: IChucVu[] = [...additionalChucVus, ...chucVuCollection];
      jest.spyOn(chucVuService, 'addChucVuToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(chucVuService.query).toHaveBeenCalled();
      expect(chucVuService.addChucVuToCollectionIfMissing).toHaveBeenCalledWith(
        chucVuCollection,
        ...additionalChucVus.map(expect.objectContaining)
      );
      expect(comp.chucVusSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PhongBan query and add missing value', () => {
      const nhanVien: INhanVien = { id: 456 };
      const phongBan: IPhongBan = { id: 91853 };
      nhanVien.phongBan = phongBan;

      const phongBanCollection: IPhongBan[] = [{ id: 72888 }];
      jest.spyOn(phongBanService, 'query').mockReturnValue(of(new HttpResponse({ body: phongBanCollection })));
      const additionalPhongBans = [phongBan];
      const expectedCollection: IPhongBan[] = [...additionalPhongBans, ...phongBanCollection];
      jest.spyOn(phongBanService, 'addPhongBanToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(phongBanService.query).toHaveBeenCalled();
      expect(phongBanService.addPhongBanToCollectionIfMissing).toHaveBeenCalledWith(
        phongBanCollection,
        ...additionalPhongBans.map(expect.objectContaining)
      );
      expect(comp.phongBansSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ChuyenMon query and add missing value', () => {
      const nhanVien: INhanVien = { id: 456 };
      const chuyenMon: IChuyenMon = { id: 66234 };
      nhanVien.chuyenMon = chuyenMon;

      const chuyenMonCollection: IChuyenMon[] = [{ id: 2993 }];
      jest.spyOn(chuyenMonService, 'query').mockReturnValue(of(new HttpResponse({ body: chuyenMonCollection })));
      const additionalChuyenMons = [chuyenMon];
      const expectedCollection: IChuyenMon[] = [...additionalChuyenMons, ...chuyenMonCollection];
      jest.spyOn(chuyenMonService, 'addChuyenMonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(chuyenMonService.query).toHaveBeenCalled();
      expect(chuyenMonService.addChuyenMonToCollectionIfMissing).toHaveBeenCalledWith(
        chuyenMonCollection,
        ...additionalChuyenMons.map(expect.objectContaining)
      );
      expect(comp.chuyenMonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrinhDoHV query and add missing value', () => {
      const nhanVien: INhanVien = { id: 456 };
      const trinhDoHV: ITrinhDoHV = { id: 74835 };
      nhanVien.trinhDoHV = trinhDoHV;

      const trinhDoHVCollection: ITrinhDoHV[] = [{ id: 42844 }];
      jest.spyOn(trinhDoHVService, 'query').mockReturnValue(of(new HttpResponse({ body: trinhDoHVCollection })));
      const additionalTrinhDoHVS = [trinhDoHV];
      const expectedCollection: ITrinhDoHV[] = [...additionalTrinhDoHVS, ...trinhDoHVCollection];
      jest.spyOn(trinhDoHVService, 'addTrinhDoHVToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(trinhDoHVService.query).toHaveBeenCalled();
      expect(trinhDoHVService.addTrinhDoHVToCollectionIfMissing).toHaveBeenCalledWith(
        trinhDoHVCollection,
        ...additionalTrinhDoHVS.map(expect.objectContaining)
      );
      expect(comp.trinhDoHVSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nhanVien: INhanVien = { id: 456 };
      const nguoiThan: INguoiThan = { id: 82809 };
      nhanVien.nguoiThan = nguoiThan;
      const chucVu: IChucVu = { id: 32752 };
      nhanVien.chucVu = chucVu;
      const phongBan: IPhongBan = { id: 92246 };
      nhanVien.phongBan = phongBan;
      const chuyenMon: IChuyenMon = { id: 48145 };
      nhanVien.chuyenMon = chuyenMon;
      const trinhDoHV: ITrinhDoHV = { id: 41262 };
      nhanVien.trinhDoHV = trinhDoHV;

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(comp.nguoiThansCollection).toContain(nguoiThan);
      expect(comp.chucVusSharedCollection).toContain(chucVu);
      expect(comp.phongBansSharedCollection).toContain(phongBan);
      expect(comp.chuyenMonsSharedCollection).toContain(chuyenMon);
      expect(comp.trinhDoHVSSharedCollection).toContain(trinhDoHV);
      expect(comp.nhanVien).toEqual(nhanVien);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanVien>>();
      const nhanVien = { id: 123 };
      jest.spyOn(nhanVienFormService, 'getNhanVien').mockReturnValue(nhanVien);
      jest.spyOn(nhanVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanVien }));
      saveSubject.complete();

      // THEN
      expect(nhanVienFormService.getNhanVien).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nhanVienService.update).toHaveBeenCalledWith(expect.objectContaining(nhanVien));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanVien>>();
      const nhanVien = { id: 123 };
      jest.spyOn(nhanVienFormService, 'getNhanVien').mockReturnValue({ id: null });
      jest.spyOn(nhanVienService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanVien }));
      saveSubject.complete();

      // THEN
      expect(nhanVienFormService.getNhanVien).toHaveBeenCalled();
      expect(nhanVienService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INhanVien>>();
      const nhanVien = { id: 123 };
      jest.spyOn(nhanVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nhanVienService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNguoiThan', () => {
      it('Should forward to nguoiThanService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nguoiThanService, 'compareNguoiThan');
        comp.compareNguoiThan(entity, entity2);
        expect(nguoiThanService.compareNguoiThan).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareChucVu', () => {
      it('Should forward to chucVuService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(chucVuService, 'compareChucVu');
        comp.compareChucVu(entity, entity2);
        expect(chucVuService.compareChucVu).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePhongBan', () => {
      it('Should forward to phongBanService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(phongBanService, 'comparePhongBan');
        comp.comparePhongBan(entity, entity2);
        expect(phongBanService.comparePhongBan).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareChuyenMon', () => {
      it('Should forward to chuyenMonService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(chuyenMonService, 'compareChuyenMon');
        comp.compareChuyenMon(entity, entity2);
        expect(chuyenMonService.compareChuyenMon).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTrinhDoHV', () => {
      it('Should forward to trinhDoHVService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(trinhDoHVService, 'compareTrinhDoHV');
        comp.compareTrinhDoHV(entity, entity2);
        expect(trinhDoHVService.compareTrinhDoHV).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
