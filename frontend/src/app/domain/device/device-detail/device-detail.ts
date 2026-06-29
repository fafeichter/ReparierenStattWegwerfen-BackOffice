import {Component, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {
  DeviceBaseDetailsDto,
  DeviceBuyingDetailsDto,
  DeviceDetailsControllerService,
  DeviceNoteDto,
  DeviceNotesControllerService,
  DeviceSellingDetailsDto
} from '@api/device';
import {ClrDatagridModule, ClrIcon, ClrLabel} from '@clr/angular';
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
  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);
  deviceSelling = signal<DeviceSellingDetailsDto | undefined>(undefined);
  deviceNotes = signal<DeviceNoteDto[]>([]);
  private route = inject(ActivatedRoute);
  private api = inject(DeviceDetailsControllerService);
  private notesApi = inject(DeviceNotesControllerService);

  ngOnInit(): void {
    let deviceId = Number(this.route.snapshot.paramMap.get('deviceId'));
    this.deviceId.set(deviceId);
    this.api.getDeviceBaseDetails(deviceId).subscribe(data => this.deviceBase.set(data));
    this.api.getDeviceBuyingDetails(deviceId).subscribe(data => this.deviceBuying.set(data));
    this.api.getDeviceSellingDetails(deviceId).subscribe(data => this.deviceSelling.set(data));
    this.notesApi.getDeviceNotes(deviceId).subscribe(data => this.deviceNotes.set(data));
  }
}
