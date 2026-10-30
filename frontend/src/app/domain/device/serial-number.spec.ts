import { NgControl } from '@angular/forms';
import { SerialNumber } from './serial-number';
import { beforeEach, expect } from 'vitest';

describe('SerialNumber Directive', () => {
  let directive: SerialNumber;
  let mockNgControl: { control: { setValue: ReturnType<typeof vi.fn> } };
  let inputElement: HTMLInputElement;

  beforeEach(() => {
    // Create a mock NgControl object with a Vitest spy function
    mockNgControl = {
      control: {
        setValue: vi.fn(),
      },
    };

    directive = new SerialNumber(mockNgControl as unknown as NgControl);
    inputElement = document.createElement('input');
  });

  it('should create an instance', () => {
    expect(directive).toBeTruthy();
  });

  it('should sanitize input to uppercase and alphanumeric only', () => {
    inputElement.value = 'abc-123 xyz!';

    // Simulate the input event
    const event = new InputEvent('input', { bubbles: true });
    Object.defineProperty(event, 'target', { value: inputElement, enumerable: true });

    directive.onInputChange(event);

    // Expect the input value to be cleaned and uppercased
    expect(inputElement.value).toBe('ABC123XYZ');

    // Expect the reactive form control value to be updated using Vitest's 'toHaveBeenCalledWith'
    expect(mockNgControl.control.setValue).toHaveBeenCalledWith('ABC123XYZ', { emitEvent: false });
  });

  it('should not update value or trigger control change if already clean and uppercase', () => {
    inputElement.value = 'ABC123XYZ';
    const event = new InputEvent('input', { bubbles: true });
    Object.defineProperty(event, 'target', { value: inputElement, enumerable: true });

    directive.onInputChange(event);

    expect(inputElement.value).toBe('ABC123XYZ');
    expect(mockNgControl.control.setValue).not.toHaveBeenCalled();
  });
});
