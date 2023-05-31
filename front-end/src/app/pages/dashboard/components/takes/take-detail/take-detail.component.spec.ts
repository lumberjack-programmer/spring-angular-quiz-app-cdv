import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TakeDetailComponent } from './take-detail.component';

describe('TakeDetailComponent', () => {
  let component: TakeDetailComponent;
  let fixture: ComponentFixture<TakeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TakeDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TakeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
