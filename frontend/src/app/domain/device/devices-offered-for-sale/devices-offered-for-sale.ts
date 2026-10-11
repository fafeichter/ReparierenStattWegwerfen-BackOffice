import { Component, inject, OnInit, output, signal } from '@angular/core';
import { DeviceOfferedForSaleDto, DeviceStatusControllerService } from '@api/device';
import { ClrDatagridModule } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrDatagridModule, DatePipe, OrElsePipe, RouterLink],
  selector: 'app-devices-offered-for-sale',
  styleUrl: './devices-offered-for-sale.css',
  templateUrl: './devices-offered-for-sale.html',
})
export class DevicesOfferedForSale implements OnInit {
  devicesOfferedForSaleCount = output<number>();

  devicesOfferedForSale = signal<DeviceOfferedForSaleDto[]>([]);
  private deviceStatusApi = inject(DeviceStatusControllerService);

  ngOnInit(): void {
    this.deviceStatusApi.getDevicesOfferedForSale().subscribe((devices) => {
      this.devicesOfferedForSale.set(devices);
      this.devicesOfferedForSaleCount.emit(devices.length);
    });
  }
}
