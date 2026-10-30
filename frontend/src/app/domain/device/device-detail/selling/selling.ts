import { Component, effect, inject, input, OnInit, signal } from '@angular/core';
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
  DeviceSellingAccessoriesDto,
  DeviceSellingAccessoriesFormDto,
  DeviceSellingDetailsDto,
  DeviceSellingInvoiceControllerService,
} from '@api/device';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/components/scrollspy/scrollspy';

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
export class Selling implements OnInit {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);
  deviceAccessories = signal<DeviceSellingAccessoriesDto | undefined>(undefined);
  invoiceNumber = signal<number | undefined>(undefined);

  modalOpen = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  accessoriesForm = new FormGroup({
    charger: new FormControl<boolean | null>(null, [Validators.required]),
    chargingCable: new FormControl<boolean | null>(null, [Validators.required]),
    originalPackaging: new FormControl<boolean | null>(null, [Validators.required]),
  });

  private deviceApi = inject(DeviceDetailsControllerService);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private deviceAccessoriesApi = inject(DeviceSellingAccessoriesControllerService);
  private deviceInvoiceApi = inject(DeviceSellingInvoiceControllerService);

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

  ngOnInit(): void {
    this.deviceApi
      .getDeviceSellingDetails(this.deviceId())
      .subscribe((data) => this.deviceSelling.set(data));
    this.deviceAccessoriesApi.getSellingAccessories(this.deviceId()).subscribe((data) => {
      this.deviceAccessories.set(data);
    });
    this.deviceInvoiceApi
      .getDeviceSellingInvoiceNumber(this.deviceId())
      .subscribe((saleInvoiceNumber) => {
        this.invoiceNumber.set(saleInvoiceNumber);
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
        this.deviceAccessories.update(() => {
          this.accessoriesForm.reset();
          this.isSubmitting.set(false);
          this.modalOpen.set(false);

          return {
            charger: deviceAccessories.charger,
            chargingCable: deviceAccessories.chargingCable,
            originalPackaging: deviceAccessories.originalPackaging,
          };
        });
      });
  }

  protected setBuyerToAsgoodasnew() {
    this.deviceBusinessPartnerApi.setBuyerToAsgoodasnew(this.deviceId()).subscribe((response) => {
      this.deviceSelling.update((deviceSelling) => {
        return {
          ...deviceSelling!,
          buyer: response,
        };
      });
    });
  }
}
