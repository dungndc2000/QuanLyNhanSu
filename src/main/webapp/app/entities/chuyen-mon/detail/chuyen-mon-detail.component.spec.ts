import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChuyenMonDetailComponent } from './chuyen-mon-detail.component';

describe('ChuyenMon Management Detail Component', () => {
  let comp: ChuyenMonDetailComponent;
  let fixture: ComponentFixture<ChuyenMonDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChuyenMonDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ chuyenMon: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ChuyenMonDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChuyenMonDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load chuyenMon on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.chuyenMon).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
