import {ChangeDetectionStrategy, Component} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs';
import {BreadcrumbItem, ClrBreadcrumbsModule} from '@clr/angular';

@Component({
  selector: 'app-breadcrumbs',
  imports: [ClrBreadcrumbsModule],
  templateUrl: './breadcrumbs.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './breadcrumbs.css',
})
export class Breadcrumbs {
  items: BreadcrumbItem[] = [];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
      this.items = this.buildBreadcrumbs();
    });
  }

  private buildBreadcrumbs(): BreadcrumbItem[] {
    const breadcrumbs: BreadcrumbItem[] = [];

    let route = this.activatedRoute.root;
    let url = '';

    while (route.firstChild) {
      route = route.firstChild;

      const routeUrl = route.snapshot.url.map((segment) => segment.path).join('/');

      if (routeUrl) {
        url += `/${routeUrl}`;
      }

      const label = route.snapshot.data['breadcrumb'];

      if (label) {
        breadcrumbs.push({
          label,
          routerLink: url || '/',
        });
      }
    }

    return breadcrumbs;
  }
}