import { Component, inject, LOCALE_ID, OnInit, signal, VERSION } from '@angular/core';
import { buildInfo } from '../../../environments/build.info';
import { DatePipe, JsonPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AppInfoControllerService, AppInfoDto } from '@api/application';

@Component({
  selector: 'app-technical-details',
  imports: [DatePipe, JsonPipe],
  templateUrl: './technical-details.html',
  styleUrl: './technical-details.css',
})
export class TechnicalDetails implements OnInit {
  buildInfo = buildInfo;
  readonly currentLocale = inject(LOCALE_ID);
  readonly angularVersion = VERSION.full;
  backendAppInfo = signal<AppInfoDto | undefined>(undefined);
  protected readonly environment = environment;
  private readonly oidcSecurityService = inject(OidcSecurityService);
  readonly userData = toSignal(
    this.oidcSecurityService.userData$.pipe(map(({ userData }) => userData)),
  );
  private readonly api = inject(AppInfoControllerService);

  ngOnInit(): void {
    this.api.getAppInfo().subscribe((backendAppInfo) => {
      this.backendAppInfo.set(backendAppInfo);
    });
  }
}
