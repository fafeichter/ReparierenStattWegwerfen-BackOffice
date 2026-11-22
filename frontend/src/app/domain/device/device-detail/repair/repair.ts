import { Component, input, signal } from '@angular/core';
import {
  ClrCardModule,
  ClrCommonFormsModule,
  ClrDatagridModule,
  ClrIcon,
  ClrModalModule,
  ClrNumberInputModule,
  ClrSelectModule,
  ClrTextareaModule,
} from '@clr/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';
import { Defects } from './defects/defects';
import { SpareParts } from './spare-parts/spare-parts';

@Component({
  selector: 'app-repair',
  imports: [
    ClrDatagridModule,
    ClrIcon,
    ClrCommonFormsModule,
    ClrModalModule,
    ClrTextareaModule,
    ReactiveFormsModule,
    ClrSelectModule,
    ClrNumberInputModule,
    ClrCardModule,
    Defects,
    SpareParts,
  ],
  templateUrl: './repair.html',
  styleUrl: './repair.css',
})
export class Repair {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();

  modalOpen = signal<boolean>(false);
}
