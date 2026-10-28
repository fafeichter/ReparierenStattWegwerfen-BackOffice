import { Component, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { DeviceActivityControllerService, DeviceActivityDto } from '@api/device';
import { interval, startWith, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { NavItem } from '../../../../clarity-addons/components/scrollspy/scrollspy';

@Component({
  selector: 'app-activity',
  imports: [DatePipe, ClrCardModule, ClrIcon],
  templateUrl: './activity.html',
  styleUrl: './activity.css',
})
export class Activity implements OnInit {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  deviceActivities = signal<DeviceActivityDto[]>([]);

  private activityApi = inject(DeviceActivityControllerService);

  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    interval(5000)
      .pipe(
        startWith(0),
        switchMap(() => this.activityApi.getActivities(this.deviceId())),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe((data) => this.deviceActivities.set(data));
  }
}
