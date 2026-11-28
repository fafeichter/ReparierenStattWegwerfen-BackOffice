import { Component, effect, inject, input, model, output, signal } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DeviceNotesControllerService } from '@api/device';
import {
  ClrCommonFormsModule,
  ClrIcon,
  ClrLoadingButtonModule,
  ClrLoadingModule,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { Autofocus } from '../../../../../clarity-addons/input/autofocus';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrIcon,
    ClrModalModule,
    ClrTextareaModule,
    FormsModule,
    ReactiveFormsModule,
    Autofocus,
    ClrLoadingModule,
    ClrLoadingButtonModule,
  ],
  selector: 'app-note-add-modal',
  styleUrl: './note-add-modal.css',
  templateUrl: './note-add-modal.html',
})
export class NoteAddModal {
  deviceId = input.required<number>();
  modalOpen = model.required<boolean>();
  saved = output<void>();
  isSubmitting = signal<boolean>(false);

  form = new FormGroup({
    text: new FormControl<string | null>(null, [Validators.required]),
  });
  private notesApi = inject(DeviceNotesControllerService);

  constructor() {
    effect(() => {
      if (this.modalOpen()) {
        this.form.reset();
      }
    });
  }

  addNote() {
    this.isSubmitting.set(true);

    this.notesApi
      .addDeviceNote(this.deviceId(), this.form.controls.text.value || '')
      .subscribe(() => {
        this.isSubmitting.set(false);
        this.modalOpen.set(false);
        this.saved.emit();
      });
  }
}
