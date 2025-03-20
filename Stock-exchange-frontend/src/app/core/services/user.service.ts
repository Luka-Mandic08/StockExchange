import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterDto } from '../dto/register.dto';
import { Observable } from 'rxjs';
import { LoginDto } from '../dto/login.dto';
import { LoginResponse } from '../dto/login-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  baseUrl = `${environment.apiUrl}/users/`;

  register(dto : RegisterDto): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}register`,dto);
  }

  login(dto : LoginDto): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}login`,dto);
  }
}
