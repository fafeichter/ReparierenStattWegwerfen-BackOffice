import { Component, computed, DestroyRef, inject } from '@angular/core';
import { ClrAlertModule } from '@clr/angular';
import { httpResource } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface ActuatorHealth {
  status: 'UP' | 'DOWN' | 'OUT_OF_SERVICE' | 'UNKNOWN';
}

const HEALTH_POLL_INTERVAL_MS = 10_000;

@Component({
  imports: [ClrAlertModule],
  selector: 'app-backend-down-alert',
  styleUrl: './backend-down-alert.css',
  templateUrl: './backend-down-alert.html',
})
export class BackendDownAlert {
  private readonly health = httpResource<ActuatorHealth>(
    () => `${environment.backendUrl}/actuator/health`,
  );
  readonly isBackendUp = computed(() => {
    if (this.health.error()) return false;
    if (!this.health.hasValue()) return true;
    return this.health.value().status === 'UP';
  });

  constructor() {
    const id = setInterval(() => this.health.reload(), HEALTH_POLL_INTERVAL_MS);
    inject(DestroyRef).onDestroy(() => clearInterval(id));
  }
}
