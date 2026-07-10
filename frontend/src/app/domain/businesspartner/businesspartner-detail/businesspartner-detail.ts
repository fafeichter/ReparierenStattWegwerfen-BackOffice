import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BusinessPartnerControllerService, BusinessPartnerDetailDto } from '@api/businesspartner';
import { ClrLabel } from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { DeviceBusinessPartnerControllerService, DeviceBusinesspartnerDto } from '@api/device';

@Component({
  selector: 'app-businesspartner-detail',
  imports: [ClrLabel, OrElsePipe, RouterLink],
  templateUrl: './businesspartner-detail.html',
  styleUrl: './businesspartner-detail.css',
})
export class BusinesspartnerDetail {
  readonly businessPartner = signal<BusinessPartnerDetailDto | undefined>(undefined);
  readonly businessPartnerDevices = signal<DeviceBusinesspartnerDto | undefined>(undefined);

  private api = inject(BusinessPartnerControllerService);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    const businessPartnerId = Number(this.route.snapshot.paramMap.get('businessPartnerId'));
    this.api
      .getBusinessPartnerDetails(businessPartnerId)
      .subscribe((data) => this.businessPartner.set(data));

    this.deviceBusinessPartnerApi
      .getDevicesOfBusinessPartner(businessPartnerId)
      .subscribe((data) => this.businessPartnerDevices.set(data));
  }
}
