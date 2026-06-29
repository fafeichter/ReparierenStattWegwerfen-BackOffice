import {Component, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {DeviceBaseDetailsDto, DeviceBuyingDetailsDto, DeviceDetailsControllerService} from '@api/device';
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

  deviceBase = signal<DeviceBaseDetailsDto | undefined>(undefined);
  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);
  deviceId = signal<number | undefined>(undefined);
  private route = inject(ActivatedRoute);
  private api = inject(DeviceDetailsControllerService);

  ngOnInit(): void {
    let deviceId = Number(this.route.snapshot.paramMap.get('deviceId'));
    this.deviceId.set(deviceId);
    this.api.getDeviceBaseDetails(deviceId).subscribe(data => this.deviceBase.set(data));
    this.api.getDeviceBuyingDetails(deviceId).subscribe(data => this.deviceBuying.set(data));
  }
}
