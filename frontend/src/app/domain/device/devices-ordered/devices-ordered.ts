import { Component, inject, OnInit, signal } from '@angular/core';
import { DeviceControllerService, DeviceOrderedDto } from '@api/device';
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
  devicesOrdered = signal<DeviceOrderedDto[]>([]);
  private devicesApi = inject(DeviceControllerService);

  ngOnInit(): void {
    this.devicesApi.getDevicesOrdered().subscribe((devices) => this.devicesOrdered.set(devices));
  }
}
