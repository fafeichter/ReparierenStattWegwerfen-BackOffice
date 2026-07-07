import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import {
  BatteryHealthDto,
  DeviceBaseControllerService,
  DeviceBaseDetailsDto,
  DeviceBatteryStatusControllerService,
  DeviceGradeControllerService,
  DeviceStatusControllerService,
  NamedIdDto,
} from '@api/device';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import {
  ClrCommonFormsModule,
  ClrIcon,
  ClrInputModule,
  ClrLabel,
  ClrNumberInputModule,
} from '@clr/angular';
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
    ClrNumberInputModule,
  ],
  templateUrl: './base.html',
  styleUrl: './base.css',
})
export class Base implements OnInit {
  deviceId = input.required<number>();
  statusChanged = output<void>();
  batteryStatusChanged = output<void>();
  gradeChanged = output<void>();
  tagsChanged = output<void>();

  deviceBase = signal<DeviceBaseDetailsDto | undefined>(undefined);

  deviceStatus = signal<NamedIdDto[]>([]);
  deviceBatteryStatus = signal<NamedIdDto[]>([]);
  deviceGrades = signal<NamedIdDto[]>([]);
  statusEditModeActive = signal<boolean>(false);
  tagEditModeActive = signal<boolean>(false);
  deviceTags = signal<NamedIdDto[]>([]);

  serialNumberEditModeActive = signal<boolean>(false);
  batteryEditModeActive = signal<boolean>(false);
  batteryStatusEditModeActive = signal<boolean>(false);
  gradeEditModeActive = signal<boolean>(false);

  statusForm = new FormGroup({
    newStatusId: new FormControl<number | null>(null, [Validators.required]),
  });

  serialNumberForm = new FormGroup({
    newSerialNumber: new FormControl<string | null>(null),
  });

  batteryForm = new FormGroup({
    newMaximumCapacity: new FormControl<number | null>(null),
    newCycleCount: new FormControl<number | null>(null),
  });

  batteryStatusForm = new FormGroup({
    newBatteryStatusId: new FormControl<number | null>(null, [Validators.required]),
  });

  gradeForm = new FormGroup({
    newGradeId: new FormControl<number | null>(null, [Validators.required]),
  });

  tagForm = new FormGroup({
    newTagId: new FormControl<number | null>(null, [Validators.required]),
  });

  private api = inject(DeviceBaseControllerService);
  private statusApi = inject(DeviceStatusControllerService);
  private batteryStatusApi = inject(DeviceBatteryStatusControllerService);
  private gradeApi = inject(DeviceGradeControllerService);

  ngOnInit(): void {
    this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
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
              (deviceStatus) => deviceStatus.id === this.statusForm.controls.newStatusId.value!,
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

  activateBatteryEditMode() {
    this.batteryEditModeActive.set(true);
    this.batteryForm.patchValue({
      newCycleCount: this.deviceBase()?.batteryCycleCount,
      newMaximumCapacity: this.deviceBase()?.batteryMaximumCapacity,
    });
  }

  changeBattery() {
    const newBatteryHealth: BatteryHealthDto = {
      cycleCount: this.batteryForm.controls.newCycleCount.value!,
      maximumCapacity: this.batteryForm.controls.newMaximumCapacity.value!,
    };

    this.api.updateBattery(this.deviceId(), newBatteryHealth).subscribe(() => {
      this.batteryEditModeActive.set(false);
      this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
    });
  }

  activateBatteryStatusEditMode() {
    this.batteryStatusEditModeActive.set(true);

    this.batteryStatusApi.getAllBatteryStatus().subscribe((data) => {
      this.deviceBatteryStatus.set(data);
      this.batteryStatusForm.patchValue({
        newBatteryStatusId: this.deviceBase()?.batteryStatus?.id,
      });
    });
  }

  changeBatteryStatus() {
    this.api
      .updateBatteryStatus(
        this.deviceId(),
        this.batteryStatusForm.controls.newBatteryStatusId.value!,
      )
      .subscribe(() => {
        this.batteryStatusEditModeActive.set(false);
        this.batteryStatusChanged.emit();

        this.deviceBase.update((currentValue) => {
          return {
            ...currentValue!,
            batteryStatus: this.deviceBatteryStatus().find((deviceBatteryStatus) => {
              return (
                deviceBatteryStatus.id == this.batteryStatusForm.controls.newBatteryStatusId.value!
              );
            })!,
          };
        });
      });
  }

  activateGradeEditMode() {
    this.gradeEditModeActive.set(true);

    this.gradeApi.getAllGrades().subscribe((data) => {
      this.deviceGrades.set(data);
      this.gradeForm.patchValue({
        newGradeId: this.deviceBase()?.grade?.id,
      });
    });
  }

  changeGrade() {
    this.api
      .updateGrade(this.deviceId(), this.gradeForm.controls.newGradeId.value!)
      .subscribe(() => {
        this.gradeEditModeActive.set(false);
        this.gradeChanged.emit();

        this.deviceBase.update((currentValue) => {
          return {
            ...currentValue!,
            grade: this.deviceGrades().find((deviceGrade) => {
              return deviceGrade.id == this.gradeForm.controls.newGradeId.value!;
            })!,
          };
        });
      });
  }

  activateTagEditMode() {
    this.tagEditModeActive.set(true);

    this.api.getAvailableTags(this.deviceId()).subscribe((data) => {
      this.deviceTags.set(data);
    });
  }

  addTag() {
    this.api.addTag(this.deviceId(), this.tagForm.controls.newTagId.value!).subscribe(() => {
      this.tagEditModeActive.set(false);
      this.tagsChanged.emit();

      this.deviceBase.update((currentValue) => {
        let addedTag: NamedIdDto = this.deviceTags().find((deviceTag) => {
          return deviceTag.id == this.tagForm.controls.newTagId.value!;
        })!;

        this.deviceBase()?.tags!.push(addedTag);

        return {
          ...currentValue!,
          tags: this.deviceBase()?.tags,
        };
      });
    });
  }

  protected deleteTag(tagId: number) {
    if (confirm('Do you really want to delete this tag?')) {
      this.api.deleteTag(this.deviceId(), tagId).subscribe(() => {
        this.tagsChanged.emit();

        this.deviceBase.update((currentValue) => {
          this.deviceBase()!.tags = this.deviceBase()!.tags!.filter(
            (deviceTag) => deviceTag.id !== tagId,
          );

          return {
            ...currentValue!,
            tags: this.deviceBase()?.tags,
          };
        });
      });
    }
  }
}
