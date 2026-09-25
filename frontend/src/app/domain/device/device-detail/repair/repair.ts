import { Component, computed, effect, inject, input, OnInit, signal } from '@angular/core';
import {
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrIcon,
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
  DeviceDefectsDetailsDto,
  DeviceDefectsDto,
  DeviceSparePartDto,
  DeviceSparePartsControllerService,
  NamedIdDto,
} from '@api/device';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

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
  ],
  templateUrl: './repair.html',
  styleUrl: './repair.css',
})
export class Repair implements OnInit {
  deviceId = input.required<number>();

  deviceDefects = signal<DeviceDefectsDetailsDto | undefined>(undefined);
  deviceSpareParts = signal<DeviceSparePartDto[]>([]);
  totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });

  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  sparePartEditModeActive = signal<boolean>(false);
  form = new FormGroup({
    sparePartId: new FormControl<number | null>(null, [Validators.required]),
    priceNetto: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
  });

  defectsForm = new FormGroup({
    originalDefect: new FormControl<string | null>(null, [Validators.required]),
    confirmedDefect: new FormControl<string | null>(null),
  });

  availableSpareParts = signal<NamedIdDto[]>([]);

  private defectsApi = inject(DeviceDefectsControllerService);
  private deviceSparePartsApi = inject(DeviceSparePartsControllerService);

  constructor() {
    effect(() => {
      const defects = this.deviceDefects();
      if (defects) {
        this.defectsForm.patchValue({
          originalDefect: defects.reportedDefect,
          confirmedDefect: defects.diagnosedDefect,
        });
      }

      if (this.sparePartEditModeActive()) {
        this.deviceSparePartsApi
          .getAvailableSparePartsForDevice(this.deviceId())
          .subscribe((availableSpareParts) => this.availableSpareParts.set(availableSpareParts));
      }
    });
  }

  ngOnInit(): void {
    this.defectsApi
      .getDeviceDefectDetails(this.deviceId())
      .subscribe((data) => this.deviceDefects.set(data));
    this.deviceSparePartsApi
      .getDeviceSpareParts(this.deviceId())
      .subscribe((deviceSpareParts) => this.deviceSpareParts.set(deviceSpareParts));
  }

  updateDefects() {
    const defects: DeviceDefectsDto = {
      reportedDefect: this.defectsForm.controls.originalDefect.value!,
      diagnosedDefect: this.defectsForm.controls.confirmedDefect.value!,
    };

    this.defectsApi.updateDefects(this.deviceId(), defects).subscribe(() => {
      this.deviceDefects.update(() => {
        this.defectsForm.reset();
        this.isSubmitting.set(false);
        this.modalOpened.set(false);

        return {
          reportedDefect: defects.reportedDefect,
          diagnosedDefect: defects.diagnosedDefect,
        };
      });
    });
  }

  confirmOriginalDefect() {
    this.defectsApi.confirmOriginalDefect(this.deviceId()).subscribe(() => {
      this.deviceDefects.update((deviceDefects) => {
        return {
          ...deviceDefects!,
          diagnosedDefect: 'WIE ANGEGEBEN',
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
        this.deviceSparePartsApi
          .getDeviceSpareParts(this.deviceId())
          .subscribe((deviceSpareParts) => this.deviceSpareParts.set(deviceSpareParts));
      });
  }

  deleteDeviceSparePart(oldDeviceSparePartId: number) {
    if (confirm('Do you really want to remove this spare part?')) {
      this.deviceSparePartsApi
        .deleteDeviceSparePart(this.deviceId(), oldDeviceSparePartId)
        .subscribe(() => {
          this.form.reset();
          this.deviceSpareParts.update((deviceSparePart) => {
            return deviceSparePart.filter(
              (deviceSparePart) => deviceSparePart.deviceSparePartId !== oldDeviceSparePartId,
            );
          });
        });
    }
  }
}
