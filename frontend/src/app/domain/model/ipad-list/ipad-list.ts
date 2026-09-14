import { Component, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ClrDatagridModule, ClrDatagridSortOrder } from '@clr/angular';
import { ModelControllerService, ModelDto } from '@api/model';
import { RouterLink } from '@angular/router';
import { ReleaseDateComparator } from '../ReleaseDateComparator';
import { NameFilter } from '../NameFilter';
import { ReleaseDateFilter } from '../ReleaseDateFilter';

@Component({
  imports: [ClrDatagridModule, RouterLink, DatePipe],
  providers: [DatePipe],
  selector: 'app-ipad-list',
  styleUrl: './ipad-list.css',
  templateUrl: './ipad-list.html',
})
export class IpadList implements OnInit {
  public releaseDateComparator = new ReleaseDateComparator();
  public nameFilter = new NameFilter();
  public releaseDateFilter = new ReleaseDateFilter();
  protected readonly ClrDatagridSortOrder = ClrDatagridSortOrder;

  private api = inject(ModelControllerService);

  private _ipads = signal<ModelDto[]>([]);
  readonly ipads = this._ipads.asReadonly();

  private _ipadsLoading = signal<boolean>(false);
  readonly ipadsLoading = this._ipadsLoading.asReadonly();

  ngOnInit(): void {
    this.loadAllIPads();
  }

  loadAllIPads(): void {
    this._ipadsLoading.set(true);
    this.api.getAllIPads().subscribe((data) => {
      this._ipads.set(data);
      this._ipadsLoading.set(false);
    });
  }

  createDate(year: number, monthIndex: number): Date {
    return new Date(year, monthIndex);
  }
}
