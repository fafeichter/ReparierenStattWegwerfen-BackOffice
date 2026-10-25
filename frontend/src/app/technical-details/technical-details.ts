import { Component, inject, LOCALE_ID, OnInit, signal, VERSION } from '@angular/core';
import { buildInfo } from '../../environments/build.info';
import { DatePipe, JsonPipe } from '@angular/common';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { AppInfoControllerService, AppInfoDto } from '@api/application';
import { environment } from '../../environments/environment';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { version as clarityUiVersion } from '@clr/ui/package.json';
import { version as clarityAngularVersion } from '@clr/angular/package.json';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-technical-details',
  imports: [DatePipe, ClrCardModule, ClrIcon, JsonPipe],
  templateUrl: './technical-details.html',
  styleUrl: './technical-details.css',
})
export class TechnicalDetails implements OnInit {
  buildInfo = buildInfo;
  readonly currentLocale = inject(LOCALE_ID);
  readonly angularVersion = VERSION.full;
  backendAppInfo = signal<AppInfoDto | undefined>(undefined);
  clarityUiVersion = clarityUiVersion;
  clarityAngularVersion = clarityAngularVersion;
  protected readonly frontendEnvironment = environment;
  private readonly oidcSecurityService = inject(OidcSecurityService);
  readonly authenticated = this.oidcSecurityService.authenticated;
  readonly accessToken = toSignal(this.oidcSecurityService.getAccessToken(), {
    initialValue: null,
  });
  readonly refreshToken = toSignal(this.oidcSecurityService.getRefreshToken(), {
    initialValue: null,
  });
  readonly accessTokenPayload = toSignal(this.oidcSecurityService.getPayloadFromAccessToken(), {
    initialValue: null,
  });
  private readonly api = inject(AppInfoControllerService);

  ngOnInit(): void {
    this.api.getAppInfo().subscribe((backendAppInfo) => {
      this.backendAppInfo.set(backendAppInfo);
    });
  }
}
