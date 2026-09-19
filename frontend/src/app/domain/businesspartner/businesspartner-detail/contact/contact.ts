import { Component, inject, input, OnInit, signal } from '@angular/core';
import { ClrCommonFormsModule, ClrFileInputModule, ClrIcon, ClrLabel } from '@clr/angular';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { OrElsePipe } from '../../../../pipes/or-else-pipe';
import {
  BusinessPartnerAddressExtractionControllerService,
  BusinessPartnerControllerService,
  BusinessPartnerDetailDto,
} from '@api/businesspartner';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrFileInputModule,
    ClrIcon,
    ClrLabel,
    FormsModule,
    OrElsePipe,
    ReactiveFormsModule,
  ],
  selector: 'app-contact',
  styleUrl: './contact.css',
  templateUrl: './contact.html',
})
export class Contact implements OnInit {
  businessPartnerId = input.required<number>();
  readonly businessPartner = signal<BusinessPartnerDetailDto | undefined>(undefined);
  form = new FormGroup({
    shippingLabelImage: new FormControl<FileList | null>(null, [Validators.required]),
  });
  private businessPartnerAdressExtractionApi = inject(
    BusinessPartnerAddressExtractionControllerService,
  );
  private router = inject(Router);
  private api = inject(BusinessPartnerControllerService);

  private route = inject(ActivatedRoute);
  addressEditModeActive = signal<boolean>(
    this.route.snapshot.queryParamMap.get('edit-address') === 'true',
  );

  private rawDeviceId = this.route.snapshot.queryParamMap.get('device-id');
  deviceId = signal<number | undefined>(this.rawDeviceId ? Number(this.rawDeviceId) : undefined);

  ngOnInit(): void {
    this.api
      .getBusinessPartnerDetails(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartner.set(data));
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
