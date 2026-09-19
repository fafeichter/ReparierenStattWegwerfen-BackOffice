import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClrFileInputModule, ClrVerticalNavModule } from '@clr/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { Activity } from './activity/activity';
import { Contact } from './contact/contact';
import { Devices } from './devices/devices';

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
    RouterLink,
  ],
  templateUrl: './businesspartner-detail.html',
  styleUrl: './businesspartner-detail.css',
})
export class BusinesspartnerDetail {
  navItems = [
    { id: 'contact', label: 'Contact' },
    { id: 'devices', label: 'Devices' },
    { id: 'activity', label: 'Activity' },
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

  scrollToSection(sectionId: string): void {
    document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
  }
}
