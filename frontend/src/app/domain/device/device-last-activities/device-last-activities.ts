import {Component, inject, OnInit, signal} from '@angular/core';
import {ClrCardModule, ClrIcon} from '@clr/angular';
import {DatePipe} from '@angular/common';
import {DeviceActivityControllerService, DeviceLastActivityDto, Pageable} from '@api/device';
import {RouterLink} from '@angular/router';

@Component({
  imports: [
    ClrCardModule,
    ClrIcon,
    DatePipe,
    RouterLink
  ],
  selector: 'app-device-last-activities',
  styleUrl: './device-last-activities.css',
  templateUrl: './device-last-activities.html',
})
export class DeviceLastActivities implements OnInit {

  deviceLastActivities = signal<DeviceLastActivityDto[] | undefined>(undefined);

  deviceActivityApi = inject(DeviceActivityControllerService);

  ngOnInit(): void {
    const page: Pageable = {
      page: 0,
      size: 7
    }
    this.deviceActivityApi.getLastActivities(page).subscribe((deviceLastActivities) => {
      this.deviceLastActivities.set(deviceLastActivities);
    });
  }
}