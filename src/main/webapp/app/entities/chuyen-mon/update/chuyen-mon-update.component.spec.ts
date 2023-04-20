import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChuyenMonFormService } from './chuyen-mon-form.service';
import { ChuyenMonService } from '../service/chuyen-mon.service';
import { IChuyenMon } from '../chuyen-mon.model';

import { ChuyenMonUpdateComponent } from './chuyen-mon-update.component';

describe('ChuyenMon Management Update Component', () => {
  let comp: ChuyenMonUpdateComponent;
  let fixture: ComponentFixture<ChuyenMonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chuyenMonFormService: ChuyenMonFormService;
  let chuyenMonService: ChuyenMonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChuyenMonUpdateComponent],
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
      .overrideTemplate(ChuyenMonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChuyenMonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chuyenMonFormService = TestBed.inject(ChuyenMonFormService);
    chuyenMonService = TestBed.inject(ChuyenMonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chuyenMon: IChuyenMon = { id: 456 };

      activatedRoute.data = of({ chuyenMon });
      comp.ngOnInit();

      expect(comp.chuyenMon).toEqual(chuyenMon);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChuyenMon>>();
      const chuyenMon = { id: 123 };
      jest.spyOn(chuyenMonFormService, 'getChuyenMon').mockReturnValue(chuyenMon);
      jest.spyOn(chuyenMonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chuyenMon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chuyenMon }));
      saveSubject.complete();

      // THEN
      expect(chuyenMonFormService.getChuyenMon).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chuyenMonService.update).toHaveBeenCalledWith(expect.objectContaining(chuyenMon));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChuyenMon>>();
      const chuyenMon = { id: 123 };
      jest.spyOn(chuyenMonFormService, 'getChuyenMon').mockReturnValue({ id: null });
      jest.spyOn(chuyenMonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chuyenMon: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chuyenMon }));
      saveSubject.complete();

      // THEN
      expect(chuyenMonFormService.getChuyenMon).toHaveBeenCalled();
      expect(chuyenMonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChuyenMon>>();
      const chuyenMon = { id: 123 };
      jest.spyOn(chuyenMonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chuyenMon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chuyenMonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
