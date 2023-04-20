jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TrinhDoHVService } from '../service/trinh-do-hv.service';

import { TrinhDoHVDeleteDialogComponent } from './trinh-do-hv-delete-dialog.component';

describe('TrinhDoHV Management Delete Component', () => {
  let comp: TrinhDoHVDeleteDialogComponent;
  let fixture: ComponentFixture<TrinhDoHVDeleteDialogComponent>;
  let service: TrinhDoHVService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TrinhDoHVDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TrinhDoHVDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrinhDoHVDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TrinhDoHVService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
