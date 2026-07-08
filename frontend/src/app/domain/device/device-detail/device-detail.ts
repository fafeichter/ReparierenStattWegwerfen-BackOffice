import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBaseControllerService, DeviceBaseDetailsDto } from '@api/device';
import {
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrInputModule,
  ClrModalModule,
  ClrNumberInputModule,
  ClrRadioModule,
  ClrSpinnerModule,
  ClrTextareaModule,
} from '@clr/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Notes } from './notes/notes';
import { Base } from './base/base';
import { Repair } from './repair/repair';
import { Activity } from './activity/activity';
import { Selling } from './selling/selling';
import { Buying } from './buying/buying';

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

  private baseApi = inject(DeviceBaseControllerService);

  ngOnInit(): void {
    this.baseApi
      .getDeviceBaseDetails(this.deviceId())
      .subscribe((data) => this.deviceBase.set(data));
  }
}
