import { Component } from '@angular/core';
import { ClrDatagridModule } from '@clr/angular';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-models',
  imports: [ClrDatagridModule, RouterLink],
  templateUrl: './models.html',
  styleUrl: './models.css',
})
export class Models {}
