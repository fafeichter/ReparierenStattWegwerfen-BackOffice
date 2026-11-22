import { Component, computed, inject, linkedSignal, signal } from '@angular/core';
import {
  BusinessPartnerSearchControllerService,
  PagedModelBusinessPartnerDto,
} from '@api/businesspartner';
import {
  ClrDatagridModule,
  ClrDatagridStateInterface,
  ClrLabel,
  ClrTabsModule,
} from '@clr/angular';
import { RouterLink } from '@angular/router';
import { OrElsePipe } from '../../../pipes/or-else-pipe';

@Component({
  selector: 'app-businesspartners',
  imports: [ClrDatagridModule, ClrTabsModule, RouterLink, ClrLabel, OrElsePipe],
  templateUrl: './business-partners.component.html',
  styleUrl: './business-partners.component.css',
})
export class BusinessPartners {
  protected page = signal(1);
  protected readonly pageSize = signal(10);

  // re-requested automatically whenever page or page size changes
  private readonly businessPartnersResource = inject(
    BusinessPartnerSearchControllerService,
  ).searchResource(() => ({ page: { page: this.page() - 1, size: this.pageSize() } }));
  // keeps the last page while the next one loads, so the pagination does not jump back
  private readonly businessPartnersPage = linkedSignal<
    PagedModelBusinessPartnerDto | undefined,
    PagedModelBusinessPartnerDto | undefined
  >({
    source: () =>
      this.businessPartnersResource.hasValue() ? this.businessPartnersResource.value() : undefined,
    computation: (businessPartnersPage, previous) => businessPartnersPage ?? previous?.value,
  });

  readonly businessPartners = computed(() => this.businessPartnersPage()?.content ?? []);
  protected readonly lastPage = computed(() => this.businessPartnersPage()?.page?.totalPages ?? 1);
  protected readonly totalItems = computed(
    () => this.businessPartnersPage()?.page?.totalElements ?? 0,
  );

  refresh(dataGridState: ClrDatagridStateInterface): void {
    const pageSize = dataGridState.page!.size ?? this.pageSize();
    const currentPage = dataGridState.page!.current ?? 1;

    if (pageSize !== this.pageSize()) {
      this.pageSize.set(pageSize);
      this.page.set(1);
    } else {
      this.page.set(currentPage);
    }
  }
}
