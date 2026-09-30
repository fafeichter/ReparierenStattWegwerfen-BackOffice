import { Component, inject, signal } from '@angular/core';
import { DeviceDto, DeviceSearchControllerService } from '@api/device';
import {
  ClrCheckboxModule,
  ClrDatagridModule,
  ClrDatagridStateInterface,
  ClrLabel,
} from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { DatePipe } from '@angular/common';
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
  ],
  templateUrl: './devices.html',
  styleUrl: './devices.css',
})
export class Devices {
  showInactiveDevices = signal(false);
  protected readonly loading = signal(true);
  protected readonly devices = signal<DeviceDto[]>([]);
  protected readonly page = signal(1);
  protected readonly pageSize = signal(10);
  protected readonly lastPage = signal(1);
  protected readonly totalItems = signal(0);
  private readonly devicesSearchApi = inject(DeviceSearchControllerService);

  refresh(state: ClrDatagridStateInterface): void {
    if (!state.page) {
      return;
    }

    const pageSize = state.page.size ?? this.pageSize();
    const currentPage = state.page.current ?? 1;

    if (pageSize !== this.pageSize()) {
      this.pageSize.set(pageSize);
      this.page.set(1);
    } else {
      this.page.set(currentPage);
    }

    this.search();
  }

  search(): void {
    this.loading.set(true);

    this.devicesSearchApi
      .search(this.pageSize(), this.page(), this.showInactiveDevices())
      .subscribe((devicesPage) => {
        this.devices.set(devicesPage.content ?? []);
        this.lastPage.set(devicesPage.page?.totalPages ?? 1);
        this.totalItems.set(devicesPage.page?.totalElements ?? 0);
        this.loading.set(false);
      });
  }

  toggleInactiveDevices(showInactiveDevices: boolean) {
    this.showInactiveDevices.set(showInactiveDevices);
    this.search();
  }
}
