import { Component, inject } from '@angular/core';
import { ClrTabsModule } from '@clr/angular';
import { ActivatedRoute, Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';
import { MacbookList } from '../macbook-list/macbook-list';
import { IpadList } from '../ipad-list/ipad-list';

export enum ModelTab {
  MacBook = 'macbook',
  IPad = 'ipad',
}

@Component({
  selector: 'app-models',
  imports: [ClrTabsModule, MacbookList, IpadList],
  templateUrl: './models.html',
  styleUrl: './models.css',
})
export class Models {
  protected readonly ModelTab = ModelTab;

  private route = inject(ActivatedRoute);
  activeTab = toSignal(
    this.route.queryParams.pipe(map((params) => params['type']?.toLowerCase())),
    { initialValue: ModelTab.MacBook },
  );
  private router = inject(Router);

  selectTab(tab: ModelTab): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: tab },
      queryParamsHandling: 'merge',
    });
  }
}
