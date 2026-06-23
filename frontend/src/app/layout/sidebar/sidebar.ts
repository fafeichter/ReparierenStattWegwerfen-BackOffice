import {AfterViewInit, ChangeDetectionStrategy, Component, inject, ViewChild} from '@angular/core';
import {ClrIcon, ClrVerticalNav, ClrVerticalNavModule} from '@clr/angular';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {toSignal} from '@angular/core/rxjs-interop';
import {map} from 'rxjs';

@Component({
  selector: 'app-sidebar',
  imports: [ClrIcon, ClrVerticalNavModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './sidebar.css',
})
export class Sidebar implements AfterViewInit {

  @ViewChild(ClrVerticalNav) verticalNav!: ClrVerticalNav;

  private breakpointObserver = inject(BreakpointObserver);

  readonly isMobile = toSignal(
    this.breakpointObserver
      .observe([Breakpoints.Handset])
      .pipe(map(result => result.matches)),
    {initialValue: true}
  );

  ngAfterViewInit(): void {
    // Wrap in a microtask to prevent ExpressionChangedAfterItHasBeenCheckedError
    queueMicrotask(() => {
      this.verticalNav.collapsed = this.isMobile();
    });
  }
}