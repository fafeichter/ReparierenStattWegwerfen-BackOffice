import {ChangeDetectionStrategy, Component, computed, inject, OnInit, signal} from '@angular/core';
import {ModelControllerService, ModelDetailDto, SiliconDto, SizeDto} from '@api/model';
import {ActivatedRoute} from '@angular/router';

interface SizeAvailability {
  id: number;
  label: string;
  availableOn: string[];  // collapsed to short names where possible
  onAllChips: boolean;
}

@Component({
  selector: 'app-model-detail',
  imports: [],
  templateUrl: './model-detail.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './model-detail.css',
})
export class ModelDetail implements OnInit {

  private api = inject(ModelControllerService);
  private route = inject(ActivatedRoute);

  protected readonly model = signal<ModelDetailDto | undefined>(undefined);

  /** Colors sorted by name. */
  protected readonly colors = computed(() =>
    [...(this.model()?.colors ?? [])].sort((a, b) => a.name.localeCompare(b.name)));

  /** Silicons sorted ascending by total CPU cores, then GPU cores. */
  protected readonly silicons = computed(() =>
    [...(this.model()?.siliconOptions ?? [])].sort((a, b) =>
      this.cpuCores(a) - this.cpuCores(b)
      || (a.numberGpuCores ?? 0) - (b.numberGpuCores ?? 0)));

  /** Silicons grouped by short name; group order follows the sorted silicons. */
  protected readonly siliconGroups = computed(() => {
    const groups = new Map<string, SiliconDto[]>();
    for (const s of this.silicons()) {
      const list = groups.get(s.nameShort) ?? [];
      list.push(s);
      groups.set(s.nameShort, list);
    }
    return [...groups.entries()].map(([shortName, chips]) => ({shortName, chips}));
  });

  /** Features sorted by category, then name. */
  protected readonly features = computed(() =>
    [...(this.model()?.features ?? [])].sort((a, b) =>
      a.category.localeCompare(b.category) || a.name.localeCompare(b.name)));

  protected readonly memoryOptions = computed(() =>
    this.buildAvailability(s => s.memoryOptions));

  protected readonly storageOptions = computed(() =>
    this.buildAvailability(s => s.storageOptions));

  ngOnInit(): void {
    const modelId = Number(this.route.snapshot.paramMap.get('businessPartnerId'));
    this.api.getModelDetails(modelId).subscribe(data => this.model.set(data));
  }

  private buildAvailability(pick: (s: SiliconDto) => SizeDto[] | undefined): SizeAvailability[] {
    const silicons = this.silicons();
    const totalChips = silicons.length;

    const byId = new Map<number, { size: SizeDto; siliconIds: Set<number> }>();
    for (const silicon of silicons) {
      for (const size of pick(silicon) ?? []) {
        let entry = byId.get(size.id);
        if (!entry) {
          entry = {size, siliconIds: new Set<number>()};
          byId.set(size.id, entry);
        }
        entry.siliconIds.add(silicon.id);
      }
    }

    return [...byId.values()]
      .sort((a, b) => this.toGb(a.size) - this.toGb(b.size))
      .map(e => ({
        id: e.size.id,
        label: `${e.size.size} ${e.size.unit}`,
        onAllChips: e.siliconIds.size === totalChips,
        availableOn: this.describeChips(e.siliconIds, silicons),
      }));
  }

  /** Collapses to the short name when every chip sharing that short name offers the size. */
  private describeChips(availableIds: Set<number>, silicons: SiliconDto[]): string[] {
    const byShort = new Map<string, SiliconDto[]>();
    for (const s of silicons) {
      const group = byShort.get(s.nameShort) ?? [];
      group.push(s);
      byShort.set(s.nameShort, group);
    }

    const tokens: string[] = [];
    for (const [shortName, group] of byShort) {
      const available = group.filter(s => availableIds.has(s.id));
      if (available.length === 0) {
        continue;
      }
      if (available.length === group.length) {
        tokens.push(shortName);
      } else {
        tokens.push(...available.map(s => s.name));
      }
    }
    return tokens;
  }

  /** Total CPU cores: efficiency + performance + super. */
  private cpuCores(s: SiliconDto): number {
    return (s.numberCpuEfficiencyCores ?? 0)
      + (s.numberCpuPerformanceCores ?? 0)
      + (s.numberCpuSuperCores ?? 0);
  }

  /** Normalizes a size to GB for sorting, regardless of unit casing/whitespace. */
  private toGb(s: SizeDto): number {
    const size = Number(s.size ?? 0);
    switch ((s.unit ?? '').trim().toUpperCase()) {
      case 'TB': return size * 1024;
      case 'MB': return size / 1024;
      case 'KB': return size / (1024 * 1024);
      case 'B':  return size / (1024 * 1024 * 1024);
      case 'GB':
      default:   return size;
    }
  }
}
