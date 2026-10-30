import { Component, inject, input, model, output, signal } from '@angular/core';
import {
  ClrCommonFormsModule,
  ClrDatepickerModule,
  ClrInputModule,
  ClrModalModule,
  ClrNumberInputModule,
} from '@clr/angular';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { DeviceSellingControllerService, DeviceSellingDto } from '@api/device';

@Component({
  imports: [
    ClrModalModule,
    ClrCommonFormsModule,
    ClrDatepickerModule,
    ClrInputModule,
    ClrNumberInputModule,
    DatePipe,
    ReactiveFormsModule,
  ],
  selector: 'app-selling-modal',
  styleUrl: './selling-modal.css',
  templateUrl: './selling-modal.html',
})
export class SellingModal {
  deviceId = input.required<number>();
  modalOpen = model.required<boolean>();
  modalCanceled = output<void>();
  isSubmitting = signal<boolean>(false);

  now = new Date();

  private formattedDate = new Intl.DateTimeFormat('de-DE', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  }).format(this.now);
  form = new FormGroup({
    sellingPrice: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
    sellingDate: new FormControl<string | null>(this.formattedDate, [
      Validators.required,
      (control) => {
        if (!control.value) return null;
        const [day, month, year] = control.value.split('.').map(Number);
        return new Date(year, month - 1, day) <= new Date() ? null : { futureDate: true };
      },
    ]),
  });
  private deviceSellingControllerService = inject(DeviceSellingControllerService);

  setSellingDetails() {
    this.isSubmitting.set(true);

    const [day, month, year] = this.form.controls.sellingDate.value!.split('.');
    const sellingDateIso = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;

    const dto: DeviceSellingDto = {
      sellingPrice: this.form.controls.sellingPrice.value!,
      sellingDate: sellingDateIso,
    };
    this.deviceSellingControllerService.setSellingDetails(this.deviceId(), dto).subscribe(() => {
      this.modalOpen.set(false);
      this.isSubmitting.set(false);
    });
  }
}
