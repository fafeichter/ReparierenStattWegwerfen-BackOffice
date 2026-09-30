import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClrButtonGroupModule, ClrInputModule, ClrSelectModule } from '@clr/angular';
import {
  BusinessPartnerAddressCountryControllerService,
  BusinessPartnerAddressCountryDto,
} from '@api/businesspartner';
import { ActivatedRoute, Router } from '@angular/router';
import {
  CreateBuyerBusinessPartnerForDeviceDto,
  DeviceBusinessPartnerControllerService,
} from '@api/device';

@Component({
  selector: 'app-businesspartner-create',
  imports: [ClrInputModule, ReactiveFormsModule, ClrSelectModule, ClrButtonGroupModule],
  templateUrl: './businesspartner-create.html',
  styleUrl: './businesspartner-create.css',
})
export class BusinesspartnerCreate implements OnInit {
  businessPartnerAddressCountries = signal<BusinessPartnerAddressCountryDto[]>([]);
  businessPartnerForm = new FormGroup({
    firstName: new FormControl<string | null>(null, [Validators.required]),
    lastName: new FormControl<string | null>(null, [Validators.required]),
    street: new FormControl<string | null>(null, [Validators.required]),
    houseNumber: new FormControl<string | null>(null, [Validators.required]),
    zipCode: new FormControl<string | null>(null, [Validators.required]),
    city: new FormControl<string | null>(null, [Validators.required]),
    businessPartnerCountryId: new FormControl<number | null>(null, [Validators.required]),
  });
  isSubmitting = signal<boolean>(false);
  private route = inject(ActivatedRoute);
  private rawDeviceId = this.route.snapshot.queryParamMap.get('device-id');
  deviceId = signal<number | undefined>(this.rawDeviceId ? Number(this.rawDeviceId) : undefined);
  private businessPartnerAddressCountryApi = inject(BusinessPartnerAddressCountryControllerService);
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private router = inject(Router);

  ngOnInit(): void {
    this.businessPartnerAddressCountryApi
      .getAllCountries()
      .subscribe((data) => this.businessPartnerAddressCountries.set(data));
  }

  createBusinessPartner() {
    this.isSubmitting.set(true);

    const businessPartner: CreateBuyerBusinessPartnerForDeviceDto = {
      firstName: this.businessPartnerForm.controls.firstName.value!,
      lastName: this.businessPartnerForm.controls.firstName.value!,
      deviceId: this.deviceId()!,
      street: this.businessPartnerForm.controls.street.value!,
      houseNumber: this.businessPartnerForm.controls.houseNumber.value!,
      zipCode: this.businessPartnerForm.controls.zipCode.value!,
      city: this.businessPartnerForm.controls.city.value!,
      countryId: this.businessPartnerForm.controls.businessPartnerCountryId.value!,
    };

    this.deviceBusinessPartnerApi
      .createBuyerBusinessPartnerForDevice(businessPartner)
      .subscribe(() => {
        this.isSubmitting.set(false);

        if (!this.deviceId()) {
          this.isSubmitting.set(false);
        } else {
          this.router.navigate(['/devices', this.deviceId()]);
        }
      });
  }

  cancelForm() {
    if (!this.deviceId()) {
      this.isSubmitting.set(false);
    } else {
      this.router.navigate(['/devices', this.deviceId()]);
    }
  }
}
