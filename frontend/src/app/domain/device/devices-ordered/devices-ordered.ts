import { Component, inject, OnInit, output, signal } from '@angular/core';
import { DeviceOrderedDto, DeviceSearchControllerService } from '@api/device';
import { ClrDatagridModule } from '@clr/angular';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../pipes/or-else-pipe';

@Component({
  imports: [ClrDatagridModule, RouterLink, DatePipe, OrElsePipe],
  selector: 'app-devices-ordered',
  styleUrl: './devices-ordered.css',
  templateUrl: './devices-ordered.html',
})
export class DevicesOrdered implements OnInit {
  devicesOrderedCount = output<number>();

  devicesOrdered = signal<DeviceOrderedDto[]>([]);
  isLoading = signal<boolean>(true);
  private deviceSearchApi = inject(DeviceSearchControllerService);

  ngOnInit(): void {
    this.isLoading.set(true);
    this.deviceSearchApi.getDevicesOrdered().subscribe((devices) => {
      this.isLoading.set(false);
      this.devicesOrdered.set(devices);
      this.devicesOrderedCount.emit(devices.length);
    });
  }
}
