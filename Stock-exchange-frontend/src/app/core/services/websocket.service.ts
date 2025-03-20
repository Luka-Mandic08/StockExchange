import { Injectable } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private socket$: WebSocketSubject<any> | null = null;
  private messageSubject = new BehaviorSubject<any>(null);
  messages$ = this.messageSubject.asObservable();

  constructor() {}

  /** Connect to WebSocket with auth token */
  connect(authToken: string) {
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = new WebSocketSubject({
        url: `ws://localhost:9090/order-matching`,
        protocol: authToken, // Send token in the WebSocket "Sec-WebSocket-Protocol" header
      });
      console.log("Useo valjda")
      this.socket$.subscribe({
        next: (message) => this.messageSubject.next(message),
        error: (err) => console.error('WebSocket error:', err),
        complete: () => console.warn('WebSocket connection closed'),
      });
    }
  }

  /** Send data to WebSocket server */
  sendMessage(message: any) {
    if (this.socket$) {
      this.socket$.next(message);
    }
  }

  /** Close WebSocket on logout */
  disconnect() {
    if (this.socket$) {
      this.socket$.complete();
      this.socket$ = null;
    }
  }
}
