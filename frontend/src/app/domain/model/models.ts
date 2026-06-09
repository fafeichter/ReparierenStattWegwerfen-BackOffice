import {Component, inject, OnInit, signal} from '@angular/core';
import {ClrDatagridModule, ClrTabsModule} from '@clr/angular';
import {RouterLink} from '@angular/router';
import {ModelControllerService, ModelDto} from '@api/model';

@Component({
  selector: 'app-models',
  imports: [ClrDatagridModule, RouterLink, ClrTabsModule],
  templateUrl: './models.html',
  styleUrl: './models.css',
})
export class Models implements OnInit {

  private api = inject(ModelControllerService);

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
  }

  // 3. Method to trigger fetch
  loadAllMacBooks() {
    this._macbooksLoading.set(true);

    // Subscribe to the generated RxJS Observable
    this.api.getAllMacBooks().subscribe(data => {
      this._macbooks.set(data);
      this._macbooksLoading.set(false);
    })
  }

  // 3. Method to trigger fetch
  loadAllIPads() {
    this._ipadsLoading.set(true);

    // Subscribe to the generated RxJS Observable
    this.api.getAllIPads().subscribe(data => {
        this._ipads.set(data);
        this._ipadsLoading.set(false);
    });
  }
}
