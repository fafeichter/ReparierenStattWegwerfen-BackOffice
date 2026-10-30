import { Component, effect, inject, input, OnInit, signal } from '@angular/core';
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
import { DeviceNoteDto, DeviceNotesControllerService } from '@api/device';
import { NavItem } from '../../../../clarity-addons/components/scrollspy/scrollspy';

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
export class Notes implements OnInit {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();
  deviceNotes = signal<DeviceNoteDto[]>([]);
  modalOpen = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  form = new FormGroup({
    text: new FormControl<string | null>(null, [Validators.required]),
  });
  private notesApi = inject(DeviceNotesControllerService);

  constructor() {
    // Automatically reset the form whenever the modal opens
    effect(() => {
      if (this.modalOpen()) {
        this.form.reset();
      }
    });
  }

  ngOnInit(): void {
    this.notesApi.getDeviceNotes(this.deviceId()).subscribe((data) => this.deviceNotes.set(data));
  }

  addNote() {
    this.notesApi
      .addDeviceNote(this.deviceId(), this.form.controls.text.value || '')
      .subscribe(() => {
        this.modalOpen.set(false);
        this.notesApi.getDeviceNotes(this.deviceId()).subscribe((data) => {
          this.deviceNotes.set(data);
        });
      });
  }

  deleteNote(oldDeviceNoteId: number) {
    if (confirm('Do you really want to remove this note?')) {
      this.notesApi.deleteDeviceNote(this.deviceId(), oldDeviceNoteId).subscribe(() => {
        this.deviceNotes.update((deviceNotes) => {
          return deviceNotes.filter((note) => note.noteId !== oldDeviceNoteId);
        });
      });
    }
  }
}
