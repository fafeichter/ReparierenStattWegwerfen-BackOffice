export function scrollToSection(sectionId: string): void {
  if (typeof document === 'undefined') return;
  document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
}
