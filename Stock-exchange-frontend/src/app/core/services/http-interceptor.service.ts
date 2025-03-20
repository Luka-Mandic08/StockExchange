import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService {

  constructor(private jwtService : JwtService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('access_token');
    if (token && this.jwtService.isTokenValid(token)) {
      const authRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
          
        },
      });
      return next.handle(authRequest)
    }
    return next.handle(request);
  }
}
