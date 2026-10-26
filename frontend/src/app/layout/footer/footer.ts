import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { AppInfoControllerService } from '@api/application';

@Component({
  imports: [],
  selector: 'app-footer',
  styleUrl: './footer.css',
  templateUrl: './footer.html',
})
export class Footer {
  private readonly applicationApi = inject(AppInfoControllerService);

  readonly isRunningOnProdDbAndData = toSignal(this.applicationApi.isRunningOnProdDbAndData());
}
