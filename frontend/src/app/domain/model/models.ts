import { Component, inject, OnInit } from '@angular/core';
import { ClrTabsModule } from '@clr/angular';
import { ActivatedRoute, Router } from '@angular/router';
import { MacbookList } from './macbook-list/macbook-list';
import { IpadList } from './ipad-list/ipad-list';

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
export class Models implements OnInit {
  activeTab: ModelTab = ModelTab.MacBook;
  protected readonly ModelTab = ModelTab;

  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const type = params['type']?.toLowerCase();
      this.activeTab = type === ModelTab.IPad ? ModelTab.IPad : ModelTab.MacBook;
    });
  }

  selectTab(tab: ModelTab): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { type: tab },
      queryParamsHandling: 'merge',
    });
  }
}
