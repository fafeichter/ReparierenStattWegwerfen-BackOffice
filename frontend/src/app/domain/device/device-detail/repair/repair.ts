import { Component, computed, effect, inject, input, OnInit, signal } from '@angular/core';
import {
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { CurrencyPipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  DeviceDefectsControllerService,
  DeviceDefectsDetailsDto,
  DeviceDefectsDto,
  DeviceSparePartDto,
  DeviceSparePartsControllerService,
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

  defectsForm = new FormGroup({
    originalDefect: new FormControl<string | null>(null, [Validators.required]),
    confirmedDefect: new FormControl<string | null>(null),
  });

  private defectsApi = inject(DeviceDefectsControllerService);
  private sparePartsApi = inject(DeviceSparePartsControllerService);

  constructor() {
    effect(() => {
      const defects = this.deviceDefects();
      if (defects) {
        this.defectsForm.patchValue({
          originalDefect: defects.reportedDefect,
          confirmedDefect: defects.diagnosedDefect,
        });
      }
    });
  }

  ngOnInit(): void {
    this.defectsApi
      .getDeviceDefectDetails(this.deviceId())
      .subscribe((data) => this.deviceDefects.set(data));
    this.sparePartsApi
      .getDeviceSpareParts(this.deviceId())
      .subscribe((data) => this.deviceSpareParts.set(data));
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
      this.deviceDefects.update((currentValue) => {
        return {
          ...currentValue!,
          diagnosedDefect: 'WIE ANGEGEBEN',
        };
      });
    });
  }
}
