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
import { ClrCommonFormsModule, ClrIcon, ClrLabel } from '@clr/angular';
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

  statusForm = new FormGroup({
    newStatusId: new FormControl<number | null>(null, [Validators.required]),
  });

  private api = inject(DeviceBaseControllerService);
  private tagsApi = inject(DeviceTagsControllerService);
  private statusApi = inject(DeviceStatusControllerService);

  ngOnInit(): void {
    this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
    this.tagsApi.getTagsForDevice(this.deviceId()).subscribe((data) => this.deviceTags.set(data));
  }

  protected activateStatusEditMode() {
    this.statusEditModeActive.set(true);
    this.statusApi.getAllStatus().subscribe((data) => {
      this.deviceStatus.set(data);
      this.statusForm.patchValue({
        newStatusId: this.deviceBase()?.status.id,
      });
    });
  }

  protected changeStatus() {
    this.api
      .updateStatus(this.deviceId(), this.statusForm.controls.newStatusId.value!)
      .subscribe(() => {
        this.statusEditModeActive.set(false);
        this.statusChanged.emit();
        this.api
          .getDeviceBaseDetails(this.deviceId())
          .subscribe((data) => this.deviceBase.set(data));
      });
  }
}
