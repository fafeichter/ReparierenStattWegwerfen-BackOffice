import { Component, computed, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ClrDatagridModule, ClrDatagridSortOrder } from '@clr/angular';
import { ModelControllerService, ModelDto } from '@api/model';
import { RouterLink } from '@angular/router';
import { ReleaseDateComparator } from '../models-list/ReleaseDateComparator';
import { NameFilter } from '../models-list/NameFilter';
import { ReleaseDateFilter } from '../models-list/ReleaseDateFilter';

@Component({
  imports: [ClrDatagridModule, RouterLink, DatePipe],
  providers: [DatePipe],
  selector: 'app-ipad-list',
  styleUrl: './ipad-list.css',
  templateUrl: './ipad-list.html',
})
export class IpadList {
  public releaseDateComparator = new ReleaseDateComparator();
  public nameFilter = new NameFilter();
  public releaseDateFilter = new ReleaseDateFilter();
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;

  private readonly ipadsResource = inject(ModelControllerService).getAllIPadsResource();
  readonly ipads = computed<ModelDto[]>(() =>
    this.ipadsResource.hasValue() ? this.ipadsResource.value() : [],
  );
  createDate(year: number, monthIndex: number): Date {
    return new Date(year, monthIndex);
  }
}
