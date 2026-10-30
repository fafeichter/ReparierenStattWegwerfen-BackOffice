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
import { map } from 'rxjs';
import { TitleFn } from '../../../layout/route-title.service';
import { NavItem, Scrollspy } from '../../../clarity-addons/components/scrollspy/scrollspy';

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
    ClrVerticalNavModule,
    ClrTabsModule,
    ClrMainContainerModule,
    Scrollspy,
  ],
  templateUrl: './device-detail.html',
  styleUrl: './device-detail.css',
})
export class DeviceDetail {
  deviceId = input.required<number, string>({
    transform: (value: string) => Number(value),
  });
  navItems: NavItem[] = [
    { id: 'device', label: 'Device', icon: 'cpu' },
    { id: 'buying', label: 'Buying', icon: 'user' },
    { id: 'repair', label: 'Repair', icon: 'wrench' },
    { id: 'selling', label: 'Selling', icon: 'tag' },
    { id: 'notes', label: 'Notes', icon: 'note' },
    { id: 'activity', label: 'Activity', icon: 'timeline' },
  ];
}
