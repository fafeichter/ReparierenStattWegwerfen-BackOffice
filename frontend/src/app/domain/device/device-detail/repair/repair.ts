import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { ClrDatagridModule } from '@clr/angular';
import { CurrencyPipe } from '@angular/common';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  DeviceDefectsDetailsDto,
  DeviceDetailsControllerService,
  DeviceSparePartDto,
  DeviceSparePartsControllerService,
} from '@api/device';

@Component({
  selector: 'app-repair',
  imports: [ClrDatagridModule, CurrencyPipe, OrElsePipe],
  templateUrl: './repair.html',
  styleUrl: './repair.css',
})
export class Repair implements OnInit {
  deviceId = input.required<number>();

  deviceDefects = signal<DeviceDefectsDetailsDto | undefined>(undefined);
  deviceSpareParts = signal<DeviceSparePartDto[]>([]);
  totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });

  private api = inject(DeviceDetailsControllerService);
  private sparePartsApi = inject(DeviceSparePartsControllerService);

  ngOnInit(): void {
    this.sparePartsApi
      .getDeviceSpareParts(this.deviceId())
      .subscribe((data) => this.deviceSpareParts.set(data));
    this.api
      .getDeviceDefectDetails(this.deviceId())
      .subscribe((data) => this.deviceDefects.set(data));
  }
}
