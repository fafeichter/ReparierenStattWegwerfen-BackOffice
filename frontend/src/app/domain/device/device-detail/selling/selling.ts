import { Component, computed, effect, inject, input, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCheckboxModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  DeviceBusinessPartnerControllerService,
  DeviceDetailsControllerService,
  DeviceSellingAccessoriesControllerService,
  DeviceSellingAccessoriesFormDto,
  DeviceSellingInvoiceControllerService,
} from '@api/device';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  selector: 'app-selling',
  imports: [
    ClrIcon,
    CurrencyPipe,
    DatePipe,
    OrElsePipe,
    RouterLink,
    ClrCommonFormsModule,
    ClrModalModule,
    ClrTextareaModule,
    ReactiveFormsModule,
    ClrCheckboxModule,
    ClrCardModule,
  ],
  templateUrl: './selling.html',
  styleUrl: './selling.css',
})
export class Selling {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  modalOpen = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  accessoriesForm = new FormGroup({
    charger: new FormControl<boolean | null>(null, [Validators.required]),
    chargingCable: new FormControl<boolean | null>(null, [Validators.required]),
    originalPackaging: new FormControl<boolean | null>(null, [Validators.required]),
  });

  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private deviceAccessoriesApi = inject(DeviceSellingAccessoriesControllerService);

  private readonly deviceSellingResource = inject(
    DeviceDetailsControllerService,
  ).getDeviceSellingDetailsResource(() => ({ deviceId: this.deviceId() }));
  readonly deviceSelling = computed(() =>
    this.deviceSellingResource.hasValue() ? this.deviceSellingResource.value() : undefined,
  );
  private readonly deviceAccessoriesResource =
    this.deviceAccessoriesApi.getSellingAccessoriesResource(() => ({ deviceId: this.deviceId() }));
  readonly deviceAccessories = computed(() =>
    this.deviceAccessoriesResource.hasValue() ? this.deviceAccessoriesResource.value() : undefined,
  );
  private readonly invoiceNumberResource = inject(
    DeviceSellingInvoiceControllerService,
  ).getDeviceSellingInvoiceNumberResource(() => ({ deviceId: this.deviceId() }));
  readonly invoiceNumber = computed(() =>
    this.invoiceNumberResource.hasValue() ? this.invoiceNumberResource.value() : undefined,
  );

  constructor() {
    effect(() => {
      const accessories = this.deviceAccessories();
      if (accessories) {
        this.accessoriesForm.patchValue({
          charger: accessories.charger || false,
          chargingCable: accessories.chargingCable || false,
          originalPackaging: accessories.originalPackaging || false,
        });
      }
    });
  }

  protected updateAccessories() {
    const deviceAccessories: DeviceSellingAccessoriesFormDto = {
      charger: this.accessoriesForm.controls.charger.value!,
      chargingCable: this.accessoriesForm.controls.chargingCable.value!,
      originalPackaging: this.accessoriesForm.controls.originalPackaging.value!,
    };

    this.deviceAccessoriesApi
      .updateSellingAccessories(this.deviceId(), deviceAccessories)
      .subscribe(() => {
        this.accessoriesForm.reset();
        this.isSubmitting.set(false);
        this.modalOpen.set(false);
        this.deviceAccessoriesResource.value.set({
          charger: deviceAccessories.charger,
          chargingCable: deviceAccessories.chargingCable,
          originalPackaging: deviceAccessories.originalPackaging,
        });
      });
  }

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
