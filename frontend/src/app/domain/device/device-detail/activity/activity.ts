import { Component, computed, DestroyRef, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { DeviceActivityControllerService } from '@api/device';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  selector: 'app-activity',
  imports: [DatePipe, ClrCardModule, ClrIcon],
  templateUrl: './activity.html',
  styleUrl: './activity.css',
})
export class Activity {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  protected readonly visibleRowCount = 10;
  protected readonly expanded = signal(false);
  protected readonly hiddenRowCount = computed(() =>
    Math.max(0, this.deviceActivities().length - this.visibleRowCount),
  );
  private readonly activitiesResource = inject(
    DeviceActivityControllerService,
  ).getActivitiesForDeviceResource(() => ({ deviceId: this.deviceId() }));
  // keeps the previous list while reloading, so the poll does not flicker
  readonly deviceActivities = computed(() =>
    this.activitiesResource.hasValue() ? this.activitiesResource.value() : [],
  );

  constructor() {
    const pollId = setInterval(() => this.activitiesResource.reload(), 2500);
    inject(DestroyRef).onDestroy(() => clearInterval(pollId));
  }
}
