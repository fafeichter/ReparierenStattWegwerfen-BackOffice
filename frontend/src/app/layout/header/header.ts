import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {ClrDropdownModule, ClrIcon, ClrIconModule, ClrIfOpen} from '@clr/angular';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {OidcSecurityService} from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  imports: [ClrDropdownModule, ClrIcon, ClrIconModule, ClrIfOpen, RouterLink, RouterLinkActive],
  templateUrl: './header.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './header.css',
})
export class Header {
  private readonly oidcSecurityService = inject(OidcSecurityService);

  logout() {
    this.oidcSecurityService.logoff().subscribe((result) => console.log(result));
  }
}
