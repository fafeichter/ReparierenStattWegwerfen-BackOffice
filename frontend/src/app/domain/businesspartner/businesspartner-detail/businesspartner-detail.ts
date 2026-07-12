import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import {
  BusinessPartnerAddressExtractionControllerService,
  BusinessPartnerControllerService,
  BusinessPartnerDetailDto,
} from '@api/businesspartner';
import { ClrFileInputModule, ClrLabel } from '@clr/angular';
import { OrElsePipe } from '../../../pipes/or-else-pipe';
import { DeviceBusinessPartnerControllerService, DeviceBusinesspartnerDto } from '@api/device';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-businesspartner-detail',
  imports: [ClrLabel, OrElsePipe, RouterLink, ClrFileInputModule, ReactiveFormsModule],
  templateUrl: './businesspartner-detail.html',
  styleUrl: './businesspartner-detail.css',
})
export class BusinesspartnerDetail {
  readonly businessPartnerId = signal<number | undefined>(undefined);
  readonly businessPartner = signal<BusinessPartnerDetailDto | undefined>(undefined);
  readonly businessPartnerDevices = signal<DeviceBusinesspartnerDto | undefined>(undefined);
  form = new FormGroup({
    shippingLabelImage: new FormControl<FileList | null>(null, [Validators.required]),
  });

  private route = inject(ActivatedRoute);
  addressEditModeActive = signal<boolean>(
    this.route.snapshot.queryParamMap.get('edit-address') === 'true',
  );
  private rawDeviceId = this.route.snapshot.queryParamMap.get('device-id');
  deviceId = signal<number | undefined>(this.rawDeviceId ? Number(this.rawDeviceId) : undefined);
  private api = inject(BusinessPartnerControllerService);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private businessPartnerAdressExtractionApi = inject(
    BusinessPartnerAddressExtractionControllerService,
  );
  private router = inject(Router);

  ngOnInit(): void {
    this.businessPartnerId.set(Number(this.route.snapshot.paramMap.get('businessPartnerId')));

    this.api
      .getBusinessPartnerDetails(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartner.set(data));

    this.deviceBusinessPartnerApi
      .getDevicesOfBusinessPartner(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartnerDevices.set(data));
  }

  updateAddress() {
    const shippingLabelImage: File = this.form.controls.shippingLabelImage.value![0];

    if (shippingLabelImage) {
      this.businessPartnerAdressExtractionApi
        .extractAddressFromImage(this.businessPartnerId()!, shippingLabelImage)
        .subscribe(() => {
          this.api
            .getBusinessPartnerDetails(this.businessPartnerId()!)
            .subscribe((data) => this.businessPartner.set(data));
        });
    }
  }

  cancelForm() {
    if (!this.deviceId()) {
      this.addressEditModeActive.set(false);
    } else {
      this.router.navigate(['/devices', this.deviceId()]);
    }
  }
}
