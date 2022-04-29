import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuCruComponent } from './menu-cru.component';

describe('MenuCruComponent', () => {
  let component: MenuCruComponent;
  let fixture: ComponentFixture<MenuCruComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuCruComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuCruComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
