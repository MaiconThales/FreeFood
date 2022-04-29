import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestCrudComponent } from './request-crud.component';

describe('RequestCrudComponent', () => {
  let component: RequestCrudComponent;
  let fixture: ComponentFixture<RequestCrudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestCrudComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
