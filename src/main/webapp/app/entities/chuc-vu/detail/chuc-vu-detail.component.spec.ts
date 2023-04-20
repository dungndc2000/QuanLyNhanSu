import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChucVuDetailComponent } from './chuc-vu-detail.component';

describe('ChucVu Management Detail Component', () => {
  let comp: ChucVuDetailComponent;
  let fixture: ComponentFixture<ChucVuDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChucVuDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ chucVu: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ChucVuDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChucVuDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load chucVu on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.chucVu).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
