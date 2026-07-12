import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClrInputModule, ClrSelectModule } from '@clr/angular';
import {
  BusinessPartnerAddressCountryControllerService,
  BusinessPartnerAddressCountryDto,
} from '@api/businesspartner';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-businesspartner-create',
  imports: [ClrInputModule, ReactiveFormsModule, ClrSelectModule],
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
  private router = inject(Router);

  ngOnInit(): void {
    this.businessPartnerAddressCountryApi
      .getAllCountries()
      .subscribe((data) => this.businessPartnerAddressCountries.set(data));
  }

  createBusinessPartner() {
    // todo
  }

  cancelForm() {
    if (!this.deviceId()) {
      this.isSubmitting.set(false);
    } else {
      this.router.navigate(['/devices', this.deviceId()]);
    }
  }
}
