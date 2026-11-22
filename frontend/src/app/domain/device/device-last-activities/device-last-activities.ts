import { Component, computed, inject } from '@angular/core';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { DeviceActivityControllerService } from '@api/device';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ClrCardModule, ClrIcon, DatePipe, RouterLink],
  selector: 'app-device-last-activities',
  styleUrl: './device-last-activities.css',
  templateUrl: './device-last-activities.html',
})
export class DeviceLastActivities {
  private readonly lastActivitiesResource = inject(
    DeviceActivityControllerService,
  ).getLastActivitiesResource(() => ({ page: { page: 0, size: 7 } }));

  readonly deviceLastActivities = computed(() =>
    this.lastActivitiesResource.hasValue() ? this.lastActivitiesResource.value() : undefined,
  );
}
