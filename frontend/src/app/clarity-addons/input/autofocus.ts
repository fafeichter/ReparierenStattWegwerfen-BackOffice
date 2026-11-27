import { afterNextRender, Directive, ElementRef, inject } from '@angular/core';

@Directive({
  selector: '[appAutofocus]',
})
export class Autofocus {
  constructor() {
    const inputElement = inject<ElementRef<HTMLElement>>(ElementRef);
    afterNextRender(() => inputElement.nativeElement.focus());
  }
}
