import { Component, inject, OnInit, output, signal } from '@angular/core';
import { ClrDatagridModule } from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import { DeviceControllerService, DeviceToFinishDto } from '@api/device';

@Component({
  imports: [ClrDatagridModule, OrElsePipe, RouterLink],
  selector: 'app-devices-to-finish',
  styleUrl: './devices-to-finish.css',
  templateUrl: './devices-to-finish.html',
})
export class DevicesToFinish implements OnInit {
  devicesToFinishCount = output<number>();

  devicesToFinish = signal<DeviceToFinishDto[]>([]);
  private devicesApi = inject(DeviceControllerService);

  ngOnInit(): void {
    this.devicesApi.getDevicesToFinish().subscribe((devices) => {
      this.devicesToFinish.set(devices);
      this.devicesToFinishCount.emit(devices.length);
    });
  }
}
