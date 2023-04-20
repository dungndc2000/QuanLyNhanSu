import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KhenThuongDetailComponent } from './khen-thuong-detail.component';

describe('KhenThuong Management Detail Component', () => {
  let comp: KhenThuongDetailComponent;
  let fixture: ComponentFixture<KhenThuongDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KhenThuongDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ khenThuong: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KhenThuongDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KhenThuongDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load khenThuong on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.khenThuong).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
