import { Component, inject, OnInit, signal } from '@angular/core';
import {
  ClrDatagridComparatorInterface,
  ClrDatagridModule,
  ClrDatagridSortOrder,
  ClrDatagridStringFilterInterface,
  ClrTabsModule,
} from '@clr/angular';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ModelControllerService, ModelDto } from '@api/model';
import { DatePipe } from '@angular/common';

class ReleaseDateComparator implements ClrDatagridComparatorInterface<ModelDto> {
  compare(a: ModelDto, b: ModelDto): number {
    return (
      +new Date(a.releaseYear, a.releaseMonth - 1) - +new Date(b.releaseYear, b.releaseMonth - 1)
    );
  }
}

class NameFilter implements ClrDatagridStringFilterInterface<ModelDto> {
  accepts(item: ModelDto, search: string): boolean {
    return item.name.toLowerCase().includes(search.toLowerCase());
  }
}

class ReleaseDateFilter implements ClrDatagridStringFilterInterface<ModelDto> {
  private datePipe = inject(DatePipe);

  accepts(item: ModelDto, search: string): boolean {
    return (
      this.datePipe.transform(+new Date(item.releaseYear, item.releaseMonth - 1), 'MMM yyyy') || ''
    )
      .toLowerCase()
      .includes(search.toLowerCase());
  }
}

export enum ModelTab {
  MacBook = 'macbook',
  IPad = 'ipad',
}

@Component({
  selector: 'app-models',
  imports: [ClrDatagridModule, RouterLink, ClrTabsModule, DatePipe],
  providers: [DatePipe],
  templateUrl: './models.html',
  styleUrl: './models.css',
})
export class Models implements OnInit {
  activeTab: ModelTab = ModelTab.MacBook;
  public releaseDateComparator = new ReleaseDateComparator();
  public nameFilter = new NameFilter();
  public releaseDateFilter = new ReleaseDateFilter();
  protected readonly ModelTab = ModelTab;
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;
  private api = inject(ModelControllerService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  // 1. Private state signal
  private _macbooks = signal<ModelDto[]>([]);
  // 2. Public read-only signals
  readonly macbooks = this._macbooks.asReadonly();
  private _macbooksLoading = signal<boolean>(false);
  readonly macbooksLoading = this._macbooksLoading.asReadonly();
  // 1. Private state signal
  private _ipads = signal<ModelDto[]>([]);
  // 2. Public read-only signals
  readonly ipads = this._ipads.asReadonly();
  private _ipadsLoading = signal<boolean>(false);
  readonly ipadsLoading = this._ipadsLoading.asReadonly();

  ngOnInit(): void {
    this.loadAllMacBooks();
    this.loadAllIPads();

    this.route.queryParams.subscribe((params) => {
      const type = params['type']?.toLowerCase();
      this.activeTab = type === ModelTab.IPad ? ModelTab.IPad : ModelTab.MacBook;
    });
  }

  // 3. Method to trigger fetch
  loadAllMacBooks() {
    this._macbooksLoading.set(true);

    // Subscribe to the generated RxJS Observable
    this.api.getAllMacBooks().subscribe((data) => {
      this._macbooks.set(data);
      this._macbooksLoading.set(false);
    });
  }

  // 3. Method to trigger fetch
  loadAllIPads() {
    this._ipadsLoading.set(true);

    // Subscribe to the generated RxJS Observable
    this.api.getAllIPads().subscribe((data) => {
      this._ipads.set(data);
      this._ipadsLoading.set(false);
    });
  }

  createDate(year: number, monthIndex: number): Date {
    return new Date(year, monthIndex);
  }

  selectTab(tab: ModelTab): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: tab },
      queryParamsHandling: 'merge',
    });
  }
}