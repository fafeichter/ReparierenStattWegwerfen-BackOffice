import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { SystemInfoControllerService } from '@api/application';
import { ClrIcon } from '@clr/angular';

@Component({
  imports: [ClrIcon],
  selector: 'app-footer',
  styleUrl: './footer.css',
  templateUrl: './footer.html',
})
export class Footer {
  private readonly systemInfoApi = inject(SystemInfoControllerService);

  readonly isRunningOnProdDbAndData = toSignal(this.systemInfoApi.isRunningOnProdDbAndData());
}
