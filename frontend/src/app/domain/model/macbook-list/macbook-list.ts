import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ClrDatagridModule, ClrDatagridSortOrder } from '@clr/angular';
import { ModelControllerService, ModelDto } from '@api/model';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ReleaseDateComparator } from '../ReleaseDateComparator';
import { NameFilter } from '../NameFilter';
import { ReleaseDateFilter } from '../ReleaseDateFilter';

@Component({
  imports: [ClrDatagridModule, RouterLink, DatePipe],
  providers: [DatePipe],
  selector: 'app-macbook-list',
  styleUrl: './macbook-list.css',
  templateUrl: './macbook-list.html',
})
export class MacbookList implements OnInit {
  public releaseDateComparator = new ReleaseDateComparator();
  public nameFilter = new NameFilter();
  public releaseDateFilter = new ReleaseDateFilter();
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;

  private api = inject(ModelControllerService);

  private _macbooks = signal<ModelDto[]>([]);
  readonly macbooks = this._macbooks.asReadonly();

  private _macbooksLoading = signal<boolean>(false);
  readonly macbooksLoading = this._macbooksLoading.asReadonly();

  ngOnInit(): void {
    this.loadAllMacBooks();
  }

  loadAllMacBooks(): void {
    this._macbooksLoading.set(true);
    this.api.getAllMacBooks().subscribe((data) => {
      this._macbooks.set(data);
      this._macbooksLoading.set(false);
    });
  }

  createDate(year: number, monthIndex: number): Date {
    return new Date(year, monthIndex);
  }
}
