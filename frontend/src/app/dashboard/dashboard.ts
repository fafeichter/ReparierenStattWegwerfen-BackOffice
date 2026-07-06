import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ClarityModule, ClrVerticalNavModule } from '@clr/angular';
import { AddDevice } from './add-device/add-device';

@Component({
  selector: 'app-dashboard',
  imports: [RouterModule, ClarityModule, ClrVerticalNavModule, AddDevice],
  templateUrl: './dashboard.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './dashboard.css',
})
export class Dashboard {}
