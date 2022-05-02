import { TestBed } from '@angular/core/testing';

import { LayoutMenuService } from './layout-menu.service';

describe('LayoutMenuService', () => {
  let service: LayoutMenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LayoutMenuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
