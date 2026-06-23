import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {ClrBreadcrumbsModule, ClrDropdownModule, ClrSpinnerModule, ClrVerticalNavModule,} from '@clr/angular';
import {Layout} from './layout/layout';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {toSignal} from '@angular/core/rxjs-interop';
import {map} from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [ClrBreadcrumbsModule, ClrDropdownModule, ClrVerticalNavModule, Layout, ClrSpinnerModule],
  templateUrl: './app.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './app.css',
})
export class App {

  private readonly oidc = inject(OidcSecurityService);

  readonly isAuthenticated = toSignal(
    this.oidc.isAuthenticated$.pipe(map(({isAuthenticated}) => isAuthenticated)),
    {initialValue: false}
  );
}