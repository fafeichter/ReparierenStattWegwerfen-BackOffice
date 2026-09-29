import { Component, inject, Signal } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { filter, Observable, of, startWith, switchMap } from 'rxjs';
import { RouteTitleService } from '../route-title.service';

@Component({
  selector: 'app-title',
  styleUrl: './title.css',
  templateUrl: './title.html',
  standalone: true,
})
export class Title {
  readonly title: Signal<string>;

  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly routeTitle = inject(RouteTitleService);

  constructor() {
    this.title = toSignal(
      this.router.events.pipe(
        filter((event) => event instanceof NavigationEnd),
        startWith(null),
        switchMap(() => this.buildTitle$()),
      ),
      { initialValue: '' },
    );
  }

  private buildTitle$(): Observable<string> {
    let route = this.activatedRoute.root;
    let title$: Observable<string> | undefined;

    // deepest route with a title wins
    while (route.firstChild) {
      route = route.firstChild;
      title$ = this.routeTitle.resolve(route.snapshot) ?? title$;
    }

    return title$ ?? of('');
  }
}
