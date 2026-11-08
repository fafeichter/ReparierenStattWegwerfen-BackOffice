import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ClrCardModule, ClrVerticalNavModule } from '@clr/angular';

export interface NavItem {
  id: string;
  label: string;
  icon: string;
}

@Component({
  imports: [RouterLink, ClrVerticalNavModule, ClrCardModule],
  selector: 'app-scrollspy',
  styleUrl: './scrollspy.css',
  templateUrl: './scrollspy.html',
})
export class Scrollspy {
  items = input.required<NavItem[]>();

  scrollToSection(sectionId: string): void {
    if (typeof document === 'undefined') return;
    document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
  }
}
