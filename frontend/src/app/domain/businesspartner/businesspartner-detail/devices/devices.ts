import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBusinessPartnerControllerService, DeviceBusinesspartnerDto } from '@api/device';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-devices',
  styleUrl: './devices.css',
  templateUrl: './devices.html',
})
export class Devices implements OnInit {
  businessPartnerId = input.required<number>();
  readonly businessPartnerDevices = signal<DeviceBusinesspartnerDto | undefined>(undefined);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);

  ngOnInit(): void {
    this.deviceBusinessPartnerApi
      .getDevicesOfBusinessPartner(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartnerDevices.set(data));
  }
}
