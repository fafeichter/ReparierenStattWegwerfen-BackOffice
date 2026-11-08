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
import { PageSidebar } from '../clarity-addons/page-sidebar/page-sidebar';
import { NavItem, Scrollspy } from '../clarity-addons/scrollspy/scrollspy';
import { OrElsePipe } from '../pipes/or-else-pipe';

@Component({
  selector: 'app-system-info',
  imports: [DatePipe, ClrCardModule, ClrIcon, JsonPipe, PageSidebar, Scrollspy, OrElsePipe],
  templateUrl: './system-info.html',
  styleUrl: './system-info.css',
})
export class SystemInfo implements OnInit {
  navItems: NavItem[] = [
    { id: 'build', label: 'Build', icon: 'code' },
    { id: 'backend', label: 'Backend', icon: 'host' },
    { id: 'frontend', label: 'Frontend', icon: 'application' },
    { id: 'database', label: 'Database', icon: 'storage' },
    { id: 'ai', label: 'AI Models', icon: 'wand' },
    { id: 'userinfo', label: 'User Info', icon: 'id-badge' },
    { id: 'authentication', label: 'Authentication', icon: 'certificate' },
  ];

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
