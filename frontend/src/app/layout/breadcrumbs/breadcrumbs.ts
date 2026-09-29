import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, ActivatedRouteSnapshot, NavigationEnd, Router } from '@angular/router';
import { filter, startWith } from 'rxjs';
import { BreadcrumbItem, ClrBreadcrumbsModule } from '@clr/angular';

@Component({
  selector: 'app-breadcrumbs',
  imports: [ClrBreadcrumbsModule],
  templateUrl: './breadcrumbs.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './breadcrumbs.css',
})
export class Breadcrumbs {
  items: BreadcrumbItem[] = [];
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  constructor() {
    this.router.events
      .pipe(
        filter((event) => event instanceof NavigationEnd),
        startWith(null), // build once on init, in case NavigationEnd already fired
        takeUntilDestroyed(),
      )
      .subscribe(() => {
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

      // routeConfig.data = only what THIS route defines (no inheritance from the parent)
      const template = route.snapshot.routeConfig?.data?.['breadcrumb'] as string | undefined;

      if (template) {
        breadcrumbs.push({
          label: this.resolveLabel(template, route.snapshot),
          routerLink: url || '/',
        });
      }
    }

    return breadcrumbs;
  }

  /** Replaces ":param" tokens with the value from the route params, e.g. "#:deviceId" -> "#2" */
  private resolveLabel(template: string, snapshot: ActivatedRouteSnapshot): string {
    return template.replace(/:(\w+)/g, (_, param) => snapshot.paramMap.get(param) ?? '');
  }
}
