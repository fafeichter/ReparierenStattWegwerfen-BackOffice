import { Component, computed, inject, input } from '@angular/core';
import { DeviceDetailsControllerService } from '@api/device';
import { ClrCardModule, ClrCommonFormsModule, ClrFileInputModule, ClrIcon } from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  selector: 'app-buying',
  imports: [
    ClrIcon,
    CurrencyPipe,
    DatePipe,
    RouterLink,
    ClrCommonFormsModule,
    ClrFileInputModule,
    FormsModule,
    ReactiveFormsModule,
    ClrCardModule,
  ],
  templateUrl: './buying.html',
  styleUrl: './buying.css',
})
export class Buying {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  private readonly deviceBuyingResource = inject(
    DeviceDetailsControllerService,
  ).getDeviceBuyingDetailsResource(() => ({ deviceId: this.deviceId() }));

  readonly deviceBuying = computed(() =>
    this.deviceBuyingResource.hasValue() ? this.deviceBuyingResource.value() : undefined,
  );
}
