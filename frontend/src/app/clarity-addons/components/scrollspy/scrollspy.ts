import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ClrVerticalNavModule } from '@clr/angular';

export interface Navtem {
  id: string;
  label: string;
}

@Component({
  imports: [RouterLink, ClrVerticalNavModule],
  selector: 'app-scrollspy',
  styleUrl: './scrollspy.css',
  templateUrl: './scrollspy.html',
})
export class Scrollspy {
  items = input.required<Navtem[]>();

  scrollToSection(sectionId: string): void {
    if (typeof document === 'undefined') return;
    document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
  }
}
