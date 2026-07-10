import { Component, effect, inject, input, OnInit, signal } from '@angular/core';
import {
  ClrCheckboxModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  DeviceDetailsControllerService,
  DeviceSellingAccessoriesControllerService,
  DeviceSellingAccessoriesDto,
  DeviceSellingAccessoriesFormDto,
  DeviceSellingDetailsDto,
} from '@api/device';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

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
  ],
  templateUrl: './selling.html',
  styleUrl: './selling.css',
})
export class Selling implements OnInit {
  deviceId = input.required<number>();

  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);
  deviceAccessories = signal<DeviceSellingAccessoriesDto | undefined>(undefined);

  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  accessoriesForm = new FormGroup({
    charger: new FormControl<boolean | null>(null, [Validators.required]),
    chargingCable: new FormControl<boolean | null>(null, [Validators.required]),
    originalPackaging: new FormControl<boolean | null>(null, [Validators.required]),
  });

  private api = inject(DeviceDetailsControllerService);
  private accessoriesApi = inject(DeviceSellingAccessoriesControllerService);

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
    this.api
      .getDeviceSellingDetails(this.deviceId())
      .subscribe((data) => this.deviceSelling.set(data));
    this.accessoriesApi.getSellingAccessories(this.deviceId()).subscribe((data) => {
      this.deviceAccessories.set(data);
    });
  }

  protected updateAccessories() {
    const deviceAccessories: DeviceSellingAccessoriesFormDto = {
      charger: this.accessoriesForm.controls.charger.value!,
      chargingCable: this.accessoriesForm.controls.chargingCable.value!,
      originalPackaging: this.accessoriesForm.controls.originalPackaging.value!,
    };

    this.accessoriesApi
      .updateSellingAccessories(this.deviceId(), deviceAccessories)
      .subscribe((data) => {
        this.deviceAccessories.update((currentValue) => {
          this.accessoriesForm.reset();
          this.isSubmitting.set(false);
          this.modalOpened.set(false);

          return {
            charger: deviceAccessories.charger,
            chargingCable: deviceAccessories.chargingCable,
            originalPackaging: deviceAccessories.originalPackaging,
          };
        });
      });
  }
}
