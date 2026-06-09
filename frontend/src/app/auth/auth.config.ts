import {PassedInitialConfig} from 'angular-auth-oidc-client';
import {environment} from '../../environments/environment';

export const authConfig: PassedInitialConfig = {
  config: {
    authority: environment.oidcIssuerUrl,
    redirectUrl: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
    clientId: 'reparieren-statt-wegwerfen-backoffice-angular',
    scope: 'openid profile email offline_access',
    responseType: 'code',
    silentRenew: true,
    useRefreshToken: true,
    renewTimeBeforeTokenExpiresInSeconds: 30,
    autoUserInfo: true,
    secureRoutes: [environment.backendUrl + '/api/'],
  },
};
