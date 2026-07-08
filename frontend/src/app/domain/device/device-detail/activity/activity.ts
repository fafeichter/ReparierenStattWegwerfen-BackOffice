import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { DeviceActivityControllerService, DeviceActivityDto } from '@api/device';

@Component({
  selector: 'app-activity',
  imports: [DatePipe],
  templateUrl: './activity.html',
  styleUrl: './activity.css',
})
export class Activity implements OnInit {
  deviceId = input.required<number>();

  deviceActivities = signal<DeviceActivityDto[]>([]);

  private activityApi = inject(DeviceActivityControllerService);

  ngOnInit(): void {
    this.activityApi
      .getActivities(this.deviceId())
      .subscribe((data) => this.deviceActivities.set(data));
  }

  reloadActivity() {
    this.activityApi
      .getActivities(this.deviceId())
      .subscribe((data) => this.deviceActivities.set(data));
  }
}
