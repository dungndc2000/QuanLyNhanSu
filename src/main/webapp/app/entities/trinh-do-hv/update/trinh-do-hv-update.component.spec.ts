import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrinhDoHVFormService } from './trinh-do-hv-form.service';
import { TrinhDoHVService } from '../service/trinh-do-hv.service';
import { ITrinhDoHV } from '../trinh-do-hv.model';

import { TrinhDoHVUpdateComponent } from './trinh-do-hv-update.component';

describe('TrinhDoHV Management Update Component', () => {
  let comp: TrinhDoHVUpdateComponent;
  let fixture: ComponentFixture<TrinhDoHVUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trinhDoHVFormService: TrinhDoHVFormService;
  let trinhDoHVService: TrinhDoHVService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrinhDoHVUpdateComponent],
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
      .overrideTemplate(TrinhDoHVUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrinhDoHVUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trinhDoHVFormService = TestBed.inject(TrinhDoHVFormService);
    trinhDoHVService = TestBed.inject(TrinhDoHVService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trinhDoHV: ITrinhDoHV = { id: 456 };

      activatedRoute.data = of({ trinhDoHV });
      comp.ngOnInit();

      expect(comp.trinhDoHV).toEqual(trinhDoHV);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrinhDoHV>>();
      const trinhDoHV = { id: 123 };
      jest.spyOn(trinhDoHVFormService, 'getTrinhDoHV').mockReturnValue(trinhDoHV);
      jest.spyOn(trinhDoHVService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trinhDoHV });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trinhDoHV }));
      saveSubject.complete();

      // THEN
      expect(trinhDoHVFormService.getTrinhDoHV).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trinhDoHVService.update).toHaveBeenCalledWith(expect.objectContaining(trinhDoHV));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrinhDoHV>>();
      const trinhDoHV = { id: 123 };
      jest.spyOn(trinhDoHVFormService, 'getTrinhDoHV').mockReturnValue({ id: null });
      jest.spyOn(trinhDoHVService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trinhDoHV: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trinhDoHV }));
      saveSubject.complete();

      // THEN
      expect(trinhDoHVFormService.getTrinhDoHV).toHaveBeenCalled();
      expect(trinhDoHVService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrinhDoHV>>();
      const trinhDoHV = { id: 123 };
      jest.spyOn(trinhDoHVService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trinhDoHV });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trinhDoHVService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
