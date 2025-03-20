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

  constructor(private webSocketService: WebsocketService, private router : Router) {}

  showNavbar = true;
  private readonly destroy$ = new Subject<void>();

  ngOnInit() {
    const authToken = localStorage.getItem('access_token');
    if (authToken) {
      this.webSocketService.connect(authToken);
    }
  
    this.router.events.pipe(takeUntil(this.destroy$),tap((event) =>{
      if (event instanceof NavigationEnd) {
        // List of routes that should not show the navbar
        const excludedRoutes = ['/', '/register'];
        const currentRoute = event.urlAfterRedirects;

        // Check if the route matches any of the excluded routes
        this.showNavbar =
          !excludedRoutes.includes(currentRoute)
      }
    })).subscribe();
  }
  
}
