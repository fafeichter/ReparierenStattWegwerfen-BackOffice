import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBaseControllerService, DeviceBaseDetailsDto } from '@api/device';
import {
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrInputModule,
  ClrMainContainerModule,
  ClrModalModule,
  ClrNumberInputModule,
  ClrRadioModule,
  ClrSpinnerModule,
  ClrTabsModule,
  ClrTextareaModule,
  ClrVerticalNavModule,
} from '@clr/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Notes } from './notes/notes';
import { Base } from './base/base';
import { Repair } from './repair/repair';
import { Activity } from './activity/activity';
import { Selling } from './selling/selling';
import { Buying } from './buying/buying';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-device-detail',
  imports: [
    ClrDatagridModule,
    ClrCommonFormsModule,
    ClrInputModule,
    ClrModalModule,
    ClrNumberInputModule,
    ClrRadioModule,
    ClrSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
    ClrTextareaModule,
    Notes,
    Base,
    Repair,
    Activity,
    Selling,
    Buying,
    RouterLink,
    ClrVerticalNavModule,
    ClrTabsModule,
    ClrMainContainerModule,
  ],
  templateUrl: './device-detail.html',
  standalone: true,
  styleUrl: './device-detail.css',
})
export class DeviceDetail implements OnInit {
  deviceId = input.required<number, string>({
    transform: (value: string) => Number(value),
  });
  deviceBase = signal<DeviceBaseDetailsDto | undefined>(undefined);
  navItems = [
    { id: 'device', label: 'Device' },
    { id: 'buying', label: 'Buying' },
    { id: 'repair', label: 'Repair' },
    { id: 'selling', label: 'Selling' },
    { id: 'notes', label: 'Notes' },
    { id: 'activity', label: 'Activity' },
  ];
  private baseApi = inject(DeviceBaseControllerService);

  ngOnInit(): void {
    this.baseApi
      .getDeviceBaseDetails(this.deviceId())
      .subscribe((data) => this.deviceBase.set(data));
  }

  scrollToSection(sectionId: string): void {
    document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
  }
}
