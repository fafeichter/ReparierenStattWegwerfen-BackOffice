import { Component, computed, inject, input, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {
  DeviceActivityControllerService,
  DeviceActivityDto,
  DeviceBaseDetailsDto,
  DeviceBuyingDetailsDto,
  DeviceDefectsDetailsDto,
  DeviceDetailsControllerService,
  DeviceSellingDetailsDto,
  DeviceSparePartDto,
  DeviceSparePartsControllerService,
  DeviceTagDto,
  DeviceTagsControllerService,
} from '@api/device';
import {
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrDatagridSortOrder,
  ClrIcon,
  ClrInputModule,
  ClrLabel,
  ClrModalModule,
  ClrNumberInputModule,
  ClrRadioModule,
  ClrSpinnerModule,
  ClrTextareaModule,
} from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Notes } from './notes/notes';

@Component({
  selector: 'app-device-detail',
  imports: [
    ClrDatagridModule,
    ClrLabel,
    ClrIcon,
    OrElsePipe,
    RouterLink,
    DatePipe,
    CurrencyPipe,
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
  deviceTags = signal<DeviceTagDto[]>([]);
  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);
  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);
  deviceSpareParts = signal<DeviceSparePartDto[]>([]);
  deviceDefects = signal<DeviceDefectsDetailsDto | undefined>(undefined);
  totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });
  deviceActivities = signal<DeviceActivityDto[]>([]);

  private route = inject(ActivatedRoute);
  private api = inject(DeviceDetailsControllerService);
  private tagsApi = inject(DeviceTagsControllerService);

  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;
  private sparePartsApi = inject(DeviceSparePartsControllerService);
  private activityApi = inject(DeviceActivityControllerService);

  ngOnInit(): void {
    this.api.getDeviceBaseDetails(this.deviceId()).subscribe((data) => this.deviceBase.set(data));
    this.tagsApi.getTagsForDevice(this.deviceId()).subscribe((data) => this.deviceTags.set(data));
    this.api
      .getDeviceBuyingDetails(this.deviceId())
      .subscribe((data) => this.deviceBuying.set(data));
    this.api
      .getDeviceSellingDetails(this.deviceId())
      .subscribe((data) => this.deviceSelling.set(data));
    this.sparePartsApi
      .getDeviceSpareParts(this.deviceId())
      .subscribe((data) => this.deviceSpareParts.set(data));
    this.api
      .getDeviceDefectDetails(this.deviceId())
      .subscribe((data) => this.deviceDefects.set(data));
    this.activityApi
      .getActivities(this.deviceId())
      .subscribe((data) => this.deviceActivities.set(data));
  }
}
