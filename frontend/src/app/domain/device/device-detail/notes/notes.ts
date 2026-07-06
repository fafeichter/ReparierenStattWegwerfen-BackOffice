import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ClrCommonFormsModule, ClrIcon, ClrModalModule, ClrTextareaModule } from '@clr/angular';
import { DatePipe } from '@angular/common';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DeviceNoteDto, DeviceNotesControllerService } from '@api/device';

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
  ],
  templateUrl: './notes.html',
  styleUrl: './notes.css',
})
export class Notes implements OnInit {
  deviceId = input.required<number>();
  deviceNotes = signal<DeviceNoteDto[]>([]);
  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  form = new FormGroup({
    text: new FormControl<string | null>(null, [Validators.required]),
  });
  private notesApi = inject(DeviceNotesControllerService);

  ngOnInit(): void {
    this.notesApi.getDeviceNotes(this.deviceId()).subscribe((data) => this.deviceNotes.set(data));
  }

  protected addNote() {
    this.notesApi
      .addDeviceNote(this.deviceId(), this.form.controls.text.value || '')
      .subscribe((data) => {
        this.modalOpened.set(false);
        this.form.reset();
        this.notesApi
          .getDeviceNotes(this.deviceId())
          .subscribe((data) => this.deviceNotes.set(data));
      });
  }
}
