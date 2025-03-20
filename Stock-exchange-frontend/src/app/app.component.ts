import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subject, takeUntil, tap } from 'rxjs';
import { WebsocketService } from './core/services/websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private webSocketService: WebsocketService) {}

  ngOnInit() {
    const authToken = localStorage.getItem('access_token');
    if (authToken) {
      this.webSocketService.connect(authToken);
    }
  }
  
}
