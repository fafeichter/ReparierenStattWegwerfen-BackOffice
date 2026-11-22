import { Component, computed, effect, inject, input, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { DatePipe } from '@angular/common';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DeviceNotesControllerService } from '@api/device';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  selector: 'app-notes',
  imports: [
    ClrCommonFormsModule,
    ClrIcon,
    ClrModalModule,
    ClrTextareaModule,
    DatePipe,
    FormsModule,
    ReactiveFormsModule,
    ClrCardModule,
  ],
  templateUrl: './notes.html',
  styleUrl: './notes.css',
})
export class Notes {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();
  modalOpen = signal<boolean>(false);
  modalEditOpen = signal<boolean>(false);
  noteIdInEditMode = signal<number | undefined>(undefined);
  isSubmitting = signal<boolean>(false);
  form = new FormGroup({
    text: new FormControl<string | null>(null, [Validators.required]),
  });
  private notesApi = inject(DeviceNotesControllerService);
  private readonly deviceNotesResource = this.notesApi.getDeviceNotesResource(() => ({
    deviceId: this.deviceId(),
  }));
  readonly deviceNotes = computed(() =>
    this.deviceNotesResource.hasValue() ? this.deviceNotesResource.value() : [],
  );

  constructor() {
    // Automatically reset the form whenever the modal opens
    effect(() => {
      if (this.modalOpen()) {
        this.form.reset();
      }
    });
  }

  addNote() {
    this.notesApi
      .addDeviceNote(this.deviceId(), this.form.controls.text.value || '')
      .subscribe(() => {
        this.modalOpen.set(false);
        this.deviceNotesResource.reload();
      });
  }

  deleteNote(oldDeviceNoteId: number) {
    if (confirm('Do you really want to delete this note?')) {
      this.notesApi.deleteDeviceNote(this.deviceId(), oldDeviceNoteId).subscribe(() => {
        this.deviceNotesResource.value.update((deviceNotes) =>
          deviceNotes?.filter((note) => note.noteId !== oldDeviceNoteId),
        );
      });
    }
  }

  activateEditMode(noteId: number) {
    this.modalEditOpen.set(true);
    this.noteIdInEditMode.set(noteId);
    const note = this.deviceNotes().find((note) => note.noteId === noteId);
    this.form.patchValue({
      text: note!.text,
    });
  }

  editNote() {
    this.notesApi
      .editDeviceNote(
        this.deviceId(),
        this.noteIdInEditMode()!,
        this.form.controls.text.value || '',
      )
      .subscribe(() => {
        this.modalEditOpen.set(false);
        this.deviceNotesResource.reload();
      });
  }
}
