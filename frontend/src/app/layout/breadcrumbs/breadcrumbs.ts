import { Component, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { combineLatest, filter, map, Observable, of, startWith, switchMap } from 'rxjs';
import { BreadcrumbItem, ClrBreadcrumbsModule } from '@clr/angular';
import { RouteTitleService } from '../route-title.service'; // adjust to where the file lives

@Component({
  selector: 'app-breadcrumbs',
  imports: [ClrBreadcrumbsModule],
  templateUrl: './breadcrumbs.html',
  styleUrl: './breadcrumbs.css',
})
export class Breadcrumbs {
  readonly items = signal<BreadcrumbItem[]>([]);

  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly routeTitle = inject(RouteTitleService);

  constructor() {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        startWith(null), // build once on init, in case NavigationEnd already fired
        // switchMap cancels the previous build if the user navigates again
        switchMap(() => this.buildBreadcrumbs$()),
        takeUntilDestroyed(),
      )
      .subscribe((items) => this.items.set(items));
  }

  private buildBreadcrumbs$(): Observable<BreadcrumbItem[]> {
    const crumbs: Observable<BreadcrumbItem>[] = [];

    let route = this.activatedRoute.root;
    let url = '';

    while (route.firstChild) {
      route = route.firstChild;

      const routeUrl = route.snapshot.url.map((segment) => segment.path).join('/');
      if (routeUrl) {
        url += `/${routeUrl}`;
      }

      const label$ = this.routeTitle.resolve(route.snapshot);
      if (label$) {
        const routerLink = url || '/';
        crumbs.push(label$.pipe(map((label) => ({ label, routerLink }))));
      }
    }

    // combineLatest of an empty array completes without emitting
    return crumbs.length ? combineLatest(crumbs) : of([]);
  }
}
