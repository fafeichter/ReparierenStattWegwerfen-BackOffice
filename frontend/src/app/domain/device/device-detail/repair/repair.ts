import { Component, computed, effect, inject, input, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrIcon,
  ClrLabel,
  ClrModalModule,
  ClrNumberInputModule,
  ClrSelectModule,
  ClrTextareaModule,
} from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  CreateDeviceSparePartDto,
  DeviceDefectsControllerService,
  DeviceDefectsDto,
  DeviceSparePartsControllerService,
} from '@api/device';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  selector: 'app-repair',
  imports: [
    ClrDatagridModule,
    CurrencyPipe,
    OrElsePipe,
    ClrIcon,
    ClrCommonFormsModule,
    ClrModalModule,
    ClrTextareaModule,
    ReactiveFormsModule,
    ClrSelectModule,
    ClrNumberInputModule,
    DatePipe,
    ClrLabel,
    ClrCardModule,
  ],
  templateUrl: './repair.html',
  styleUrl: './repair.css',
})
export class Repair {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  modalOpen = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  sparePartEditModeActive = signal<boolean>(false);
  form = new FormGroup({
    sparePartId: new FormControl<number | null>(null, [Validators.required]),
    priceNetto: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
  });

  defectsForm = new FormGroup({
    reportedDefect: new FormControl<string | null>(null, [Validators.required]),
    correctedDefect: new FormControl<string | null>(null),
  });

  private defectsApi = inject(DeviceDefectsControllerService);
  private deviceSparePartsApi = inject(DeviceSparePartsControllerService);
  private readonly deviceDefectsResource = this.defectsApi.getDeviceDefectDetailsResource(() => ({
    deviceId: this.deviceId(),
  }));
  readonly deviceDefects = computed(() =>
    this.deviceDefectsResource.hasValue() ? this.deviceDefectsResource.value() : undefined,
  );
  private readonly deviceSparePartsResource = this.deviceSparePartsApi.getDeviceSparePartsResource(
    () => ({ deviceId: this.deviceId() }),
  );
  readonly deviceSpareParts = computed(() =>
    this.deviceSparePartsResource.hasValue() ? this.deviceSparePartsResource.value() : [],
  );
  readonly totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });
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
      const defects = this.deviceDefects();
      if (defects) {
        this.defectsForm.patchValue({
          reportedDefect: defects.reportedDefect,
          correctedDefect: defects.correctedDefect,
        });
      }
    });

    effect(() => {
      if (this.sparePartEditModeActive()) {
        this.form.reset();
      }
    });
  }

  updateDefects() {
    const defects: DeviceDefectsDto = {
      reportedDefect: this.defectsForm.controls.reportedDefect.value!,
      correctedDefect: this.defectsForm.controls.correctedDefect.value!,
    };

    this.defectsApi.updateDefects(this.deviceId(), defects).subscribe(() => {
      this.defectsForm.reset();
      this.isSubmitting.set(false);
      this.modalOpen.set(false);
      this.deviceDefectsResource.value.set({
        reportedDefect: defects.reportedDefect,
        reportedDefectConfirmed: !defects.correctedDefect,
        correctedDefect: defects.correctedDefect,
      });
    });
  }

  confirmReportedDefect() {
    this.defectsApi.confirmReportedDefect(this.deviceId()).subscribe(() => {
      this.deviceDefectsResource.value.update((deviceDefects) => {
        return {
          ...deviceDefects!,
          reportedDefectConfirmed: true,
          correctedDefect: undefined,
        };
      });
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
