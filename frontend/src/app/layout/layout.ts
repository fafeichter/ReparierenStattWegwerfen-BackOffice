import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ClrDropdownModule, ClrMainContainerModule, ClrVerticalNavModule } from '@clr/angular';
import { RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { Sidebar } from './sidebar/sidebar';
import { Breadcrumbs } from './breadcrumbs/breadcrumbs';
import { Title } from './title/title';

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
  ],
  templateUrl: './layout.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './layout.css',
  standalone: true,
})
export class Layout {}
