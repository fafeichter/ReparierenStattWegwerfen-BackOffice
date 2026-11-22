import { Component, computed, effect, inject, input, signal } from '@angular/core';
import {
  BatteryHealthDto,
  DeviceBatteryHealthControllerService,
  DeviceBatteryStatusControllerService,
  DeviceControllerService,
  DeviceGradeControllerService,
  DeviceGradeDto,
  DeviceHardwareControllerService,
  DeviceStatusControllerService,
  DeviceTagsControllerService,
  NamedIdDto,
  UpdateHardwareConfigDto,
} from '@api/device';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { RouterLink } from '@angular/router';
import {
  ClrAlertModule,
  ClrCardModule,
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
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';
import { SerialNumber } from '../../serial-number';
import { SellingModal } from '../selling/selling-modal/selling-modal';

@Component({
  selector: 'app-device',
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
    ClrAlertModule,
    ClrCardModule,
    SerialNumber,
    SellingModal,
  ],
  templateUrl: './device.html',
  styleUrl: './device.css',
})
export class Device {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  modelMemories = signal<NamedIdDto[]>([]);
  modelStorages = signal<NamedIdDto[]>([]);
  deviceStatus = signal<NamedIdDto[]>([]);
  deviceBatteryStatus = signal<NamedIdDto[]>([]);
  deviceGrades = signal<DeviceGradeDto[]>([]);
  statusEditModeActive = signal<boolean>(false);
  tagEditModeActive = signal<boolean>(false);
  statusSellingModeActive = signal<boolean>(false);

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
    newSerialNumber: new FormControl<string | null>(null, [
      Validators.required,
      Validators.minLength(10),
      Validators.maxLength(12),
      Validators.pattern(/^[A-Z0-9]*$/),
    ]),
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

  private hardwareApi = inject(DeviceHardwareControllerService);
  private statusApi = inject(DeviceStatusControllerService);
  private batteryStatusApi = inject(DeviceBatteryStatusControllerService);
  private batteryHealthApi = inject(DeviceBatteryHealthControllerService);
  private gradeApi = inject(DeviceGradeControllerService);
  private appleSiliconApi = inject(ModelAppleSiliconControllerService);
  private colorApi = inject(ModelColorControllerService);
  private memoryApi = inject(ModelUnifiedMemoryControllerService);
  private storageApi = inject(ModelStorageControllerService);
  private tagsApi = inject(DeviceTagsControllerService);

  private readonly deviceBaseResource = inject(
    DeviceControllerService,
  ).getDeviceBaseDetailsResource(() => ({ deviceId: this.deviceId() }));
  readonly deviceBase = computed(() =>
    this.deviceBaseResource.hasValue() ? this.deviceBaseResource.value() : undefined,
  );

  // The option lists below are only requested while their edit mode is open.
  private readonly hardwareConfigModelId = computed(() =>
    this.hardwareConfigEditModeActive() ? this.deviceBase()?.model.id : undefined,
  );
  private readonly modelAppleSiliconsResource =
    this.appleSiliconApi.getAllAppleSiliconsForModelResource(() => {
      const modelId = this.hardwareConfigModelId();
      return modelId === undefined ? undefined : { modelId };
    });
  readonly modelAppleSilicons = computed(() =>
    this.modelAppleSiliconsResource.hasValue() ? this.modelAppleSiliconsResource.value() : [],
  );
  private readonly modelColorsResource = this.colorApi.getColorsForModelResource(() => {
    const modelId = this.hardwareConfigModelId();
    return modelId === undefined ? undefined : { modelId };
  });
  readonly modelColors = computed(() =>
    this.modelColorsResource.hasValue() ? this.modelColorsResource.value() : [],
  );
  private readonly availableDeviceTagsResource = this.tagsApi.getAvailableTagsResource(() =>
    this.tagEditModeActive() ? { deviceId: this.deviceId() } : undefined,
  );
  readonly availableDeviceTags = computed(() =>
    this.availableDeviceTagsResource.hasValue() ? this.availableDeviceTagsResource.value() : [],
  );

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

