import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NguoiThanFormService } from './nguoi-than-form.service';
import { NguoiThanService } from '../service/nguoi-than.service';
import { INguoiThan } from '../nguoi-than.model';

import { NguoiThanUpdateComponent } from './nguoi-than-update.component';

describe('NguoiThan Management Update Component', () => {
  let comp: NguoiThanUpdateComponent;
  let fixture: ComponentFixture<NguoiThanUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nguoiThanFormService: NguoiThanFormService;
  let nguoiThanService: NguoiThanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NguoiThanUpdateComponent],
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
      .overrideTemplate(NguoiThanUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NguoiThanUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nguoiThanFormService = TestBed.inject(NguoiThanFormService);
    nguoiThanService = TestBed.inject(NguoiThanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nguoiThan: INguoiThan = { id: 456 };

      activatedRoute.data = of({ nguoiThan });
      comp.ngOnInit();

      expect(comp.nguoiThan).toEqual(nguoiThan);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INguoiThan>>();
      const nguoiThan = { id: 123 };
      jest.spyOn(nguoiThanFormService, 'getNguoiThan').mockReturnValue(nguoiThan);
      jest.spyOn(nguoiThanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nguoiThan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nguoiThan }));
      saveSubject.complete();

      // THEN
      expect(nguoiThanFormService.getNguoiThan).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nguoiThanService.update).toHaveBeenCalledWith(expect.objectContaining(nguoiThan));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INguoiThan>>();
      const nguoiThan = { id: 123 };
      jest.spyOn(nguoiThanFormService, 'getNguoiThan').mockReturnValue({ id: null });
      jest.spyOn(nguoiThanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nguoiThan: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nguoiThan }));
      saveSubject.complete();

      // THEN
      expect(nguoiThanFormService.getNguoiThan).toHaveBeenCalled();
      expect(nguoiThanService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INguoiThan>>();
      const nguoiThan = { id: 123 };
      jest.spyOn(nguoiThanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nguoiThan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nguoiThanService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
