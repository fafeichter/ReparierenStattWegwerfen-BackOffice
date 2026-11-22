import { Component, input } from '@angular/core';
import {
  ClrCardModule,
  ClrCheckboxModule,
  ClrCommonFormsModule,
  ClrIcon,
  ClrModalModule,
  ClrTextareaModule,
} from '@clr/angular';
import { ReactiveFormsModule } from '@angular/forms';
import { NavItem } from '../../../../clarity-addons/scrollspy/scrollspy';
import { Invoice } from './invoice/invoice';
import { Accessories } from './accessories/accessories';
import { Sale } from './sale/sale';

@Component({
  selector: 'app-selling',
  imports: [
    ClrIcon,
    ClrCommonFormsModule,
    ClrModalModule,
    ClrTextareaModule,
    ReactiveFormsModule,
    ClrCheckboxModule,
    ClrCardModule,
    Invoice,
    Accessories,
    Sale,
  ],
  templateUrl: './selling.html',
  styleUrl: './selling.css',
})
export class Selling {
  deviceId = input.required<number>();
  navItem = input.required<NavItem>();
}
