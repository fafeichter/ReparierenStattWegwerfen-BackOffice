import { Component } from '@angular/core';
import { BackendDownAlert } from '../../system-info/backend-down-alert/backend-down-alert';
import { ClrAlertModule } from '@clr/angular';

@Component({
  imports: [BackendDownAlert, ClrAlertModule],
  selector: 'app-alerts',
  styleUrl: './alerts.css',
  templateUrl: './alerts.html',
})
export class Alerts {}
