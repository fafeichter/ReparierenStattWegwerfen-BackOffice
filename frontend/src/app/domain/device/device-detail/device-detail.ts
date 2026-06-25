import {Component, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Device, DeviceControllerService} from '@api/device';
import {ClrDatagridModule, ClrIcon, ClrLabel} from '@clr/angular';

@Component({
  selector: 'app-device-detail',
  imports: [
    ClrDatagridModule,
    ClrLabel,
    ClrIcon
  ],
  templateUrl: './device-detail.html',
  styleUrl: './device-detail.css',
})
export class DeviceDetail implements OnInit {

  device = signal<Device | undefined>(undefined);
  deviceId = signal<number | undefined>(undefined);
  private route = inject(ActivatedRoute);
  private api = inject(DeviceControllerService);

  ngOnInit(): void {
    let deviceId = Number(this.route.snapshot.paramMap.get('deviceId'));
    this.deviceId.set(deviceId);
    this.api.getDeviceDetails(deviceId).subscribe(data => this.device.set(data));

  }
}
