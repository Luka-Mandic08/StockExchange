import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil, tap, catchError, Observable, throwError } from 'rxjs';
import { RegisterDto } from 'src/app/core/dto/register.dto';
import { AlertService } from 'src/app/core/services/alert.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  private destroy$ = new Subject<void>()

  constructor(private fb: FormBuilder, private userService : UserService, private router: Router, private swal : AlertService) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const dto : RegisterDto = {
        name : this.registerForm.get('name')?.value,
        surname : this.registerForm.get('surname')?.value,
        email : this.registerForm.get('email')?.value,
        password : this.registerForm.get('password')?.value
      };
      this.userService.register(dto).pipe(takeUntil(this.destroy$), tap(
        response => {
          this.swal.fireSwalSuccess("Account created successfully")
          this.router.navigate([''])
        }
      ), catchError(
        (error: HttpErrorResponse): Observable<any> => {
            this.swal.fireSwalError(error.error.detail)
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
