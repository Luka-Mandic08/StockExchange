import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  constructor(private httpClient: HttpClient, private router: Router){}

  setTokens(token: string) : void {
      localStorage.setItem('access_token',token)
  }

  decodeToken(token: string): any {
      try {
        return jwtDecode(token);
      } catch (error) {
        console.error('Invalid token', error);
        return null;
      }
    }
  
  isTokenValid(token: string): boolean {
      const decoded = this.decodeToken(token);
      if (!decoded) {
          return false;
      }
      return true;
  }

  IsLoggedIn(): boolean {
      let token = localStorage.getItem('access_token');
      if(token != null)
          return this.isTokenValid(token)
      return false
  }

  getEmailFromToken() : string {
      let token = localStorage.getItem('access_token');
      if(token != null){
          const tokenInfo = this.decodeToken(token);
          const id = tokenInfo.email;
          return id;
      }
      return ""
  }

  Logout() : void
  {
      localStorage.removeItem('access_token');
  }



}
