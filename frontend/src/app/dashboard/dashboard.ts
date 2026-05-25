import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ClarityModule, ClrVerticalNavModule } from '@clr/angular';

@Component({
  selector: 'app-dashboard',
  imports: [RouterModule, ClarityModule, ClrVerticalNavModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {}
