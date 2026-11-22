import { Component, computed, inject, input, signal } from '@angular/core';
import { ClrIcon, ClrLabel } from '@clr/angular';
import { OrElsePipe } from '../../../../../pipes/or-else-pipe';
import { DeviceDefectsControllerService } from '@api/device';
import { DefectsModal } from './defects-modal/defects-modal';

@Component({
  imports: [ClrIcon, ClrLabel, OrElsePipe, DefectsModal],
  selector: 'app-defects',
  styleUrl: './defects.css',
  templateUrl: './defects.html',
})
export class Defects {
  deviceId = input.required<number>();
  isSubmitting = signal<boolean>(false);

  modalOpen = signal<boolean>(false);

  private defectsApi = inject(DeviceDefectsControllerService);
  private readonly deviceDefectsResource = this.defectsApi.getDeviceDefectDetailsResource(() => ({
    deviceId: this.deviceId(),
  }));
  readonly deviceDefects = computed(() =>
    this.deviceDefectsResource.hasValue() ? this.deviceDefectsResource.value() : undefined,
  );

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
}
