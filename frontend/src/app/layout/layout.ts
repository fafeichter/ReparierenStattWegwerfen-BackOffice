import { Component } from '@angular/core';
import { ClrDropdownModule, ClrMainContainerModule, ClrVerticalNavModule } from '@clr/angular';
import { RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { Sidebar } from './sidebar/sidebar';
import { Breadcrumbs } from './breadcrumbs/breadcrumbs';
import { Title } from './title/title';
import { Footer } from './footer/footer';
import { Alerts } from './alerts/alerts';

@Component({
  selector: 'app-layout',
  imports: [
    ClrDropdownModule,
    ClrVerticalNavModule,
    RouterOutlet,
    Header,
    Sidebar,
    Breadcrumbs,
    Title,
    ClrMainContainerModule,
    Footer,
    Alerts,
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout {}
