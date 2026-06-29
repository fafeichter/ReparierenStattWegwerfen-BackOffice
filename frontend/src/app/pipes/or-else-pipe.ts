import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'orElse',
  standalone: true
})
export class OrElsePipe implements PipeTransform {
  transform(value: unknown, ...args: unknown[]): unknown {
    // Checks for null, undefined, or empty/whitespace-only strings
    return value && value.toString().trim() !== '' ? value : '-';
  }
}