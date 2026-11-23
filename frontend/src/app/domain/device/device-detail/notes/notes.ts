import { Component, computed, inject, input, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DeviceNotesControllerService } from '@api/device';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';
import { NoteEditModal } from './note-edit-modal/note-edit-modal';
import { NoteAddModal } from './note-add-modal/note-add-modal';

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
    NoteEditModal,
    NoteAddModal,
  ],
  templateUrl: './notes.html',
  styleUrl: './notes.css',
})
export class Notes {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();
  addNoteModalOpen = signal<boolean>(false);

  editNoteModalOpen = signal<boolean>(false);
  noteIdInEditMode = signal<number | undefined>(undefined);
  readonly noteInEditMode = computed(() =>
    this.deviceNotes().find((note) => note.noteId === this.noteIdInEditMode()),
  );
  private notesApi = inject(DeviceNotesControllerService);
  private readonly deviceNotesResource = this.notesApi.getDeviceNotesResource(() => ({
    deviceId: this.deviceId(),
  }));
  readonly deviceNotes = computed(() =>
    this.deviceNotesResource.hasValue() ? this.deviceNotesResource.value() : [],
  );

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
    this.noteIdInEditMode.set(noteId);
    this.editNoteModalOpen.set(true);
  }
}
