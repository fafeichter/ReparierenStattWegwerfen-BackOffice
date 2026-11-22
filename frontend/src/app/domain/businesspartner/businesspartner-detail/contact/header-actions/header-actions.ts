import { Component, inject, input, model } from '@angular/core';
import { ClrDropdownModule, ClrIcon } from '@clr/angular';
import { BusinessPartnerControllerService } from '@api/businesspartner';

@Component({
  imports: [ClrDropdownModule, ClrIcon],
  selector: 'app-header-actions',
  styleUrl: './header-actions.css',
  templateUrl: './header-actions.html',
})
export class HeaderActions {
  deviceId = input.required<number>();
  isScammer = model.required<boolean>();

  private deviceApi = inject(BusinessPartnerControllerService);

  markAsScammer() {
    if (confirm('Do you really want to mark this business partner as scammer?')) {
      this.deviceApi.markBusinessPartnerAsScammer(this.deviceId()).subscribe(() => {
        this.isScammer.set(true);
      });
    }
  }

  unmarkAsScammer() {
    if (confirm('Do you really want to unmark this business partner as scammer?')) {
      this.deviceApi.unmarkBusinessPartnerAsScammer(this.deviceId()).subscribe(() => {
        this.isScammer.set(false);
      });
    }
  }
}
