import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, Observable, Subject, takeUntil, tap, throwError } from 'rxjs';
import { JwtService } from '../../../core/services/jwt.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AlertService } from '../../../core/services/alert.service';
import { UserService } from 'src/app/core/services/user.service';
import { LoginDto } from 'src/app/core/dto/login.dto';
import { WebsocketService } from 'src/app/core/services/websocket.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  loginForm: FormGroup;
  private destroy$ = new Subject<void>()

  constructor(private fb: FormBuilder, private userService : UserService, private jwtService: JwtService, private router: Router, private swal : AlertService, private webSocketService : WebsocketService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const dto : LoginDto = {
        email : this.loginForm.get('email')?.value,
        password : this.loginForm.get('password')?.value
      };
      this.userService.login(dto).pipe(takeUntil(this.destroy$), tap(
        response => {
          this.jwtService.setTokens(response.token)
          if(this.jwtService.IsLoggedIn()) {
              this.router.navigate(['order-book'])
              this.webSocketService.connect(response.token)
          }
          else
          {
            this.swal.fireSwalError("An error occured while reading token")
          }
        }
      ), catchError(
        (error: HttpErrorResponse): Observable<any> => {
            this.swal.fireSwalError("Wrong credentials")
            return throwError(() => error);
        },
      )).subscribe()
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
