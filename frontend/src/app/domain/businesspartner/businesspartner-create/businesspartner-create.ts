import { Component, computed, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  ClrButtonGroupModule,
  ClrInputModule,
  ClrLoadingButtonModule,
  ClrLoadingModule,
  ClrSelectModule,
} from '@clr/angular';
import { BusinessPartnerAddressCountryControllerService } from '@api/businesspartner';
import { ActivatedRoute, Router } from '@angular/router';
import {
  CreateBuyerBusinessPartnerForDeviceDto,
  DeviceBusinessPartnerControllerService,
} from '@api/device';
import { Autofocus } from '../../../clarity-addons/input/autofocus';

@Component({
  selector: 'app-businesspartner-create',
  imports: [
    ClrInputModule,
    ReactiveFormsModule,
    ClrSelectModule,
    ClrButtonGroupModule,
    Autofocus,
    ClrLoadingModule,
    ClrLoadingButtonModule,
  ],
  templateUrl: './businesspartner-create.html',
  styleUrl: './businesspartner-create.css',
})
export class BusinesspartnerCreate {
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
  private readonly countriesResource = inject(
    BusinessPartnerAddressCountryControllerService,
  ).getAllCountriesResource();
  readonly businessPartnerAddressCountries = computed(() =>
    this.countriesResource.hasValue() ? this.countriesResource.value() : [],
  );
  private deviceBusinessPartnerApi = inject(DeviceBusinessPartnerControllerService);
  private router = inject(Router);

  createBusinessPartner() {
    this.isSubmitting.set(true);

    const businessPartner: CreateBuyerBusinessPartnerForDeviceDto = {
      firstName: this.businessPartnerForm.controls.firstName.value!,
      lastName: this.businessPartnerForm.controls.lastName.value!,
      street: this.businessPartnerForm.controls.street.value!,
      houseNumber: this.businessPartnerForm.controls.houseNumber.value!,
      zipCode: this.businessPartnerForm.controls.zipCode.value!,
      city: this.businessPartnerForm.controls.city.value!,
      countryId: this.businessPartnerForm.controls.businessPartnerCountryId.value!,
    };

    this.deviceBusinessPartnerApi
      .createBuyerBusinessPartnerForDevice(this.deviceId()!, businessPartner)
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
