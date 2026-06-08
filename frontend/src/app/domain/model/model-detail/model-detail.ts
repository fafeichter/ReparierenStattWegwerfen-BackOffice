import {ChangeDetectionStrategy, Component} from '@angular/core';
import {ClrIconModule, ClrVerticalNavModule} from '@clr/angular';

@Component({
  selector: 'app-model-detail',
  imports: [ClrIconModule, ClrVerticalNavModule],
  templateUrl: './model-detail.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './model-detail.css',
})
export class ModelDetail {
}
