import { Component, computed, effect, inject, input, model, output, signal } from '@angular/core';
import { ClrCommonFormsModule, ClrIcon, ClrModalModule, ClrTextareaModule } from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DeviceDefectsControllerService, DeviceDefectsDto } from '@api/device';
import { Autofocus } from '../../../../../../clarity-addons/input/autofocus';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrIcon,
    ClrModalModule,
    ClrTextareaModule,
    FormsModule,
    ReactiveFormsModule,
    Autofocus,
  ],
  selector: 'app-defects-modal',
  styleUrl: './defects-modal.css',
  templateUrl: './defects-modal.html',
})
export class DefectsModal {
  deviceId = input.required<number>();

  modalOpen = model.required<boolean>();
  readonly saved = output<void>();
  isSubmitting = signal<boolean>(false);

  defectsForm = new FormGroup({
    reportedDefect: new FormControl<string | null>(null, [Validators.required]),
    correctedDefect: new FormControl<string | null>(null),
  });

  private defectsApi = inject(DeviceDefectsControllerService);
  private readonly deviceDefectsResource = this.defectsApi.getDeviceDefectDetailsResource(() =>
    this.modalOpen() ? { deviceId: this.deviceId() } : undefined,
  );
  readonly deviceDefects = computed(() =>
    this.deviceDefectsResource.hasValue() ? this.deviceDefectsResource.value() : undefined,
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
  }

  updateDefects() {
    this.isSubmitting.set(true);

    const defects: DeviceDefectsDto = {
      reportedDefect: this.defectsForm.controls.reportedDefect.value!,
      correctedDefect: this.defectsForm.controls.correctedDefect.value!,
    };

    this.defectsApi.updateDefects(this.deviceId(), defects).subscribe(() => {
      this.defectsForm.reset();
      this.isSubmitting.set(false);
      this.modalOpen.set(false);
      this.deviceDefectsResource.value.set(defects);
      this.saved.emit();
    });
  }
}
