import {Routes} from '@angular/router';
import {Dashboard} from './dashboard/dashboard';
import {AutoLoginPartialRoutesGuard} from 'angular-auth-oidc-client';
import {Models} from './models/models';
import {Statistics} from './statistics/statistics';
import {Devices} from './devices/devices';
import {BusinessPartners} from './businesspartners/business-partners.component';
import {ModelDetail} from './models/model-detail/model-detail';

export const routes: Routes = [
  {
    path: '',
    canActivateChild: [AutoLoginPartialRoutesGuard],
    data: {breadcrumb: 'Dashboard'},
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        component: Dashboard,
        data: {
          breadcrumb: 'Dashboard',
        },
      },
      {
        path: 'devices',
        component: Devices,
        data: {
          breadcrumb: 'Devices',
        },
      },
      {
        path: 'businesspartners',
        component: BusinessPartners,
        data: {
          breadcrumb: 'Business Partners',
        },
      },
      {
        path: 'models',
        data: {breadcrumb: 'Models'},
        children: [
          {
            path: '',
            component: Models,
          },
          {
            path: ':id',
            component: ModelDetail,
            data: {breadcrumb: 'Details'},
          },
        ],
      },
      {
        path: 'statistics',
        component: Statistics,
        data: {
          breadcrumb: 'Statistics',
        },
      },
    ],
  },
];
