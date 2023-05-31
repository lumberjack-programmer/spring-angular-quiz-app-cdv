import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionComponentComponent } from './option-component.component';

describe('OptionComponentComponent', () => {
  let component: OptionComponentComponent;
  let fixture: ComponentFixture<OptionComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OptionComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OptionComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
