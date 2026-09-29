import { Component, inject, input } from '@angular/core';
import { DeviceBaseControllerService } from '@api/device';
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
import { map } from 'rxjs';
import { TitleFn } from '../../../layout/route-title.service';
import { scrollToSection } from '../../../clarity-addons/js/scrollspy';

export const deviceTitle: TitleFn = (route) => {
  const deviceId = route.paramMap.get('deviceId')!;
  const api = inject(DeviceBaseControllerService);

  return api
    .getDeviceBaseDetails(Number(deviceId!))
    .pipe(map((device) => `#${deviceId} - ${device.model.name}`));
};

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
export class DeviceDetail {
  deviceId = input.required<number, string>({
    transform: (value: string) => Number(value),
  });
  navItems = [
    { id: 'device', label: 'Device' },
    { id: 'buying', label: 'Buying' },
    { id: 'repair', label: 'Repair' },
    { id: 'selling', label: 'Selling' },
    { id: 'notes', label: 'Notes' },
    { id: 'activity', label: 'Activity' },
  ];
  protected readonly scrollToSection = scrollToSection;
}
