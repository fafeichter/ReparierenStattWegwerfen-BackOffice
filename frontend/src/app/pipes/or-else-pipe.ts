import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orElse',
})
export class OrElsePipe implements PipeTransform {
  transform(value: unknown, ..._args: unknown[]): unknown {
    // Checks for null, undefined, or empty/whitespace-only strings
    return value && value.toString().trim() !== '' ? value : '-';
  }
}
