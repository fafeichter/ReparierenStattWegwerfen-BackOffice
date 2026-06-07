import {ApplicationConfig, provideBrowserGlobalErrorListeners} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {authConfig} from './auth/auth.config';
import {
  AbstractSecurityStorage,
  authInterceptor,
  DefaultLocalStorageService,
  provideAuth,
  withAppInitializerAuthCheck,
} from 'angular-auth-oidc-client';
import {provideHttpClient, withInterceptors, withXhr} from '@angular/common/http';
import {provideAnimations} from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withXhr(), withInterceptors([authInterceptor()])),
    provideAuth(authConfig, withAppInitializerAuthCheck()),
    {provide: AbstractSecurityStorage, useClass: DefaultLocalStorageService},
    provideAnimations(),
  ],
};
