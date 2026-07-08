import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBuyingDetailsDto, DeviceDetailsControllerService } from '@api/device';
import { ClrIcon } from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-buying',
  imports: [ClrIcon, CurrencyPipe, DatePipe, RouterLink],
  templateUrl: './buying.html',
  styleUrl: './buying.css',
})
export class Buying implements OnInit {
  deviceId = input.required<number>();

  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);

  private api = inject(DeviceDetailsControllerService);

  ngOnInit(): void {
    this.api
      .getDeviceBuyingDetails(this.deviceId())
      .subscribe((data) => this.deviceBuying.set(data));
  }
}
