import { ClrDatagridStringFilterInterface } from '@clr/angular';
import { ModelDto } from '@api/model';
import { inject } from '@angular/core';
import { DatePipe } from '@angular/common';

export class ReleaseDateFilter implements ClrDatagridStringFilterInterface<ModelDto> {
  private datePipe = inject(DatePipe);

  accepts(item: ModelDto, search: string): boolean {
    return (
      this.datePipe.transform(+new Date(item.releaseYear, item.releaseMonth - 1), 'MMM yyyy') || ''
    )
      .toLowerCase()
      .includes(search.toLowerCase());
  }
}
