import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebsocketNotificationComponent } from './websocket-notification.component';

describe('WebsocketNotificationComponent', () => {
  let component: WebsocketNotificationComponent;
  let fixture: ComponentFixture<WebsocketNotificationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WebsocketNotificationComponent]
    });
    fixture = TestBed.createComponent(WebsocketNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
