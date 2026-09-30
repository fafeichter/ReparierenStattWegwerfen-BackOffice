import { Component, inject, input, OnInit, signal } from '@angular/core';
import { DeviceBuyingDetailsDto, DeviceDetailsControllerService } from '@api/device';
import { ClrCommonFormsModule, ClrFileInputModule, ClrIcon } from '@clr/angular';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-buying',
  imports: [
    ClrIcon,
    CurrencyPipe,
    DatePipe,
    RouterLink,
    ClrCommonFormsModule,
    ClrFileInputModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './buying.html',
  styleUrl: './buying.css',
})
export class Buying implements OnInit {
  deviceId = input.required<number>();

  deviceBuying = signal<DeviceBuyingDetailsDto | undefined>(undefined);

  private api = inject(DeviceDetailsControllerService);

  ngOnInit(): void {
    this.api
      .getDeviceBuyingDetails(this.deviceId())
      .subscribe((data) => this.deviceBuying.set(data));
  }
}
