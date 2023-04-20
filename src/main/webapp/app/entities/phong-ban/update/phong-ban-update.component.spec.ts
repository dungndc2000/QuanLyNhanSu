import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PhongBanFormService } from './phong-ban-form.service';
import { PhongBanService } from '../service/phong-ban.service';
import { IPhongBan } from '../phong-ban.model';

import { PhongBanUpdateComponent } from './phong-ban-update.component';

describe('PhongBan Management Update Component', () => {
  let comp: PhongBanUpdateComponent;
  let fixture: ComponentFixture<PhongBanUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let phongBanFormService: PhongBanFormService;
  let phongBanService: PhongBanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PhongBanUpdateComponent],
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
      .overrideTemplate(PhongBanUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhongBanUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    phongBanFormService = TestBed.inject(PhongBanFormService);
    phongBanService = TestBed.inject(PhongBanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const phongBan: IPhongBan = { id: 456 };

      activatedRoute.data = of({ phongBan });
      comp.ngOnInit();

      expect(comp.phongBan).toEqual(phongBan);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongBan>>();
      const phongBan = { id: 123 };
      jest.spyOn(phongBanFormService, 'getPhongBan').mockReturnValue(phongBan);
      jest.spyOn(phongBanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongBan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phongBan }));
      saveSubject.complete();

      // THEN
      expect(phongBanFormService.getPhongBan).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(phongBanService.update).toHaveBeenCalledWith(expect.objectContaining(phongBan));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongBan>>();
      const phongBan = { id: 123 };
      jest.spyOn(phongBanFormService, 'getPhongBan').mockReturnValue({ id: null });
      jest.spyOn(phongBanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongBan: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phongBan }));
      saveSubject.complete();

      // THEN
      expect(phongBanFormService.getPhongBan).toHaveBeenCalled();
      expect(phongBanService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhongBan>>();
      const phongBan = { id: 123 };
      jest.spyOn(phongBanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phongBan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(phongBanService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
