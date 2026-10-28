import { Component, inject, input, OnInit, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCheckboxModule,
  ClrCommonFormsModule,
  ClrModalModule,
} from '@clr/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import { DeviceInvoiceControllerService } from '@api/device';

@Component({
  imports: [
    ClrCardModule,
    ClrCheckboxModule,
    ClrCommonFormsModule,
    ClrModalModule,
    FormsModule,
    OrElsePipe,
    ReactiveFormsModule,
  ],
  selector: 'app-invoice',
  styleUrl: './invoice.css',
  templateUrl: './invoice.html',
})
export class Invoice implements OnInit {
  deviceId = input.required<number>();
  saleInvoiceNumber = signal<number | undefined>(undefined);

  private deviceInvoiceApi = inject(DeviceInvoiceControllerService);

  ngOnInit() {
    this.deviceInvoiceApi
      .getDeviceSaleInvoiceNumber(this.deviceId())
      .subscribe((saleInvoiceNumber) => {
        this.saleInvoiceNumber.set(saleInvoiceNumber);
      });
  }
}
