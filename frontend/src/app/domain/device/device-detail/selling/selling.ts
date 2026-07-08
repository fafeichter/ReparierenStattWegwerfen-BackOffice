import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ClrIcon } from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { DeviceDetailsControllerService, DeviceSellingDetailsDto } from '@api/device';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-selling',
  imports: [ClrIcon, CurrencyPipe, DatePipe, OrElsePipe, RouterLink],
  templateUrl: './selling.html',
  styleUrl: './selling.css',
})
export class Selling implements OnInit {
  deviceId = input.required<number>();

  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);

  private api = inject(DeviceDetailsControllerService);

  ngOnInit(): void {
    this.api
      .getDeviceSellingDetails(this.deviceId())
      .subscribe((data) => this.deviceSelling.set(data));
  }
}
