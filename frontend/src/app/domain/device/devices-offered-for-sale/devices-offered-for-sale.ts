import { Component, computed, inject, signal } from '@angular/core';
import { DeviceSearchControllerService } from '@api/device';
import { ClrAlertModule, ClrBadge, ClrDatagridModule, ClrIcon } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrAlertModule, ClrDatagridModule, DatePipe, OrElsePipe, RouterLink, ClrIcon, ClrBadge],
  selector: 'app-devices-offered-for-sale',
  styleUrl: './devices-offered-for-sale.css',
  templateUrl: './devices-offered-for-sale.html',
})
export class DevicesOfferedForSale {
  protected readonly visibleRowCount = 10;
  protected readonly expanded = signal(false);
  protected readonly devicesResource = inject(
    DeviceSearchControllerService,
  ).getDevicesOfferedForSaleResource();
  protected readonly devicesOfferedForSale = computed(() =>
    this.devicesResource.hasValue() ? this.devicesResource.value() : [],
  );
  protected readonly hiddenRowCount = computed(() =>
    Math.max(0, this.devicesOfferedForSale().length - this.visibleRowCount),
  );
}
