import { Component, computed, inject, signal } from '@angular/core';
import { DeviceSearchControllerService } from '@api/device';
import { ClrBadge, ClrDatagridModule, ClrIcon, ClrIconModule } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrDatagridModule, DatePipe, OrElsePipe, RouterLink, ClrIcon, ClrBadge, ClrIconModule],
  selector: 'app-devices-ordered',
  styleUrl: './devices-ordered.css',
  templateUrl: './devices-ordered.html',
})
export class DevicesOrdered {
  protected readonly visibleRowCount = 10;
  protected readonly expanded = signal(false);
  protected readonly devicesResource = inject(
    DeviceSearchControllerService,
  ).getDevicesOrderedResource();
  protected readonly devicesOrdered = computed(() =>
    this.devicesResource.hasValue() ? this.devicesResource.value() : [],
  );
  protected readonly hiddenRowCount = computed(() =>
    Math.max(0, this.devicesOrdered().length - this.visibleRowCount),
  );
  // only the first rows are rendered until the user expands the list
  protected readonly visibleDevicesOrdered = computed(() =>
    this.expanded() ? this.devicesOrdered() : this.devicesOrdered().slice(0, this.visibleRowCount),
  );
}
