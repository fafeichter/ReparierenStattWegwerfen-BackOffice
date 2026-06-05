import { Component } from '@angular/core';
import { ClrIcon, ClrIconModule, ClrVerticalNavModule } from '@clr/angular';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [ClrIcon, ClrIconModule, ClrVerticalNavModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {}
