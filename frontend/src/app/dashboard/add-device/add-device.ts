import {Component, effect, ElementRef, inject, signal, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {JsonPipe, NgTemplateOutlet} from '@angular/common';

import {
  ClrButtonModule,
  ClrFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrSpinnerModule,
  ClrTooltipModule
} from "@clr/angular";

import {Alternative, ModelControllerService, ResolvedModelMatch} from '@api/model';
import {CreateNewDevice, DeviceControllerService} from '@api/device';
import {OrElsePipe} from '../../pipes/or-else-pipe';
import {BusinessPartnerCreationControllerService, CreateBusinessPartnerPlaceholder} from '@api/businesspartner';

@Component({
  selector: 'app-add-device',
  imports: [
    ClrIcon,
    ClrModalModule,
    ClrFormsModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    ClrSpinnerModule,
    OrElsePipe,
    ClrTooltipModule,
    NgTemplateOutlet,
    ClrButtonModule
  ],
  templateUrl: './add-device.html',
  styleUrl: './add-device.css',
})
export class AddDevice {
  @ViewChild('input') urlInput!: ElementRef<HTMLInputElement>;

  modalOpened = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);
  isLoadingModel = signal<boolean>(false);
  found = signal<ResolvedModelMatch | undefined>(undefined);
  selectedAlternative = signal<number>(-1);

  form = new FormGroup({
    url: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern(/https?:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)/)
      ]
    }),
    price: new FormControl<number | null>(null, [
      Validators.required,
      Validators.min(0.01)
    ]),
  });

  private modelApi = inject(ModelControllerService);
  private deviceApi = inject(DeviceControllerService);
  private businessPartnerApi = inject(BusinessPartnerCreationControllerService);
  private router = inject(Router);

  constructor() {
    effect(() => {
      if (this.modalOpened()) {
        this.resetFormState();
        // Uses standard frame animation timing instead of arbitrary magic numbers
        requestAnimationFrame(() => this.urlInput?.nativeElement.focus());
      }
    });
  }

  addDevice(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const candidate = this.getSelectedCandidate();
    if (!candidate) return;

    this.isSubmitting.set(true);

    const newBusinessPartner: CreateBusinessPartnerPlaceholder = {
      firstName: this.found()?.sellerFirstName || '',
      lastName: this.found()?.sellerLastName,
    }

    this.businessPartnerApi.createBusinessPartnerPlaceholder(newBusinessPartner).subscribe(data => {
        const newDevice: CreateNewDevice = {
          modelId: candidate.model?.id || 0,
          purchasePrice: Number(this.form.controls.price.value),
          modelColorId: candidate.modelColor?.id,
          modelAppleSiliconId: candidate.modelAppleSilicon?.id,
          modelAppleSiliconUnifiedMemoryId: candidate.modelAppleSiliconUnifiedMemory?.id,
          modelStorageId: candidate.modelStorage?.id,
          url: this.form.controls.url.value,
          serialNumber: candidate.serialNumber,
          batteryMaximumCapacity: candidate.batteryMaximumCapacity,
          batteryCycleCount: candidate.batteryCycleCount,
          defect: this.found()?.reportedDefect,
          sellerBusinessPartnerId: data,

        };

        this.deviceApi.createNewDevice(newDevice).subscribe({
          next: (deviceId) => this.router.navigate(['/devices', deviceId]),
          error: () => this.isSubmitting.set(false)
        });

    });

  }

  protected selectUrlText(): void {
    if (this.form.controls.url.valid) {
      this.isLoadingModel.set(true);
      const adUrl = this.form.controls.url.value;

      this.modelApi.getModelNumberFromAdUrl(adUrl).subscribe({
        next: (data) => {
          this.found.set(data);
          this.isLoadingModel.set(false);
        },
        error: () => this.isLoadingModel.set(false)
      });
    }
  }

  protected getConfidenceColor(confidence: number | undefined): string {
    if (!confidence) return 'Tomato';
    if (confidence >= 8) return 'YellowGreen';
    if (confidence >= 5) return 'BlanchedAlmond';
    return 'Tomato';
  }

  private getSelectedCandidate(): Alternative | ResolvedModelMatch | undefined {
    const currentFound = this.found();
    const altIdx = this.selectedAlternative();

    if (altIdx === -1) {
      return currentFound;
    }
    return currentFound?.alternativeCandidates?.[altIdx];
  }

  private resetFormState(): void {
    this.form.reset();
    this.found.set(undefined);
    this.selectedAlternative.set(-1);
    this.isSubmitting.set(false);
  }
}