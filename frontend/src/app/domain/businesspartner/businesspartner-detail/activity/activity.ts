import { Component, computed, DestroyRef, inject, input } from '@angular/core';
import { BusinessPartnerActivityControllerService } from '@api/businesspartner';
import { DatePipe } from '@angular/common';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';

@Component({
  imports: [DatePipe, ClrCardModule, ClrIcon],
  selector: 'app-activity',
  styleUrl: './activity.css',
  templateUrl: './activity.html',
})
export class Activity {
  businessPartnerId = input.required<number>();
  navItem = input.required<NavItem>();

  private readonly activitiesResource = inject(
    BusinessPartnerActivityControllerService,
  ).getActivitiesResource(() => ({ businessPartnerId: this.businessPartnerId() }));

  // keeps the previous list while reloading, so the poll does not flicker
  readonly businessPartnerActivities = computed(() =>
    this.activitiesResource.hasValue() ? this.activitiesResource.value() : [],
  );

  constructor() {
    const pollId = setInterval(() => this.activitiesResource.reload(), 2500);
    inject(DestroyRef).onDestroy(() => clearInterval(pollId));
  }
}
