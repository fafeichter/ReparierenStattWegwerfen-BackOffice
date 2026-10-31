import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ClrIcon } from '@clr/angular';
import { CurrencyPipe } from '@angular/common';
import { DeviceEarningsControllerService } from '@api/device';

@Component({
  imports: [ClrIcon, CurrencyPipe],
  selector: 'app-earnings',
  styleUrl: './earnings.css',
  templateUrl: './earnings.html',
})
export class Earnings implements OnInit {
  deviceId = input.required<number>();
  earnings = signal<number>(0);

  private deviceEarningsApi = inject(DeviceEarningsControllerService);

  ngOnInit(): void {
    this.deviceEarningsApi.getDeviceEarnings(this.deviceId()).subscribe((earnings) => {
      this.earnings.set(earnings);
    });
  }
}
