import { Directive, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appSerialNumber]',
})
export class SerialNumber {
  constructor(private control: NgControl) {}

  @HostListener('input', ['$event'])
  onInputChange(event: Event) {
    const input = event.target as HTMLInputElement;

    // Remove non-alphanumeric characters and convert to uppercase
    const sanitized = input.value.replace(/[^a-zA-Z0-9]/g, '').toUpperCase();

    if (input.value !== sanitized) {
      input.value = sanitized;
      // Update the Reactive Form control value if it's being used
      if (this.control && this.control.control) {
        this.control.control.setValue(sanitized, { emitEvent: false });
      }
    }
  }
}
