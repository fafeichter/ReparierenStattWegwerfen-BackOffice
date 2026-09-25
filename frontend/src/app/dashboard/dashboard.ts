import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ClarityModule, ClrVerticalNavModule } from '@clr/angular';
import { AddDevice } from '../domain/device/add-device/add-device';
import { DevicesOrdered } from '../domain/device/devices-ordered/devices-ordered';
import { DevicesOfferedForSale } from '../domain/device/devices-offered-for-sale/devices-offered-for-sale';
import { DevicesToFinish } from '../domain/device/devices-to-finish/devices-to-finish';

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterModule,
    ClarityModule,
    ClrVerticalNavModule,
    AddDevice,
    DevicesOrdered,
    DevicesOfferedForSale,
    DevicesToFinish,
  ],
  templateUrl: './dashboard.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './dashboard.css',
})
export class Dashboard {
  orderedCount = 0;
  toFinishCount = 0;
  offeredForSaleCount = 0;
}
