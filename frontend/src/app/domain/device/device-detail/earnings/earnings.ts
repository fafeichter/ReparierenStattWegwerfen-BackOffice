import { Component, computed, inject, input } from '@angular/core';
import { ClrIcon } from '@clr/angular';
import { CurrencyPipe } from '@angular/common';
import { DeviceEarningsControllerService } from '@api/device';

@Component({
  imports: [ClrIcon, CurrencyPipe],
  selector: 'app-earnings',
  styleUrl: './earnings.css',
  templateUrl: './earnings.html',
})
export class Earnings {
  deviceId = input.required<number>();

  private readonly earningsResource = inject(
    DeviceEarningsControllerService,
  ).getDeviceEarningsResource(() => ({ deviceId: this.deviceId() }));

  readonly earnings = computed(() =>
    this.earningsResource.hasValue() ? this.earningsResource.value() : 0,
  );
}
