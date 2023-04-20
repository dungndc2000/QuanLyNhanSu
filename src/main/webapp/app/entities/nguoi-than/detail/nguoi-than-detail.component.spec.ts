import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NguoiThanDetailComponent } from './nguoi-than-detail.component';

describe('NguoiThan Management Detail Component', () => {
  let comp: NguoiThanDetailComponent;
  let fixture: ComponentFixture<NguoiThanDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NguoiThanDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nguoiThan: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NguoiThanDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NguoiThanDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nguoiThan on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nguoiThan).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
