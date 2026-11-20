import {Component, inject, signal} from '@angular/core';
import {BusinessPartnerDto, BusinessPartnerSearchControllerService} from '@api/businesspartner';
import {ClrDatagridModule, ClrDatagridStateInterface, ClrLabel, ClrTabsModule} from '@clr/angular';
import {RouterLink} from '@angular/router';
import {OrElsePipe} from '../../../pipes/or-else-pipe';
import {Pageable} from '@api/device';

@Component({
  selector: 'app-businesspartners',
  imports: [ClrDatagridModule, ClrTabsModule, RouterLink, ClrLabel, OrElsePipe],
  templateUrl: './business-partners.component.html',
  styleUrl: './business-partners.component.css',
})
export class BusinessPartners {
  protected page = signal(1);
  protected readonly pageSize = signal(10);
  protected readonly lastPage = signal(1);
  protected readonly totalItems = signal(0);
  private api = inject(BusinessPartnerSearchControllerService);
  private _businessPartners = signal<BusinessPartnerDto[]>([]);
  readonly businessPartners = this._businessPartners.asReadonly();
  private _businessPartnersLoading = signal<boolean>(false);
  readonly businessPartnersLoading = this._businessPartnersLoading.asReadonly();

  refresh(dataGridState: ClrDatagridStateInterface): void {
    const pageSize = dataGridState.page!.size ?? this.pageSize();
    const currentPage = dataGridState.page!.current ?? 1;

    if (pageSize !== this.pageSize()) {
      this.pageSize.set(pageSize);
      this.page.set(1);
    } else {
      this.page.set(currentPage);
    }

    this.search();
  }

  search() {
    this._businessPartnersLoading.set(true);

    const page: Pageable = {
      page: this.page() - 1,
      size: this.pageSize()
    }
    this.api.search(page).subscribe((businessPartnersPage) => {
      this._businessPartners.set(businessPartnersPage.content ?? []);
      this.lastPage.set(businessPartnersPage.page?.totalPages ?? 1);
      this.totalItems.set(businessPartnersPage.page?.totalElements ?? 0);
      this._businessPartnersLoading.set(false);
    });
  }
}
