import { Component, computed, inject, input, signal } from '@angular/core';
import { DeviceSellingAccessoriesControllerService } from '@api/device';
import { ClrIcon } from '@clr/angular';
import { SellingAccessoriesModal } from './selling-accessories-modal/selling-accessories-modal';

@Component({
  imports: [SellingAccessoriesModal, ClrIcon, SellingAccessoriesModal],
  selector: 'app-accessories',
  styleUrl: './accessories.css',
  templateUrl: './accessories.html',
})
export class Accessories {
  deviceId = input.required<number>();
  modalOpen = signal<boolean>(false);

  private deviceAccessoriesApi = inject(DeviceSellingAccessoriesControllerService);
  private readonly deviceAccessoriesResource =
    this.deviceAccessoriesApi.getSellingAccessoriesResource(() => ({ deviceId: this.deviceId() }));
  readonly deviceAccessories = computed(() =>
    this.deviceAccessoriesResource.hasValue() ? this.deviceAccessoriesResource.value() : undefined,
  );
}
