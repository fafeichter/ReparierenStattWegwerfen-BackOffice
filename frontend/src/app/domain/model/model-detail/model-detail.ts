import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  input,
  numberAttribute,
} from '@angular/core';
import { ModelControllerService, SiliconDto, SizeDto } from '@api/model';
import { ClrCardModule, ClrIcon, ClrLabel } from '@clr/angular';
import { DatePipe } from '@angular/common';
import { TitleFn } from '../../../layout/route-title.service';
import { map } from 'rxjs';
import { NavItem, Scrollspy } from '../../../clarity-addons/scrollspy/scrollspy';
import { PageSidebar } from '../../../clarity-addons/page-sidebar/page-sidebar';

export const modelTitle: TitleFn = (route) =>
  inject(ModelControllerService)
    .getModelDetails(Number(route.paramMap.get('modelId')))
    .pipe(map((model) => `${model.modelNumber} | ${model.name}`));

@Component({
  selector: 'app-model-detail',
  imports: [ClrLabel, ClrIcon, DatePipe, Scrollspy, ClrCardModule, PageSidebar],
  templateUrl: './model-detail.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './model-detail.css',
})
export class ModelDetail {
  navItems: NavItem[] = [
    { id: 'model', label: 'Model', icon: 'details' },
    { id: 'hardware', label: 'Hardware', icon: 'nvme' },
    { id: 'features', label: 'Features', icon: 'star' },
  ];
  readonly modelId = input.required({ transform: numberAttribute });
  protected readonly memoryOptions = computed(() =>
    this.availability((silicon) => silicon.memoryOptions),
  );
  protected readonly storageOptions = computed(() =>
    this.availability((silicon) => silicon.storageOptions),
  );
  private readonly modelResource = inject(ModelControllerService).getModelDetailsResource(() => ({
    modelId: this.modelId(),
  }));
  protected readonly model = computed(() =>
    this.modelResource.hasValue() ? this.modelResource.value() : undefined,
  );
  private readonly silicons = computed(() => this.model()?.siliconOptions ?? []);
  /** Grouped by variant, in the order the silicons arrive. */
  protected readonly siliconGroups = computed(() => {
    const silicons = this.silicons();
    const variants = new Map(silicons.map((s) => [s.variant.id, s.variant]));
    return [...variants.values()].map((variant) => ({
      variant,
      chips: silicons.filter((s) => s.variant.id === variant.id),
    }));
  });

  /**
   * Every size offered by at least one chip. `availableOn` lists the variant name when all
   * its chips offer the size, otherwise the individual chip names.
   */
  private availability(pick: (silicon: SiliconDto) => SizeDto[]) {
    const sizes = new Map(
      this.silicons()
        .flatMap((silicon) => pick(silicon))
        .map((size) => [size.id, size]),
    );

    return [...sizes.values()]
      .sort((a, b) => toGb(a) - toGb(b))
      .map((size) => {
        const offers = (s: SiliconDto) => pick(s).some((o) => o.id === size.id);
        return {
          id: size.id,
          label: `${size.size} ${size.unit}`,
          onAllChips: this.silicons().every(offers),
          availableOn: this.siliconGroups().flatMap(({ variant, chips }) =>
            chips.every(offers) ? [variant.name] : chips.filter(offers).map((s) => s.name),
          ),
        };
      });
  }
}

/** The database only stores GB and TB. */
function toGb(size: SizeDto): number {
  return size.unit === 'TB' ? size.size * 1024 : size.size;
}
