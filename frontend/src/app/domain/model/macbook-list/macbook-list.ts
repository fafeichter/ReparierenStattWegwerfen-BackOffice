import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ClrDatagridModule, ClrDatagridSortOrder } from '@clr/angular';
import { ModelControllerService, ModelDto } from '@api/model';
import { Component, computed, inject } from '@angular/core';
import { ReleaseDateComparator } from '../models-list/ReleaseDateComparator';
import { NameFilter } from '../models-list/NameFilter';
import { ReleaseDateFilter } from '../models-list/ReleaseDateFilter';

@Component({
  imports: [ClrDatagridModule, RouterLink, DatePipe],
  providers: [DatePipe],
  selector: 'app-macbook-list',
  styleUrl: './macbook-list.css',
  templateUrl: './macbook-list.html',
})
export class MacbookList {
  public releaseDateComparator = new ReleaseDateComparator();
  public nameFilter = new NameFilter();
  public releaseDateFilter = new ReleaseDateFilter();
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;

  private readonly macbooksResource = inject(ModelControllerService).getAllMacBooksResource();
  readonly macbooks = computed<ModelDto[]>(() =>
    this.macbooksResource.hasValue() ? this.macbooksResource.value() : [],
  );
  createDate(year: number, monthIndex: number): Date {
    return new Date(year, monthIndex);
  }
}
