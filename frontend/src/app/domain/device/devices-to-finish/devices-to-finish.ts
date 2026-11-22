import {Component, computed, effect, inject, output, signal} from '@angular/core';
import {DeviceSearchControllerService} from '@api/device';
import {ClrDatagridModule, ClrIcon} from '@clr/angular';
import {OrElsePipe} from '../../../pipes/or-else-pipe';
import {RouterLink} from '@angular/router';

@Component({
  imports: [ClrDatagridModule, OrElsePipe, RouterLink, ClrIcon],
  selector: 'app-devices-to-finish',
  styleUrl: './devices-to-finish.css',
  templateUrl: './devices-to-finish.html',
})
export class DevicesToFinish {
  readonly devicesToFinishCount = output<number>();
  protected readonly visibleRowCount = 10;
  protected readonly expanded = signal(false);
  protected readonly devicesResource = inject(DeviceSearchControllerService).getDevicesToFinishResource();
  protected readonly devicesToFinish = computed(() => this.devicesResource.hasValue() ? this.devicesResource.value() : []);
  protected readonly hiddenRowCount = computed(() => Math.max(0, this.devicesToFinish().length - this.visibleRowCount));

  constructor() {
    effect(() => {
      if (this.devicesResource.hasValue()) {
        this.devicesToFinishCount.emit(this.devicesResource.value().length);
      }
    });
  }
}
