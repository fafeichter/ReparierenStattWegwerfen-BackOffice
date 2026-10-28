import { Component, inject, LOCALE_ID, OnInit, signal, VERSION } from '@angular/core';
import { buildInfo } from '../../environments/build.info';
import { DatePipe, JsonPipe } from '@angular/common';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { SystemInfoControllerService, SystemInfoDto } from '@api/application';
import { environment } from '../../environments/environment';
import { ClrCardModule, ClrIcon } from '@clr/angular';
import { version as clarityUiVersion } from '@clr/ui/package.json';
import { version as clarityAngularVersion } from '@clr/angular/package.json';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-system-info',
  imports: [DatePipe, ClrCardModule, ClrIcon, JsonPipe],
  templateUrl: './system-info.html',
  styleUrl: './system-info.css',
})
export class SystemInfo implements OnInit {
  buildInfo = buildInfo;
  readonly currentLocale = inject(LOCALE_ID);
  readonly angularVersion = VERSION.full;
  backendSystemInfo = signal<SystemInfoDto | undefined>(undefined);
  clarityUiVersion = clarityUiVersion;
  clarityAngularVersion = clarityAngularVersion;
  protected readonly frontendProduction = environment.production;
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
  private readonly systemInfoApi = inject(SystemInfoControllerService);

  ngOnInit(): void {
    this.systemInfoApi.getSystemInfo().subscribe((backendSystemInfo) => {
      this.backendSystemInfo.set(backendSystemInfo);
    });
  }
}
