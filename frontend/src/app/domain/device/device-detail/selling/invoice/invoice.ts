import { Component, computed, inject, input } from '@angular/core';
import { OrElsePipe } from '../../../../../pipes/or-else-pipe';
import { DeviceSellingInvoiceControllerService } from '@api/device';

@Component({
  imports: [OrElsePipe],
  selector: 'app-invoice',
  styleUrl: './invoice.css',
  templateUrl: './invoice.html',
})
export class Invoice {
  deviceId = input.required<number>();

  private readonly invoiceNumberResource = inject(
    DeviceSellingInvoiceControllerService,
  ).getDeviceSellingInvoiceNumberResource(() => ({ deviceId: this.deviceId() }));

  readonly invoiceNumber = computed(() =>
    this.invoiceNumberResource.hasValue() ? this.invoiceNumberResource.value() : undefined,
  );
}
