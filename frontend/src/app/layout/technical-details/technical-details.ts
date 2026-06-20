import {Component, inject} from '@angular/core';
import {buildInfo} from "../../../environments/build.info";
import {DatePipe, JsonPipe} from '@angular/common';
import {toSignal} from '@angular/core/rxjs-interop';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {map} from 'rxjs';

@Component({
  selector: 'app-technical-details',
  imports: [
    DatePipe,
    JsonPipe
  ],
  templateUrl: './technical-details.html',
  styleUrl: './technical-details.css',
})
export class TechnicalDetails {

  buildInfo = buildInfo;
  private readonly oidcSecurityService = inject(OidcSecurityService);
  readonly userData = toSignal(
    this.oidcSecurityService.userData$.pipe(map(({userData}) => userData))
  );
}