import { Component, effect, inject, input, model, output, signal } from '@angular/core';
import {
  ClrCommonFormsModule,
  ClrIcon,
  ClrLoadingButtonModule,
  ClrLoadingModule,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DeviceNoteDto, DeviceNotesControllerService } from '@api/device';
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
  selector: 'app-note-edit-modal',
  styleUrl: './note-edit-modal.css',
  templateUrl: './note-edit-modal.html',
})
export class NoteEditModal {
  deviceId = input.required<number>();
  note = input.required<DeviceNoteDto>();
  modalOpen = model.required<boolean>();
  saved = output<void>();
  isSubmitting = signal<boolean>(false);

  form = new FormGroup({
    text: new FormControl<string | null>(null, [Validators.required]),
  });

  private notesApi = inject(DeviceNotesControllerService);

  constructor() {
    effect(() => {
      this.form.reset({ text: this.note().text });
    });
  }

  editNote() {
    this.isSubmitting.set(true);

    this.notesApi
      .editDeviceNote(this.deviceId(), this.note().noteId, this.form.controls.text.value || '')
      .subscribe(() => {
        this.isSubmitting.set(false);
        this.modalOpen.set(false);
        this.saved.emit();
      });
  }
}
