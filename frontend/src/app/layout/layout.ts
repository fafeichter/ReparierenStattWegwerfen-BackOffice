import { Component } from '@angular/core';
import {
  ClrDropdownModule,
  ClrIconModule,
  ClrMainContainerModule,
  ClrVerticalNavModule,
} from '@clr/angular';
import { RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { Sidebar } from './sidebar/sidebar';
import { Breadcrumbs } from './breadcrumbs/breadcrumbs';

@Component({
  selector: 'app-layout',
  imports: [
    ClrDropdownModule,
    ClrIconModule,
    ClrVerticalNavModule,
    RouterOutlet,
    Header,
    Sidebar,
    Breadcrumbs,
    ClrMainContainerModule,
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout {}
