import { Component, computed, effect, inject, output, signal } from '@angular/core';
import { DeviceSearchControllerService } from '@api/device';
import { ClrDatagridModule, ClrIcon } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrDatagridModule, DatePipe, OrElsePipe, RouterLink, ClrIcon],
  selector: 'app-devices-ordered',
  styleUrl: './devices-ordered.css',
  templateUrl: './devices-ordered.html',
})
export class DevicesOrdered {
  readonly devicesOrderedCount = output<number>();
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

  constructor() {
    effect(() => {
      if (this.devicesResource.hasValue()) {
        this.devicesOrderedCount.emit(this.devicesResource.value().length);
      }
    });
  }
}
