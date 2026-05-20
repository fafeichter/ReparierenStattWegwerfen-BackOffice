import {Routes} from '@angular/router';
import {Dashboard} from './dashboard/dashboard';
import {AutoLoginPartialRoutesGuard} from 'angular-auth-oidc-client';

export const routes: Routes = [
  {
    path: '',
    component: Dashboard,
    canActivate: [AutoLoginPartialRoutesGuard]
  }
];
