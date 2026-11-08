import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orElse',
})
export class OrElsePipe implements PipeTransform {
  transform(value: unknown, ..._args: unknown[]): unknown {
    const na = '-';

    // Only null, undefined, or empty/whitespace-only strings fall back
    if (value === null || value === undefined) {
      return na;
    }

    if (typeof value === 'string' && value.trim() === '') {
      return na;
    }

    return value;
  }
}
