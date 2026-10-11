import { Component, inject, OnInit, output, signal } from '@angular/core';
import { ClrDatagridModule } from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import { DeviceStatusControllerService, DeviceToFinishDto } from '@api/device';

@Component({
  imports: [ClrDatagridModule, OrElsePipe, RouterLink],
  selector: 'app-devices-to-finish',
  styleUrl: './devices-to-finish.css',
  templateUrl: './devices-to-finish.html',
})
export class DevicesToFinish implements OnInit {
  devicesToFinishCount = output<number>();

  devicesToFinish = signal<DeviceToFinishDto[]>([]);
  private deviceStatusApi = inject(DeviceStatusControllerService);

  ngOnInit(): void {
    this.deviceStatusApi.getDevicesToFinish().subscribe((devices) => {
      this.devicesToFinish.set(devices);
      this.devicesToFinishCount.emit(devices.length);
    });
  }
}