    if (this.deviceBase()!.appleSilicon) {
      this.loadMemoryOptionsForAppleSilicon(this.deviceBase()!.appleSilicon?.id!);
      this.loadStorageOptions();
    }
  }

  loadMemoryOptions($event: Event) {
    const selectedAppleSiliconId: number = Number(($event.target as HTMLSelectElement).value);
    this.loadMemoryOptionsForAppleSilicon(selectedAppleSiliconId);
  }

  loadStorageOptions() {
    const selectedAppleSiliconId: number =
      this.hardwareConfigForm.controls.newAppleSiliconId.value!;

    this.storageApi
      .getStoragesForModelAndAppleSilicon(this.deviceBase()!.model.id, selectedAppleSiliconId)
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
    this.hardwareApi.updateHardwareConfig(this.deviceId(), updateHardwareConfig).subscribe(() => {
      this.deviceBaseResource.value.update((deviceBase) => {
        return {
          ...deviceBase!,
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
      this.hardwareConfigEditModeActive.set(false);
    });
  }

  activateStatusEditMode() {
    this.statusEditModeActive.set(true);
    this.statusForm.patchValue({
      newStatusId: this.deviceBase()?.status.id,
    });

    this.statusApi.getAllNonSystemStatus().subscribe((data) => {
      this.deviceStatus.set(data);
    });
  }

  changeStatus() {
    this.statusApi
      .updateStatus(this.deviceId(), this.statusForm.controls.newStatusId.value!)
      .subscribe(() => {
        this.statusEditModeActive.set(false);
        this.deviceBaseResource.value.update((deviceBase) => {
          return {
            ...deviceBase!,
            status: this.deviceStatus().find(
              (deviceStatus) => deviceStatus.id === this.statusForm.controls.newStatusId.value!,
            )!,
          };
        });

        this.deviceBaseResource.reload();
      });
  }

  activateSerialNumberEditMode() {
    this.serialNumberEditModeActive.set(true);
    this.serialNumberForm.patchValue({
      newSerialNumber: this.deviceBase()?.serialNumber,
    });
  }

  changeSerialNumber() {
    this.hardwareApi
      .updateSerialNumber(this.deviceId(), this.serialNumberForm.controls.newSerialNumber.value!)
      .subscribe(() => {
        this.serialNumberEditModeActive.set(false);
        this.deviceBaseResource.value.update((deviceBase) => {
          return {
            ...deviceBase!,
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

    this.batteryHealthApi.updateBattery(this.deviceId(), newBatteryHealth).subscribe(() => {
      this.batteryEditModeActive.set(false);
      this.deviceBaseResource.reload();
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
    this.batteryStatusApi
      .updateBatteryStatus(
        this.deviceId(),
        this.batteryStatusForm.controls.newBatteryStatusId.value!,
      )
      .subscribe(() => {
        this.batteryStatusEditModeActive.set(false);

        this.deviceBaseResource.value.update((deviceBase) => {
          return {
            ...deviceBase!,
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
    this.gradeApi
      .updateGrade(this.deviceId(), this.gradeForm.controls.newGradeId.value!)
      .subscribe(() => {
        this.gradeEditModeActive.set(false);

        this.deviceBaseResource.value.update((deviceBase) => {
          return {
            ...deviceBase!,
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
  }

  addTag() {
    const selectedTagId = this.tagForm.controls.newTagId.value!;
    this.tagsApi.addTag(this.deviceId(), selectedTagId).subscribe(() => {
      // read the tag first: closing the edit mode unloads the available tags
      const addedTag: NamedIdDto = this.availableDeviceTags().find((deviceTag) => {
        return deviceTag.id == selectedTagId;
      })!;
      this.tagEditModeActive.set(false);

      this.deviceBaseResource.value.update((deviceBase) => {
        return {
          ...deviceBase!,
          tags: [...(deviceBase?.tags ?? []), addedTag],
        };
      });
    });
  }

  removeTag(tagId: number) {
    if (confirm('Do you really want to remove this tag?')) {
      this.tagsApi.removeTag(this.deviceId(), tagId).subscribe(() => {
        this.deviceBaseResource.value.update((deviceBase) => {
          return {
            ...deviceBase!,
            tags: deviceBase?.tags?.filter((deviceTag) => deviceTag.id !== tagId),
          };
        });
        this.availableDeviceTagsResource.reload();
      });
    }
  }

  statusSelected($event: Event) {
    const selectedId = Number(($event.target as HTMLSelectElement).value);

    if (selectedId === 6) {
      this.statusSellingModeActive.set(true);
    } else {
      this.statusSellingModeActive.set(false);
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
