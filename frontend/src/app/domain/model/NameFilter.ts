import { ClrDatagridStringFilterInterface } from '@clr/angular';
import { ModelDto } from '@api/model';

export class NameFilter implements ClrDatagridStringFilterInterface<ModelDto> {
  accepts(item: ModelDto, search: string): boolean {
    return item.name.toLowerCase().includes(search.toLowerCase());
  }
}
