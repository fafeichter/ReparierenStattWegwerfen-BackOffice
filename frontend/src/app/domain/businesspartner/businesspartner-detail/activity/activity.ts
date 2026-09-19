import { Component, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { interval, startWith, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  BusinessPartnerActivityControllerService,
  BusinessPartnerActivityDto,
} from '@api/businesspartner';
import { DatePipe } from '@angular/common';

@Component({
  imports: [DatePipe],
  selector: 'app-activity',
  styleUrl: './activity.css',
  templateUrl: './activity.html',
})
export class Activity implements OnInit {
  businessPartnerId = input.required<number>();

  businessPartnerActivities = signal<BusinessPartnerActivityDto[]>([]);

  private activityApi = inject(BusinessPartnerActivityControllerService);

  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    interval(5000)
      .pipe(
        startWith(0),
        switchMap(() => this.activityApi.getActivities(this.businessPartnerId())),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe((data) => this.businessPartnerActivities.set(data));
  }
}
