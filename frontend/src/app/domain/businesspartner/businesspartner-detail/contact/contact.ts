import { Component, computed, effect, inject, input, signal } from '@angular/core';
import {
  ClrAlertModule,
  ClrCardModule,
  ClrCommonFormsModule,
  ClrDropdownModule,
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
} from '@api/businesspartner';
import { ActivatedRoute, Router } from '@angular/router';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';
import { HeaderActions } from './header-actions/header-actions';

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
    ClrDropdownModule,
    HeaderActions,
  ],
  selector: 'app-contact',
  styleUrl: './contact.css',
  templateUrl: './contact.html',
})
export class Contact {
  businessPartnerId = input.required<number>();
  navItem = input.required<NavItem>();
  form = new FormGroup({
    shippingLabelImage: new FormControl<FileList | null>(null, [Validators.required]),
  });
  isLoading = signal<boolean>(false);
  success = signal<boolean>(false);
  private businessPartnerAdressExtractionApi = inject(
    BusinessPartnerAddressExtractionControllerService,
  );
  private router = inject(Router);
  private readonly businessPartnerResource = inject(
    BusinessPartnerControllerService,
  ).getBusinessPartnerDetailsResource(() => ({ businessPartnerId: this.businessPartnerId() }));
  readonly businessPartner = computed(() =>
    this.businessPartnerResource.hasValue() ? this.businessPartnerResource.value() : undefined,
  );
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

  updateAddress() {
    const shippingLabelImage: File = this.form.controls.shippingLabelImage.value![0];

    if (shippingLabelImage) {
      this.isLoading.set(true);
      this.businessPartnerAdressExtractionApi
        .extractAddressFromImage(this.businessPartnerId()!, shippingLabelImage)
        .subscribe(() => {
          this.businessPartnerResource.reload();
          this.success.set(true);
          this.resetForm();
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

  setScammerFlag(isScammer: boolean) {
    this.businessPartnerResource.value.update((businessPartner) =>
      businessPartner
        ? {
            ...businessPartner,
            scammer: isScammer,
          }
        : businessPartner,
    );
  }

  private resetForm() {
    this.isLoading.set(false);
    this.form.reset();
    this.form.markAsUntouched();
  }
}
