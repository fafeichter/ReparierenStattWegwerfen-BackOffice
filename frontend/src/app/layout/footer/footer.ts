import { Component, computed, inject } from '@angular/core';
import { SystemInfoControllerService } from '@api/application';
import { ClrIcon } from '@clr/angular';

@Component({
  imports: [ClrIcon],
  selector: 'app-footer',
  styleUrl: './footer.css',
  templateUrl: './footer.html',
})
export class Footer {
  private readonly prodDbAndDataResource = inject(
    SystemInfoControllerService,
  ).isRunningOnProdDbAndDataResource();

  readonly isRunningOnProdDbAndData = computed(() =>
    this.prodDbAndDataResource.hasValue() ? this.prodDbAndDataResource.value() : undefined,
  );
}
