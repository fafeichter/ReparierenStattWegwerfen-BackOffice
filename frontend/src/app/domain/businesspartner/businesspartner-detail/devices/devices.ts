import { Component, computed, inject, input } from '@angular/core';
import { DeviceBusinessPartnerControllerService } from '@api/device';
import { RouterLink } from '@angular/router';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  imports: [RouterLink, ClrCardModule, ClrIcon],
  selector: 'app-devices',
  styleUrl: './devices.css',
  templateUrl: './devices.html',
})
export class Devices {
  businessPartnerId = input.required<number>();
  navItem = input.required<NavItem>();

  private readonly devicesResource = inject(
    DeviceBusinessPartnerControllerService,
  ).getDevicesOfBusinessPartnerResource(() => ({ businessPartnerId: this.businessPartnerId() }));

  readonly businessPartnerDevices = computed(() =>
    this.devicesResource.hasValue() ? this.devicesResource.value() : undefined,
  );
}
