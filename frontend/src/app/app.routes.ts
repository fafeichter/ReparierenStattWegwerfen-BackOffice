import {Routes} from '@angular/router';
import {Dashboard} from './dashboard/dashboard';
import {AutoLoginPartialRoutesGuard} from 'angular-auth-oidc-client';
import {Models} from './domain/model/models';
import {BusinessPartners} from './domain/businesspartner/business-partners.component';
import {ModelDetail} from './domain/model/model-detail/model-detail';
import {Statistics} from './domain/statistics/statistics';
import {Devices} from './domain/device/devices';
import {TechnicalDetails} from './layout/technical-details/technical-details';
import {BusinesspartnerDetail} from './domain/businesspartner/businesspartner-detail/businesspartner-detail';
import {DeviceDetail} from './domain/device/device-detail/device-detail';

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
        data: {breadcrumb: 'Devices'},
        children: [
          {
            path: '',
            component: Devices,
          },
          {
            path: ':deviceId',
            component: DeviceDetail,
            data: {breadcrumb: 'Details'},
          },
        ],
      },
      {
        path: 'businesspartners',
        data: {
          breadcrumb: 'Business Partners',
        },
        children: [
          {
            path: '',
            component: BusinessPartners,
          },
          {
            path: ':businessPartnerId',
            component: BusinesspartnerDetail,
            data: {breadcrumb: 'Details'},
          },
        ],
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
            path: ':modelId',
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
      {
        path: 'technical-details',
        component: TechnicalDetails,
        data: {
          breadcrumb: 'Technical details',
        },
      },
    ],
  },
];