import { Component, computed, inject, linkedSignal, signal } from '@angular/core';
import { DeviceSearchControllerService, PagedModelDeviceDto } from '@api/device';
import {
  ClrCheckboxModule,
  ClrDatagridModule,
  ClrDatagridStateInterface,
  ClrLabel,
} from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-devices',
  imports: [
    ClrDatagridModule,
    OrElsePipe,
    DatePipe,
    RouterLink,
    ClrLabel,
    ClrCheckboxModule,
    FormsModule,
    CurrencyPipe,
  ],
  templateUrl: './devices.html',
  styleUrl: './devices.css',
})
export class Devices {
  showInactiveDevices = signal(false);
  protected page = signal(1);
  protected readonly pageSize = signal(20);

  // re-requested automatically whenever page, page size or the inactive toggle changes
  private readonly devicesResource = inject(DeviceSearchControllerService).searchResource(() => ({
    page: { page: this.page() - 1, size: this.pageSize() },
    includeInactiveDevices: this.showInactiveDevices(),
  }));
  // keeps the last page while the next one loads, so the pagination does not jump back
  private readonly devicesPage = linkedSignal<
    PagedModelDeviceDto | undefined,
    PagedModelDeviceDto | undefined
  >({
    source: () => (this.devicesResource.hasValue() ? this.devicesResource.value() : undefined),
    computation: (devicesPage, previous) => devicesPage ?? previous?.value,
  });

  protected readonly devices = computed(() => this.devicesPage()?.content ?? []);
  protected readonly lastPage = computed(() => this.devicesPage()?.page?.totalPages ?? 1);
  protected readonly totalItems = computed(() => this.devicesPage()?.page?.totalElements ?? 0);

  refresh(dataGridState: ClrDatagridStateInterface): void {
    const pageSize = dataGridState.page!.size ?? this.pageSize();
    const currentPage = dataGridState.page!.current ?? 1;

    if (pageSize !== this.pageSize()) {
      this.pageSize.set(pageSize);
      this.page.set(1);
    } else {
      this.page.set(currentPage);
    }
  }

  toggleInactiveDevices(showInactiveDevices: boolean) {
    this.showInactiveDevices.set(showInactiveDevices);
  }
}
