import { OrElsePipe } from './or-else-pipe';

describe('OrElsePipe', () => {
  let pipe: OrElsePipe;

  beforeEach(() => {
    pipe = new OrElsePipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('returns "-" for null', () => {
    expect(pipe.transform(null)).toBe('-');
  });

  it('returns "-" for undefined', () => {
    expect(pipe.transform(undefined)).toBe('-');
  });

  it('returns "-" for ""', () => {
    expect(pipe.transform('')).toBe('-');
  });

  it('returns "-" for "   "', () => {
    expect(pipe.transform('   ')).toBe('-');
  });

  it('returns "abc" for "abc"', () => {
    expect(pipe.transform('abc')).toBe('abc');
  });

  it('returns "0" for "0"', () => {
    expect(pipe.transform('0')).toBe('0');
  });

  it('returns 0 for 0', () => {
    expect(pipe.transform(42)).toBe(42);
  });

  it('returns 42 for 42', () => {
    expect(pipe.transform(42)).toBe(42);
  });

  it('returns the same object for {}', () => {
    const obj = {};
    expect(pipe.transform(obj)).toBe(obj);
  });

  it('returns the same Date for new Date()', () => {
    const date = new Date();
    expect(pipe.transform(date)).toBe(date);
  });

  it('returns 0 for 0', () => {
    expect(pipe.transform(0)).toBe(0);
  });

  it('returns -0 for -0', () => {
    expect(Object.is(pipe.transform(-0), -0)).toBe(true);
  });
});
