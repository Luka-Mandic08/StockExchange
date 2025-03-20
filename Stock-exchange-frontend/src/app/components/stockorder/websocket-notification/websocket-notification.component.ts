import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/core/services/alert.service';
import { WebsocketService } from 'src/app/core/services/websocket.service';

@Component({
  selector: 'app-websocket-notification',
  templateUrl: './websocket-notification.component.html',
  styleUrls: ['./websocket-notification.component.css']
})
export class WebsocketNotificationComponent {
  constructor(private webSocketService: WebsocketService, private swal : AlertService, private router : Router) {}

  logout(){
    localStorage.clear()
    this.router.navigate([''])
    this.webSocketService.disconnect()
  }

  ngOnInit() {
    this.webSocketService.messages$.subscribe((msg) => {
      if (msg) {
        this.swal.fireSwalSuccess(msg.message,15000)
      }
    });
  }
}
