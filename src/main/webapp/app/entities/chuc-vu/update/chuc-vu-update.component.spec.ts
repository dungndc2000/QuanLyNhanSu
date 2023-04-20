import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChucVuFormService } from './chuc-vu-form.service';
import { ChucVuService } from '../service/chuc-vu.service';
import { IChucVu } from '../chuc-vu.model';

import { ChucVuUpdateComponent } from './chuc-vu-update.component';

describe('ChucVu Management Update Component', () => {
  let comp: ChucVuUpdateComponent;
  let fixture: ComponentFixture<ChucVuUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chucVuFormService: ChucVuFormService;
  let chucVuService: ChucVuService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChucVuUpdateComponent],
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
      .overrideTemplate(ChucVuUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChucVuUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chucVuFormService = TestBed.inject(ChucVuFormService);
    chucVuService = TestBed.inject(ChucVuService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chucVu: IChucVu = { id: 456 };

      activatedRoute.data = of({ chucVu });
      comp.ngOnInit();

      expect(comp.chucVu).toEqual(chucVu);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucVu>>();
      const chucVu = { id: 123 };
      jest.spyOn(chucVuFormService, 'getChucVu').mockReturnValue(chucVu);
      jest.spyOn(chucVuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucVu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chucVu }));
      saveSubject.complete();

      // THEN
      expect(chucVuFormService.getChucVu).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chucVuService.update).toHaveBeenCalledWith(expect.objectContaining(chucVu));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucVu>>();
      const chucVu = { id: 123 };
      jest.spyOn(chucVuFormService, 'getChucVu').mockReturnValue({ id: null });
      jest.spyOn(chucVuService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucVu: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chucVu }));
      saveSubject.complete();

      // THEN
      expect(chucVuFormService.getChucVu).toHaveBeenCalled();
      expect(chucVuService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChucVu>>();
      const chucVu = { id: 123 };
      jest.spyOn(chucVuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chucVu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chucVuService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
