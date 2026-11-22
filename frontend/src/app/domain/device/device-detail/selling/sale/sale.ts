import { Component, computed, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ClrIcon } from '@clr/angular';
import {
  DeviceBusinessPartnerControllerService,
  DeviceDetailsControllerService,
} from '@api/device';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../../../pipes/or-else-pipe';

@Component({
  imports: [RouterLink, ClrIcon, CurrencyPipe, DatePipe, OrElsePipe],
  selector: 'app-sale',
  styleUrl: './sale.css',
  templateUrl: './sale.html',
})
export class Sale {
  deviceId = input.required<number>();

  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);

  private readonly deviceSellingResource = inject(
    DeviceDetailsControllerService,
  ).getDeviceSellingDetailsResource(() => ({ deviceId: this.deviceId() }));
  readonly deviceSelling = computed(() =>
    this.deviceSellingResource.hasValue() ? this.deviceSellingResource.value() : undefined,
  );

  protected setBuyerToAsgoodasnew() {
    this.deviceBusinessPartnerApi
      .setBuyerToAsGoodAsNew(this.deviceId())
      .subscribe((deviceBuyer) => {
        this.deviceSellingResource.value.update((deviceSelling) => {
          return {
            ...deviceSelling!,
            buyer: deviceBuyer,
          };
        });
      });
  }

  protected unsetBuyer() {
    this.deviceBusinessPartnerApi.unsetBuyer(this.deviceId()).subscribe(() => {
      this.deviceSellingResource.value.update((deviceSelling) => {
        return {
          ...deviceSelling!,
          buyer: undefined,
        };
      });
    });
  }
}
