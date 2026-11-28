import { Component, computed, inject, signal } from '@angular/core';
import { DeviceSearchControllerService } from '@api/device';
import { ClrBadge, ClrDatagridModule, ClrIcon, ClrIconModule } from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrDatagridModule, OrElsePipe, RouterLink, ClrIcon, ClrBadge, ClrIconModule],
  selector: 'app-devices-to-finish',
  styleUrl: './devices-to-finish.css',
  templateUrl: './devices-to-finish.html',
})
export class DevicesToFinish {
  protected readonly visibleRowCount = 10;
  protected readonly expanded = signal(false);
  protected readonly devicesResource = inject(
    DeviceSearchControllerService,
  ).getDevicesToFinishResource();
  protected readonly devicesToFinish = computed(() =>
    this.devicesResource.hasValue() ? this.devicesResource.value() : [],
  );
  protected readonly hiddenRowCount = computed(() =>
    Math.max(0, this.devicesToFinish().length - this.visibleRowCount),
  );
  // only the first rows are rendered until the user expands the list
  protected readonly visibleDevicesToFinish = computed(() =>
    this.expanded()
      ? this.devicesToFinish()
      : this.devicesToFinish().slice(0, this.visibleRowCount),
  );
}
