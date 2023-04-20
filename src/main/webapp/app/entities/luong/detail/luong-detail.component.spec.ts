import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LuongDetailComponent } from './luong-detail.component';

describe('Luong Management Detail Component', () => {
  let comp: LuongDetailComponent;
  let fixture: ComponentFixture<LuongDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LuongDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ luong: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LuongDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LuongDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load luong on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.luong).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
