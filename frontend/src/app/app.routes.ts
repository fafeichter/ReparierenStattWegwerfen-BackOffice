import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { AutoLoginPartialRoutesGuard } from 'angular-auth-oidc-client';
import { ModelDetail, modelTitle } from './domain/model/model-detail/model-detail';
import { TechnicalDetails } from './technical-details/technical-details';
import {
  BusinesspartnerDetail,
  businessPartnerTitle,
} from './domain/businesspartner/businesspartner-detail/businesspartner-detail';
import { DeviceDetail, deviceTitle } from './domain/device/device-detail/device-detail';
import { BusinesspartnerCreate } from './domain/businesspartner/businesspartner-create/businesspartner-create';
import { Devices } from './domain/device/devices-list/devices';
import { BusinessPartners } from './domain/businesspartner/businesspartners-list/business-partners.component';
import { Models } from './domain/model/models-list/models';
import { Accounting } from './domain/accounting/accounting';
import { Spareparts } from './domain/spareparts/spareparts';

export const routes: Routes = [
  {
    path: '',
    canActivateChild: [AutoLoginPartialRoutesGuard],
    data: { title: 'Dashboard' },
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        component: Dashboard,
      },
      {
        path: 'devices',
        data: { title: 'Devices' },
        children: [
          {
            path: '',
            component: Devices,
          },
          {
            path: ':deviceId',
            component: DeviceDetail,
            data: { title: deviceTitle },
          },
        ],
      },
      {
        path: 'businesspartners',
        data: { title: 'Business Partners' },
        children: [
          {
            path: '',
            component: BusinessPartners,
          },
          {
            path: 'create',
            component: BusinesspartnerCreate,
          },
          {
            path: ':businessPartnerId',
            component: BusinesspartnerDetail,
            data: { title: businessPartnerTitle },
          },
        ],
      },
      {
        path: 'models',
        data: { title: 'Models' },
        children: [
          {
            path: '',
            component: Models,
          },
          {
            path: ':modelId',
            component: ModelDetail,
            data: { title: modelTitle },
          },
        ],
      },
      {
        path: 'spareparts',
        component: Spareparts,
        data: { title: 'Spare Parts' },
      },
      {
        path: 'accounting',
        component: Accounting,
        data: { title: 'Accounting' },
      },
      {
        path: 'technical-details',
        component: TechnicalDetails,
        data: { title: 'Technical Details' },
      },
    ],
  },
];
