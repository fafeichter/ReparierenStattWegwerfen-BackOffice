import { Component, computed, effect, inject, input, model, output, signal } from '@angular/core';
import {
  ClrCheckboxModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrLoadingButtonModule,
  ClrLoadingModule,
  ClrModalModule,
} from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  DeviceSellingAccessoriesControllerService,
  DeviceSellingAccessoriesFormDto,
} from '@api/device';

@Component({
  imports: [
    ClrCheckboxModule,
    ClrCommonFormsModule,
    ClrIcon,
    ClrModalModule,
    FormsModule,
    ReactiveFormsModule,
    ClrLoadingModule,
    ClrLoadingButtonModule,
  ],
  selector: 'app-selling-accessories-modal',
  styleUrl: './selling-accessories-modal.css',
  templateUrl: './selling-accessories-modal.html',
})
export class SellingAccessoriesModal {
  deviceId = input.required<number>();
  modalOpen = model.required<boolean>();
  saved = output<void>();
  isSubmitting = signal<boolean>(false);

  accessoriesForm = new FormGroup({
    charger: new FormControl<boolean | null>(null, [Validators.required]),
    chargingCable: new FormControl<boolean | null>(null, [Validators.required]),
    originalPackaging: new FormControl<boolean | null>(null, [Validators.required]),
  });
  private deviceAccessoriesApi = inject(DeviceSellingAccessoriesControllerService);

  private readonly deviceAccessoriesResource =
    this.deviceAccessoriesApi.getSellingAccessoriesResource(() =>
      this.modalOpen() ? { deviceId: this.deviceId() } : undefined,
    );
  readonly deviceAccessories = computed(() =>
    this.deviceAccessoriesResource.hasValue() ? this.deviceAccessoriesResource.value() : undefined,
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
    this.isSubmitting.set(true);

    const deviceAccessories: DeviceSellingAccessoriesFormDto = {
      charger: this.accessoriesForm.controls.charger.value!,
      chargingCable: this.accessoriesForm.controls.chargingCable.value!,
      originalPackaging: this.accessoriesForm.controls.originalPackaging.value!,
    };

    this.deviceAccessoriesApi
      .updateSellingAccessories(this.deviceId(), deviceAccessories)
      .subscribe(() => {
        this.isSubmitting.set(false);
        this.modalOpen.set(false);
        // keep the form in sync for the next time the modal opens
        this.deviceAccessoriesResource.value.set(deviceAccessories);
        this.saved.emit();
      });
  }
}
