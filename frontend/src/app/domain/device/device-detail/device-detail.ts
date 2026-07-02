import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {
  DeviceActivityControllerService,
  DeviceActivityDto,
  DeviceBaseDetailsDto,
  DeviceBuyingDetailsDto,
  DeviceDefectsDetailsDto,
  DeviceDetailsControllerService,
  DeviceNoteDto,
  DeviceNotesControllerService,
  DeviceSellingDetailsDto,
  DeviceSparePartDto,
  DeviceSparePartsControllerService,
  DeviceTagDto,
  DeviceTagsControllerService
} from '@api/device';
import {ClrDatagridModule, ClrDatagridSortOrder, ClrIcon, ClrLabel} from '@clr/angular';
import {OrElsePipe} from '../../../pipes/or-else-pipe';
import {CurrencyPipe, DatePipe} from '@angular/common';

@Component({
  selector: 'app-device-detail',
  imports: [
    ClrDatagridModule,
    ClrLabel,
    ClrIcon,
    OrElsePipe,
    RouterLink,
    DatePipe,
    CurrencyPipe
  ],
  templateUrl: './device-detail.html',
  standalone: true,
  styleUrl: './device-detail.css'
})
export class DeviceDetail implements OnInit {

  deviceId = signal<number | undefined>(undefined);
  deviceBase = signal<DeviceBaseDetailsDto | undefined>(undefined);
  deviceTags = signal<DeviceTagDto[]>([]);
  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);
  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);
  deviceNotes = signal<DeviceNoteDto[]>([]);
  deviceSpareParts = signal<DeviceSparePartDto[]>([]);
  deviceDefects = signal<DeviceDefectsDetailsDto | undefined>(undefined);
  totalSparePartsCost = computed(() => {
    return this.deviceSpareParts().reduce((sum, part) => sum + part.priceNetto, 0);
  });
  deviceActivities = signal<DeviceActivityDto[]>([]);

  private route = inject(ActivatedRoute);
  private api = inject(DeviceDetailsControllerService);
  private tagsApi = inject(DeviceTagsControllerService);
  private notesApi = inject(DeviceNotesControllerService);
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;
  private sparePartsApi = inject(DeviceSparePartsControllerService);
  private activityApi = inject(DeviceActivityControllerService);

  ngOnInit(): void {
    let deviceId = Number(this.route.snapshot.paramMap.get('deviceId'));
    this.deviceId.set(deviceId);
    this.api.getDeviceBaseDetails(deviceId).subscribe(data => this.deviceBase.set(data));
    this.tagsApi.getTagsForDevice(deviceId).subscribe(data => this.deviceTags.set(data));
    this.api.getDeviceBuyingDetails(deviceId).subscribe(data => this.deviceBuying.set(data));
    this.api.getDeviceSellingDetails(deviceId).subscribe(data => this.deviceSelling.set(data));
    this.notesApi.getDeviceNotes(deviceId).subscribe(data => this.deviceNotes.set(data));
    this.notesApi.getDeviceNotes(deviceId).subscribe(data => this.deviceNotes.set(data));
    this.sparePartsApi.getDeviceSpareParts(deviceId).subscribe(data => this.deviceSpareParts.set(data));
    this.api.getDeviceDefectDetails(deviceId).subscribe(data => this.deviceDefects.set(data));
    this.activityApi.getActivities(deviceId).subscribe(data => this.deviceActivities.set(data));
  }
}
