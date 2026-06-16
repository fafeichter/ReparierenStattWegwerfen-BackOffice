import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {ClrDropdownModule, ClrIcon, ClrIconModule, ClrIfOpen} from '@clr/angular';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {toSignal} from '@angular/core/rxjs-interop';
import {map} from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [ClrDropdownModule, ClrIcon, ClrIconModule, ClrIfOpen, RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './header.css',
})
export class Header {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  readonly firstName = toSignal(
    this.oidcSecurityService.userData$.pipe(
      map(({userData}) => userData?.given_name)
    )
  );
  private readonly router = inject(Router);

  refreshPage() {
    const currentUrl = this.router.url;

    // Navigate to the same URL but skip updating the browser history location
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate([currentUrl]);
    });
  }

  logout() {
    this.oidcSecurityService.logoff().subscribe((result) => console.log(result));
  }
}
