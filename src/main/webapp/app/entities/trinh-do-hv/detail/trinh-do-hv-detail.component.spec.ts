import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrinhDoHVDetailComponent } from './trinh-do-hv-detail.component';

describe('TrinhDoHV Management Detail Component', () => {
  let comp: TrinhDoHVDetailComponent;
  let fixture: ComponentFixture<TrinhDoHVDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrinhDoHVDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trinhDoHV: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrinhDoHVDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrinhDoHVDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trinhDoHV on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trinhDoHV).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
