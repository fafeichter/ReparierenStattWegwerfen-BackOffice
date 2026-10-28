import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBusinessPartnerControllerService, DeviceBusinesspartnerDto } from '@api/device';
import { RouterLink } from '@angular/router';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { NavItem } from '../../../../clarity-addons/components/scrollspy/scrollspy';

@Component({
  imports: [RouterLink, ClrCardModule, ClrIcon],
  selector: 'app-devices',
  styleUrl: './devices.css',
  templateUrl: './devices.html',
})
export class Devices implements OnInit {
  businessPartnerId = input.required<number>();
  navItem = input.required<NavItem>();

  readonly businessPartnerDevices = signal<DeviceBusinesspartnerDto | undefined>(undefined);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);

  ngOnInit(): void {
    this.deviceBusinessPartnerApi
      .getDevicesOfBusinessPartner(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartnerDevices.set(data));
  }
}
