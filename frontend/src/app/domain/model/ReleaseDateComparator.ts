import { ClrDatagridComparatorInterface } from '@clr/angular';
import { ModelDto } from '@api/model';

export class ReleaseDateComparator implements ClrDatagridComparatorInterface<ModelDto> {
  compare(a: ModelDto, b: ModelDto): number {
    return (
      +new Date(a.releaseYear, a.releaseMonth - 1) - +new Date(b.releaseYear, b.releaseMonth - 1)
    );
  }
}
