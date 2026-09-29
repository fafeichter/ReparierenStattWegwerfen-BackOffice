import { inject, Injectable, Injector, runInInjectionContext } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { catchError, defer, from, Observable, of, shareReplay, startWith } from 'rxjs';

/** Custom title logic. Runs in an injection context, so call inject() at the top level. */
export type TitleFn = (
  route: ActivatedRouteSnapshot,
) => string | Promise<string> | Observable<string>;

/** Static string ('Devices') or custom function. */
export type RouteTitle = string | TitleFn;

@Injectable({ providedIn: 'root' })
export class RouteTitleService {
  private readonly injector = inject(Injector);

  // A new snapshot object is created per navigation, so this dedupes calls between
  // Title and Breadcrumbs (one REST call per route per navigation).
  private readonly cache = new WeakMap<ActivatedRouteSnapshot, Observable<string>>();

  /** Returns the title stream for this route, or undefined if the route defines no title. */
  resolve(snapshot: ActivatedRouteSnapshot): Observable<string> | undefined {
    // routeConfig.data = only what THIS route defines (no inheritance from the parent)
    const source = snapshot.routeConfig?.data?.['title'] as RouteTitle | undefined;
    if (!source) return undefined;

    if (typeof source === 'string') {
      return of(source);
    }

    let cached = this.cache.get(snapshot);
    if (!cached) {
      cached = defer(() => {
        const result = runInInjectionContext(this.injector, () => source(snapshot));
        return typeof result === 'string' ? of(result) : from(result);
      }).pipe(
        catchError(() => of('')),
        startWith(''), // placeholder while loading
        shareReplay({ bufferSize: 1, refCount: false }),
      );
      this.cache.set(snapshot, cached);
    }
    return cached;
  }
}
