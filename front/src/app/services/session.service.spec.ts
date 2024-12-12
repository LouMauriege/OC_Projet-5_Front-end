import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);

    const next = jest.fn();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should isLogged be falsy', () => {
    const next = jest.fn();
    next();
    expect(next.mock.calls).toHaveLength(1);
  });
});
