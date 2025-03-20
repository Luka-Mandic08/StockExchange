import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOrderBookComponent } from './view-order-book.component';

describe('ViewOrderBookComponent', () => {
  let component: ViewOrderBookComponent;
  let fixture: ComponentFixture<ViewOrderBookComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewOrderBookComponent]
    });
    fixture = TestBed.createComponent(ViewOrderBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
