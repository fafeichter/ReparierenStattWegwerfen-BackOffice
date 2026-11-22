import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { CreateDeviceSparePartDto, DeviceSparePartsControllerService } from '@api/device';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClrDatagridModule, ClrIcon, ClrNumberInputModule, ClrSelectModule } from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  imports: [
    ClrDatagridModule,
    ClrIcon,
    CurrencyPipe,
    DatePipe,
    ReactiveFormsModule,
    ClrSelectModule,
    ClrNumberInputModule,
  ],
  selector: 'app-spare-parts',
  styleUrl: './spare-parts.css',
  templateUrl: './spare-parts.html',
})
export class SpareParts {
  form = new FormGroup({
    sparePartId: new FormControl<number | null>(null, [Validators.required]),
    priceNetto: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
  });

  deviceId = input.required<number>();

  modalOpen = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  sparePartEditModeActive = signal<boolean>(false);
  readonly totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });
  private deviceSparePartsApi = inject(DeviceSparePartsControllerService);
  private readonly deviceSparePartsResource = this.deviceSparePartsApi.getDeviceSparePartsResource(
    () => ({ deviceId: this.deviceId() }),
  );
  readonly deviceSpareParts = computed(() =>
    this.deviceSparePartsResource.hasValue() ? this.deviceSparePartsResource.value() : [],
  );
  // only loaded (and re-loaded) while the spare part form is open
  private readonly availableSparePartsResource =
    this.deviceSparePartsApi.getAvailableSparePartsForDeviceResource(() =>
      this.sparePartEditModeActive() ? { deviceId: this.deviceId() } : undefined,
    );
  readonly availableSpareParts = computed(() =>
    this.availableSparePartsResource.hasValue() ? this.availableSparePartsResource.value() : [],
  );

  constructor() {
    effect(() => {
      if (this.sparePartEditModeActive()) {
        this.form.reset();
      }
    });
  }

  saveSparePart() {
    const newDeviceSparePart: CreateDeviceSparePartDto = {
      sparePartId: this.form.controls.sparePartId.value!,
      priceNetto: this.form.controls.priceNetto.value!,
    };

    this.deviceSparePartsApi
      .addDeviceSparePart(this.deviceId(), newDeviceSparePart)
      .subscribe(() => {
        this.sparePartEditModeActive.set(false);
        this.form.reset();
        this.deviceSparePartsResource.reload();
      });
  }

  deleteDeviceSparePart(oldDeviceSparePartId: number) {
    this.sparePartEditModeActive.set(false);
    this.form.reset();

    if (confirm('Do you really want to remove this spare part?')) {
      this.deviceSparePartsApi
        .deleteDeviceSparePart(this.deviceId(), oldDeviceSparePartId)
        .subscribe(() => {
          this.form.reset();
          this.deviceSparePartsResource.value.update((deviceSpareParts) =>
            deviceSpareParts?.filter(
              (deviceSparePart) => deviceSparePart.deviceSparePartId !== oldDeviceSparePartId,
            ),
          );
        });
    }
  }
}
