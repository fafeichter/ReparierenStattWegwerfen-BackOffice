import { Component, effect, inject, input, OnInit, output, signal } from '@angular/core';
import {
  BatteryHealthDto,
  DeviceBaseControllerService,
  DeviceBaseDetailsDto,
  DeviceBatteryStatusControllerService,
  DeviceGradeControllerService,
  DeviceStatusControllerService,
  NamedIdDto,
  UpdateHardwareConfigDto,
} from '@api/device';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import {
  ClrCommonFormsModule,
  ClrIcon,
  ClrInputModule,
  ClrLabel,
  ClrNumberInputModule,
  ClrSelectModule,
} from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  ModelAppleSiliconControllerService,
  ModelColorControllerService,
  ModelStorageControllerService,
  ModelUnifiedMemoryControllerService,
} from '@api/model';

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
    ClrSelectModule,
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

  modelAppleSilicons = signal<NamedIdDto[]>([]);
  modelColors = signal<NamedIdDto[]>([]);
  modelMemories = signal<NamedIdDto[]>([]);
  modelStorages = signal<NamedIdDto[]>([]);
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
  hardwareConfigEditModeActive = signal<boolean>(false);

  hardwareConfigForm = new FormGroup({
    newAppleSiliconId: new FormControl<number | null>(null),
    newMemoryId: new FormControl<number | null>(null),
    newStorageId: new FormControl<number | null>(null),
    newColorId: new FormControl<number | null>(null),
  });

  statusForm = new FormGroup({
    newStatusId: new FormControl<number | null>(null, [Validators.required]),
  });

  serialNumberForm = new FormGroup({
    newSerialNumber: new FormControl<string | null>(null),
  });

  batteryForm = new FormGroup({
    newMaximumCapacity: new FormControl<number | null>(null, [
      Validators.min(0),
      Validators.max(100),
    ]),
    newCycleCount: new FormControl<number | null>(null, [Validators.min(1)]),
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

  private readonly editModes = [
    this.hardwareConfigEditModeActive,
    this.statusEditModeActive,
    this.tagEditModeActive,
    this.serialNumberEditModeActive,
    this.batteryEditModeActive,
    this.batteryStatusEditModeActive,
    this.gradeEditModeActive,
  ];

  private api = inject(DeviceBaseControllerService);
  private statusApi = inject(DeviceStatusControllerService);
  private batteryStatusApi = inject(DeviceBatteryStatusControllerService);
  private gradeApi = inject(DeviceGradeControllerService);
  private appleSiliconApi = inject(ModelAppleSiliconControllerService);
  private colorApi = inject(ModelColorControllerService);
  private memoryApi = inject(ModelUnifiedMemoryControllerService);
  private storageApi = inject(ModelStorageControllerService);

  constructor() {
    this.editModes.forEach((active) => {
      effect(() => {
        if (active()) {
          this.editModes
            .filter((editMode) => editMode !== active)
            .forEach((otherEditMode) => otherEditMode.set(false));
        }
      });
    });
  }

  ngOnInit(): void {
    this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
  }

  activateHardwareConfigEditMode() {
    this.hardwareConfigEditModeActive.set(true);
    this.modelMemories.set(
      [this.deviceBase()?.unifiedMemory].filter((memory): memory is NamedIdDto => !!memory),
    );
    this.modelStorages.set(
      [this.deviceBase()?.storage].filter((storage): storage is NamedIdDto => !!storage),
    );

    this.hardwareConfigForm.patchValue({
      newAppleSiliconId: this.deviceBase()!.appleSilicon?.id,
      newMemoryId: this.deviceBase()!.unifiedMemory?.id,
      newStorageId: this.deviceBase()!.storage?.id,
      newColorId: this.deviceBase()!.color?.id,
    });

    this.appleSiliconApi
      .getAllAppleSiliconsForModel(this.deviceBase()!.model.id)
      .subscribe((data) => {
        this.modelAppleSilicons.set(data);
      });

    if (this.deviceBase()!.appleSilicon) {
      if (this.deviceBase()!.unifiedMemory) {
        this.loadMemoryOptionsForAppleSilicon(this.deviceBase()!.appleSilicon?.id!);
      }
      if (this.deviceBase()!.storage) {
        this.loadStorageOptions();
      }
    }

    this.colorApi.getColorsForModel(this.deviceBase()!.model.id).subscribe((data) => {
      this.modelColors.set(data);
    });
  }

  loadMemoryOptions($event: Event) {
    const selectedAppleSiliconId: number = Number(($event.target as HTMLSelectElement).value);
    this.loadMemoryOptionsForAppleSilicon(selectedAppleSiliconId);
  }

  loadStorageOptions() {
    const selectedAppleSiliconId: number =
      this.hardwareConfigForm.controls.newAppleSiliconId.value!;

    this.storageApi
      .getStoragesForModelAndAppleSilicon(this.deviceId(), selectedAppleSiliconId)
      .subscribe((data) => {
        this.modelStorages.set(data);
      });
  }

  changeHardwareConfig() {
    const updateHardwareConfig: UpdateHardwareConfigDto = {
      modelAppleSiliconId: this.hardwareConfigForm.controls.newAppleSiliconId.value!,
      modelAppleSiliconUnifiedMemoryId: this.hardwareConfigForm.controls.newMemoryId.value!,
      modelStorageId: this.hardwareConfigForm.controls.newStorageId.value!,
      modelColorId: this.hardwareConfigForm.controls.newColorId.value!,
    };
    this.api.updateHardwareConfig(this.deviceId(), updateHardwareConfig).subscribe(() => {
      this.hardwareConfigEditModeActive.set(false);

      this.deviceBase.update((currentValue) => {
        return {
          ...currentValue!,
          appleSilicon: this.modelAppleSilicons().find((appleSilicon) => {
            return appleSilicon.id == updateHardwareConfig.modelAppleSiliconId;
          })!,
          unifiedMemory: this.modelMemories().find((memory) => {
            return memory.id == updateHardwareConfig.modelAppleSiliconUnifiedMemoryId;
          })!,
          storage: this.modelStorages().find((storage) => {
            return storage.id == updateHardwareConfig.modelStorageId;
          })!,
          color: this.modelColors().find((appleSilicon) => {
            return appleSilicon.id == updateHardwareConfig.modelColorId;
          })!,
        };
      });
    });
  }

  activateStatusEditMode() {
    this.statusEditModeActive.set(true);
    this.statusForm.patchValue({
      newStatusId: this.deviceBase()?.status.id,
    });

    this.statusApi.getAllStatus().subscribe((data) => {
      this.deviceStatus.set(data);
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
    this.batteryStatusForm.patchValue({
      newBatteryStatusId: this.deviceBase()?.batteryStatus?.id || null,
    });

    this.batteryStatusApi.getAllBatteryStatus().subscribe((data) => {
      this.deviceBatteryStatus.set(data);
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
    this.gradeForm.patchValue({
      newGradeId: this.deviceBase()?.grade?.id || null,
    });

    this.gradeApi.getAllGrades().subscribe((data) => {
      this.deviceGrades.set(data);
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
    this.tagForm.patchValue({
      newTagId: null,
    });

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

  deleteTag(tagId: number) {
    if (confirm('Do you really want to remove this tag?')) {
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

  private loadMemoryOptionsForAppleSilicon(appleSiliconId: number) {
    this.memoryApi
      .getUnifiedMemoriesForModelAndAppleSilicon(this.deviceBase()!.model.id, appleSiliconId)
      .subscribe((data) => {
        this.modelMemories.set(data);
        this.loadStorageOptions();
      });
  }
}
