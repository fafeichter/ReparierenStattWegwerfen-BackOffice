import { Component, inject, signal } from '@angular/core';
import { BusinessPartnerControllerService, BusinessPartnerDto } from '@api/businesspartner';
import { ClrDatagridModule, ClrLabel, ClrTabsModule } from '@clr/angular';
import { RouterLink } from '@angular/router';
import { OrElsePipe } from '../../../pipes/or-else-pipe';

@Component({
  selector: 'app-businesspartners',
  imports: [ClrDatagridModule, ClrTabsModule, RouterLink, ClrLabel, OrElsePipe],
  templateUrl: './business-partners.component.html',
  styleUrl: './business-partners.component.css',
})
export class BusinessPartners {
  private api = inject(BusinessPartnerControllerService);

  private _businessPartners = signal<BusinessPartnerDto[]>([]);
  readonly businessPartners = this._businessPartners.asReadonly();
  private _businessPartnersLoading = signal<boolean>(false);
  readonly businessPartnersLoading = this._businessPartnersLoading.asReadonly();

  ngOnInit(): void {
    this.loadAllBusinessPartners();
  }

  loadAllBusinessPartners() {
    this._businessPartnersLoading.set(true);

    this.api.getAllBusinessPartners().subscribe((data) => {
      this._businessPartners.set(data);
      this._businessPartnersLoading.set(false);
    });
  }
}
