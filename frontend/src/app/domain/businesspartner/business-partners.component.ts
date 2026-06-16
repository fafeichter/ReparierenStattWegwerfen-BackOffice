import {ChangeDetectionStrategy, Component, inject, signal} from '@angular/core';
import {BusinessPartnerControllerService, BusinessPartnerDto} from '@api/businesspartner';
import {ClrDatagridModule, ClrLabel, ClrTabsModule} from '@clr/angular';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-businesspartners',
  imports: [
    ClrDatagridModule,
    ClrTabsModule,
    RouterLink,
    ClrLabel
  ],
  templateUrl: './business-partners.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './business-partners.component.css',
})
export class BusinessPartners {

  private api = inject(BusinessPartnerControllerService);

  // 1. Private state signal
  private _businessPartners = signal<BusinessPartnerDto[]>([]);
  // 2. Public read-only signals
  readonly businessPartners = this._businessPartners.asReadonly();
  private _businessPartnersLoading = signal<boolean>(false);
  readonly businessPartnersLoading = this._businessPartnersLoading.asReadonly();

  ngOnInit(): void {
    this.loadAllBusinessPartners();
  }

  // 3. Method to trigger fetch
  loadAllBusinessPartners() {
    this._businessPartnersLoading.set(true);

    // Subscribe to the generated RxJS Observable
    this.api.getAllBusinessPartners().subscribe(data => {
      this._businessPartners.set(data);
      this._businessPartnersLoading.set(false);
    })
  }
}
