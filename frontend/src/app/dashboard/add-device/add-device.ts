import {Component, effect, inject, signal} from '@angular/core';
import {ClrFormsModule, ClrIcon, ClrIconModule, ClrModalModule} from "@clr/angular";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ModelControllerService} from '@api/model';

@Component({
  selector: 'app-add-device',
  imports: [
    ClrIcon,
    ClrIconModule,
    ClrModalModule,
    ClrFormsModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './add-device.html',
  styleUrl: './add-device.css',
})
export class AddDevice {

  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  foundModels = signal<string[]>([]);

  form = new FormGroup({
    url: new FormControl('', [
      Validators.required,
      Validators.pattern(/https?:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-0()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&\/=]*)/)
    ]),

  });

  private api = inject(ModelControllerService);

  constructor() {
    effect(() => {
      if (this.modalOpened()) {
        this.resetFormState();
      }
    });
  }

  addDevice() {
    this.foundModels.set([]);

    // 1. If form is invalid, trigger Clarity's error UI by marking it touched
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);

    const adUrl = this.form.controls.url.value ?? ''

    this.api.getModelNumberFromAdUrl(adUrl).subscribe(data => {
      this.foundModels.set(data);
      this.isSubmitting.set(false);
    });
  }

  private resetFormState() {
    this.form.reset({url: ''});
    this.isSubmitting.set(false);
  }
}
