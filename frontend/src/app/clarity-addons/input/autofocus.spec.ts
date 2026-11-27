import { Component, signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Autofocus } from './autofocus';

@Component({
  imports: [Autofocus],
  template: `
    <input id="other" type="text" />
    @if (show()) {
      <input id="target" type="text" appAutofocus />
    }
  `,
})
class HostComponent {
  show = signal(true);
}

describe('Autofocus', () => {
  let fixture: ComponentFixture<HostComponent>;
  let host: HostComponent;

  async function render() {
    fixture.detectChanges();
    await fixture.whenStable();
  }

  function target(): HTMLInputElement | null {
    return fixture.nativeElement.querySelector('#target');
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HostComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HostComponent);
    host = fixture.componentInstance;
  });

  afterEach(() => {
    (document.activeElement as HTMLElement | null)?.blur();
  });

  it('should create the directive on the host element', async () => {
    await render();

    const directive = fixture.debugElement.query(By.directive(Autofocus))?.injector.get(Autofocus);

    expect(directive).toBeTruthy();
  });

  it('should focus the host element after it is rendered', async () => {
    await render();

    expect(target()).not.toBeNull();
    expect(document.activeElement).toBe(target());
  });

  it('should not focus anything when the element is not rendered', async () => {
    host.show.set(false);
    await render();

    expect(target()).toBeNull();
    expect(document.activeElement).not.toBe(fixture.nativeElement.querySelector('#other'));
  });

  it('should focus the element again each time it is re-rendered (e.g. modal reopened)', async () => {
    await render();
    expect(document.activeElement).toBe(target());

    // Move focus away, as a user would after interacting
    const other: HTMLInputElement = fixture.nativeElement.querySelector('#other');
    other.focus();
    expect(document.activeElement).toBe(other);

    // "Close" the modal
    host.show.set(false);
    await render();
    expect(target()).toBeNull();

    // "Reopen" the modal: a new directive instance should focus the new element
    host.show.set(true);
    await render();
    expect(document.activeElement).toBe(target());
  });
});
