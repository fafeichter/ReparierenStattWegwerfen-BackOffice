import {ChangeDetectionStrategy, Component, signal} from '@angular/core';
import {ClrBreadcrumbsModule, ClrDropdownModule, ClrIconModule, ClrVerticalNavModule,} from '@clr/angular';
import {Layout} from './layout/layout';

@Component({
  selector: 'app-root',
  imports: [ClrBreadcrumbsModule, ClrDropdownModule, ClrIconModule, ClrVerticalNavModule, Layout],
  templateUrl: './app.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('reparierenstattwegwerfen-backoffice-frontend');
}
