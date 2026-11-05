import { Component, effect, inject, input, OnInit, signal } from '@angular/core';
import {
  ClrAlertModule,
  ClrCardModule,
  ClrCommonFormsModule,
  ClrFileInputModule,
  ClrIcon,
  ClrLabel,
  ClrSpinnerModule,
} from '@clr/angular';
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
import { NavItem } from '../../../../clarity-addons/components/scrollspy/scrollspy';

@Component({
  imports: [
    ClrCommonFormsModule,
    ClrFileInputModule,
    ClrIcon,
    FormsModule,
    OrElsePipe,
    ReactiveFormsModule,
    ClrLabel,
    ClrCardModule,
    ClrSpinnerModule,
    ClrAlertModule,
  ],
  selector: 'app-contact',
  styleUrl: './contact.css',
  templateUrl: './contact.html',
})
export class Contact implements OnInit {
  businessPartnerId = input.required<number>();
  navItem = input.required<NavItem>();
  readonly businessPartner = signal<BusinessPartnerDetailDto | undefined>(undefined);
  form = new FormGroup({
    shippingLabelImage: new FormControl<FileList | null>(null, [Validators.required]),
  });
  isLoading = signal<boolean>(false);
  success = signal<boolean>(false);
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

  constructor() {
    effect(() => {
      if (this.addressEditModeActive()) {
        this.success.set(false);
      }
    });
  }

  ngOnInit(): void {
    this.api
      .getBusinessPartnerDetails(this.businessPartnerId()!)
      .subscribe((data) => this.businessPartner.set(data));
  }

  updateAddress() {
    const shippingLabelImage: File = this.form.controls.shippingLabelImage.value![0];

    if (shippingLabelImage) {
      this.isLoading.set(true);
      this.businessPartnerAdressExtractionApi
        .extractAddressFromImage(this.businessPartnerId()!, shippingLabelImage)
        .subscribe(() => {
          this.api.getBusinessPartnerDetails(this.businessPartnerId()!).subscribe((data) => {
            this.businessPartner.set(data);
            this.success.set(true);
            this.resetForm();
          });
        });
    } else {
      this.resetForm();
    }
  }

  cancelForm() {
    if (!this.deviceId()) {
      this.addressEditModeActive.set(false);
      this.isLoading.set(false);
    } else {
      this.router.navigate(['/devices', this.deviceId()]);
    }
  }

  private resetForm() {
    this.isLoading.set(false);
    this.form.reset();
    this.form.markAsUntouched();
  }
}
