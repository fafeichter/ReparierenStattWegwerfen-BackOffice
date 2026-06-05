import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessPartners } from './business-partners.component';

describe('Businesspartners', () => {
  let component: BusinessPartners;
  let fixture: ComponentFixture<BusinessPartners>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BusinessPartners],
    }).compileComponents();

    fixture = TestBed.createComponent(BusinessPartners);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
