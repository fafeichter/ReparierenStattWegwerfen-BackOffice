import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClrFileInputModule, ClrVerticalNavModule } from '@clr/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { Activity } from './activity/activity';
import { Contact } from './contact/contact';
import { Devices } from './devices/devices';
import { TitleFn } from '../../../layout/route-title.service';
import { map } from 'rxjs';
import { BusinessPartnerControllerService } from '@api/businesspartner';
import { NavItem, Scrollspy } from '../../../clarity-addons/components/scrollspy/scrollspy';
import { PageSidebar } from '../../../clarity-addons/components/page-sidebar/page-sidebar';

export const businessPartnerTitle: TitleFn = (route) => {
  const businessPartnerId = route.paramMap.get('businessPartnerId')!;
  const api = inject(BusinessPartnerControllerService);

  return api
    .getBusinessPartnerDetails(Number(businessPartnerId!))
    .pipe(map((businessPartner) => `${businessPartner.name}`));
};

@Component({
  selector: 'app-businesspartner-detail',
  imports: [
    ClrFileInputModule,
    ReactiveFormsModule,
    Activity,
    Contact,
    Devices,
    Activity,
    ClrVerticalNavModule,
    Scrollspy,
    PageSidebar,
  ],
  templateUrl: './businesspartner-detail.html',
  styleUrl: './businesspartner-detail.css',
})
export class BusinesspartnerDetail {
  navItems: NavItem[] = [
    { id: 'contact', label: 'Contact', icon: 'user' },
    { id: 'devices', label: 'Devices', icon: 'computer' },
    { id: 'activity', label: 'Activity', icon: 'timeline' },
  ];
  private route = inject(ActivatedRoute);
  readonly businessPartnerId = signal<number>(
    Number(this.route.snapshot.paramMap.get('businessPartnerId')),
  );
  private rawDeviceId = this.route.snapshot.queryParamMap.get('device-id');
  deviceId = signal<number | undefined>(this.rawDeviceId ? Number(this.rawDeviceId) : undefined);
  private router = inject(Router);

  goBack() {
    this.router.navigate(['/devices', this.deviceId()]);
  }
}
