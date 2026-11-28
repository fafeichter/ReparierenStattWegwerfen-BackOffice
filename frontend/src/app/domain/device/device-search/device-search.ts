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
import {
  ClrCardModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrInputModule,
  ClrLoadingButtonModule,
  ClrLoadingModule,
} from '@clr/angular';
import { Router } from '@angular/router';
import { DeviceSearchControllerService } from '@api/device';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrIcon,
    ClrInputModule,
    FormsModule,
    ReactiveFormsModule,
    ClrCardModule,
    ClrLoadingModule,
    ClrLoadingButtonModule,
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
    const searchFields = ['deviceId', 'deviceSerialNumber', 'deviceUrl'];
    const filledCount = searchFields.filter((name) => {
      const value = control.get(name)?.value;
      return value !== null && value !== undefined && String(value).trim() !== '';
    }).length;

    // Error if more than one is filled out
    if (filledCount > 1) {
      return { tooManyProvided: true };
    }

    // Error if none is filled out
    if (filledCount === 0) {
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
      deviceUrl: new FormControl<string | null>(null, [
        Validators.minLength(12),
        Validators.pattern(/^https?:\/\/[^\s/$.?#].[^\s]*$/i),
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
      const serialNumberFormControl = this.deviceSearchForm.controls.deviceSerialNumber;
      const deviceSerialNumber = serialNumberFormControl.value;
      if (deviceSerialNumber !== null) {
        this.deviceSearchApi
          .findDeviceIdsBySerialNumber(deviceSerialNumber)
          .subscribe((deviceIds) => {
            if (deviceIds.length === 1) {
              this.router.navigate(['/devices', deviceIds[0]]);
              return;
            }
            if (deviceIds.length > 1) {
              serialNumberFormControl.setErrors({ multipleDevicesFound: true });
            } else {
              serialNumberFormControl.setErrors({ deviceNotFound: true });
            }
            serialNumberFormControl.markAsTouched();
            this.isSubmitting.set(false);
          });
      } else {
        const urlFormControl = this.deviceSearchForm.controls.deviceUrl;
        const deviceUrl = urlFormControl.value;
        if (deviceUrl !== null) {
          this.deviceSearchApi.findDeviceIdsByUrl(deviceUrl).subscribe((deviceIds) => {
            if (deviceIds.length === 1) {
              this.router.navigate(['/devices', deviceIds[0]]);
              return;
            }
            if (deviceIds.length > 1) {
              urlFormControl.setErrors({ multipleDevicesFound: true });
            } else {
              urlFormControl.setErrors({ deviceNotFound: true });
            }
            urlFormControl.markAsTouched();
            this.isSubmitting.set(false);
          });
        }
      }
    }
  }
}
