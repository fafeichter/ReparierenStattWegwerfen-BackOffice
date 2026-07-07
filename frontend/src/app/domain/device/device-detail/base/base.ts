import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import {
  DeviceBaseControllerService,
  DeviceBaseDetailsDto,
  DeviceStatusControllerService,
  DeviceTagDto,
  DeviceTagsControllerService,
  NamedIdDto,
} from '@api/device';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import { ClrCommonFormsModule, ClrIcon, ClrInputModule, ClrLabel } from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-base',
  imports: [
    OrElsePipe,
    RouterLink,
    ClrIcon,
    ClrLabel,
    ClrCommonFormsModule,
    FormsModule,
    ReactiveFormsModule,
    ClrInputModule,
  ],
  templateUrl: './base.html',
  styleUrl: './base.css',
})
export class Base implements OnInit {
  deviceId = input.required<number>();
  statusChanged = output<void>();

  deviceBase = signal<DeviceBaseDetailsDto | undefined>(undefined);
  deviceTags = signal<DeviceTagDto[]>([]);

  deviceStatus = signal<NamedIdDto[]>([]);
  statusEditModeActive = signal<boolean>(false);

  serialNumberEditModeActive = signal<boolean>(false);

  statusForm = new FormGroup({
    newStatusId: new FormControl<number | null>(null, [Validators.required]),
  });

  serialNumberForm = new FormGroup({
    newSerialNumber: new FormControl<string | null>(null, []),
  });

  private api = inject(DeviceBaseControllerService);
  private tagsApi = inject(DeviceTagsControllerService);
  private statusApi = inject(DeviceStatusControllerService);

  ngOnInit(): void {
    this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
    this.tagsApi.getTagsForDevice(this.deviceId()).subscribe((data) => this.deviceTags.set(data));
  }

  activateStatusEditMode() {
    this.statusEditModeActive.set(true);
    this.statusApi.getAllStatus().subscribe((data) => {
      this.deviceStatus.set(data);
      this.statusForm.patchValue({
        newStatusId: this.deviceBase()?.status.id,
      });
    });
  }

  changeStatus() {
    this.api
      .updateStatus(this.deviceId(), this.statusForm.controls.newStatusId.value!)
      .subscribe(() => {
        this.statusEditModeActive.set(false);
        this.statusChanged.emit();
        this.deviceBase.update((currentValue) => {
          return {
            ...currentValue!,
            status: this.deviceStatus().find(
              (device) => device.id === this.statusForm.controls.newStatusId.value!,
            )!,
          };
        });

        this.api
          .getDeviceBaseDetails(this.deviceId())
          .subscribe((data) => this.deviceBase.set(data));
      });
  }

  activateSerialNumberEditMode() {
    this.serialNumberEditModeActive.set(true);
    this.serialNumberForm.patchValue({
      newSerialNumber: this.deviceBase()?.serialNumber,
    });
  }

  changeSerialNumber() {
    this.api
      .updateSerialNumber(this.deviceId(), this.serialNumberForm.controls.newSerialNumber.value!)
      .subscribe(() => {
        this.serialNumberEditModeActive.set(false);
        this.deviceBase.update((currentValue) => {
          return {
            ...currentValue!,
            serialNumber: this.serialNumberForm.controls.newSerialNumber.value!,
          };
        });
      });
  }
}
