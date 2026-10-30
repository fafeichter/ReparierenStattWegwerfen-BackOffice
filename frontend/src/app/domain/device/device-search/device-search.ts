import { Component, inject, signal } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ClrCommonFormsModule, ClrIcon, ClrInputModule } from '@clr/angular';
import { Router } from '@angular/router';
import { DeviceSearchControllerService } from '@api/device';
import { SerialNumber } from '../serial-number';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrIcon,
    ClrInputModule,
    FormsModule,
    ReactiveFormsModule,
    SerialNumber,
  ],
  selector: 'app-device-search',
  styleUrl: './device-search.css',
  templateUrl: './device-search.html',
})
export class DeviceSearch {
  isSubmitting = signal<boolean>(false);
  private router = inject(Router);
  private deviceSearchApi = inject(DeviceSearchControllerService);

  exactlyOneRequiredValidator: ValidatorFn = (
    control: AbstractControl,
  ): ValidationErrors | null => {
    const formGroup = control as FormGroup;
    const deviceId = formGroup.get('deviceId')?.value;
    const deviceSerialNumber = formGroup.get('deviceSerialNumber')?.value;

    const hasDeviceId = deviceId !== null && deviceId !== '' && deviceId !== undefined;
    const hasDeviceSerial =
      deviceSerialNumber !== null && deviceSerialNumber !== '' && deviceSerialNumber !== undefined;

    // Error if both are filled out
    if (hasDeviceId && hasDeviceSerial) {
      return { bothProvided: true };
    }

    // Error if neither is filled out
    if (!hasDeviceId && !hasDeviceSerial) {
      return { noneProvided: true };
    }

    // Valid if exactly one is provided
    return null;
  };

  deviceSearchForm = new FormGroup(
    {
      deviceId: new FormControl<string | null>(null, [
        Validators.minLength(1),
        Validators.pattern(/^[1-9]\d*$/),
      ]),
      deviceSerialNumber: new FormControl<string | null>(null, [
        Validators.minLength(3),
        Validators.pattern(/^[a-zA-Z0-9]+$/),
      ]),
    },
    { validators: [this.exactlyOneRequiredValidator] },
  );

  searchDevice() {
    this.isSubmitting.set(true);
    const deviceId = this.deviceSearchForm.controls.deviceId.value;
    if (deviceId !== null) {
      this.router.navigate(['/devices', deviceId]);
    } else {
      const deviceSerialNumber = this.deviceSearchForm.controls.deviceSerialNumber.value;
      if (deviceSerialNumber !== null) {
        this.deviceSearchApi
          .findDeviceIdBySerialNumber(deviceSerialNumber)
          .subscribe((deviceId) => {
            if (deviceId != null) {
              this.router.navigate(['/devices', deviceId]);
            } else {
              this.resetForm();
            }
          });
      }
    }
  }

  private resetForm() {
    this.deviceSearchForm.reset();
    this.isSubmitting.set(false);
  }
}
