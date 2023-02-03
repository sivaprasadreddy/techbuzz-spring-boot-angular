import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyEmailStatusComponent } from './verify-email-status.component';

describe('VerifyEmailStatusComponent', () => {
  let component: VerifyEmailStatusComponent;
  let fixture: ComponentFixture<VerifyEmailStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerifyEmailStatusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerifyEmailStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
