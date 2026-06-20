import {Component, effect, ElementRef, inject, signal, ViewChild} from '@angular/core';
import {ClrFormsModule, ClrIcon, ClrIconModule, ClrModalModule} from "@clr/angular";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AiResponse, ModelControllerService} from '@api/model';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-add-device',
  imports: [
    ClrIcon,
    ClrIconModule,
    ClrModalModule,
    ClrFormsModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe
  ],
  templateUrl: './add-device.html',
  styleUrl: './add-device.css',
})
export class AddDevice {

  @ViewChild('input') urlInput!: ElementRef<HTMLInputElement>;

  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  found = signal<AiResponse | undefined>(undefined);

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

        setTimeout(() => {
          this.urlInput?.nativeElement.focus();
        }, 1);
      }
    });
  }

  addDevice() {
    this.found.set(undefined);

    // 1. If form is invalid, trigger Clarity's error UI by marking it touched
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);

    const adUrl = this.form.controls.url.value ?? ''

    this.api.getModelNumberFromAdUrl(adUrl).subscribe(data => {
      this.found.set(data);
      this.isSubmitting.set(false);
    });
  }

  private resetFormState() {
    this.form.reset({url: ''});
    this.isSubmitting.set(false);
  }
}