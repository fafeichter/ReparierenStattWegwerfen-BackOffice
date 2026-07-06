import { ApplicationConfig, LOCALE_ID, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';
import { authConfig } from './auth/auth.config';
import {
  AbstractSecurityStorage,
  authInterceptor,
  DefaultLocalStorageService,
  provideAuth,
  withAppInitializerAuthCheck,
} from 'angular-auth-oidc-client';
import { provideHttpClient, withInterceptors, withXhr } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { Configuration as ModelConfig } from '@api/model';
import { Configuration as BusinesspartnerConfig } from '@api/businesspartner';
import { Configuration as DeviceConfig } from '@api/device';
import { environment } from '../environments/environment';

import localeAt from '@angular/common/locales/de-AT';
import { registerLocaleData } from '@angular/common';

registerLocaleData(localeAt);

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withXhr(), withInterceptors([authInterceptor()])),
    provideAuth(authConfig, withAppInitializerAuthCheck()),
    { provide: AbstractSecurityStorage, useClass: DefaultLocalStorageService },
    provideAnimations(),
    { provide: ModelConfig, useValue: new ModelConfig({ basePath: environment.backendUrl }) },
    {
      provide: BusinesspartnerConfig,
      useValue: new BusinesspartnerConfig({ basePath: environment.backendUrl }),
    },
    { provide: DeviceConfig, useValue: new DeviceConfig({ basePath: environment.backendUrl }) },
    { provide: LOCALE_ID, useValue: 'de-AT' },
  ],
};