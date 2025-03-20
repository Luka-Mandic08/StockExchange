import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil, tap, catchError, Observable, throwError } from 'rxjs';
import { NewStockOrderDto } from 'src/app/core/dto/new-stock-order.dto';
import { RegisterDto } from 'src/app/core/dto/register.dto';
import { AlertService } from 'src/app/core/services/alert.service';
import { StockOrderService } from 'src/app/core/services/stock-order.service';

@Component({
  selector: 'app-create-stock-order',
  templateUrl: './create-stock-order.component.html',
  styleUrls: ['./create-stock-order.component.css']
})
export class CreateStockOrderComponent {
  createOrderForm: FormGroup;
  private destroy$ = new Subject<void>()

  constructor(private fb: FormBuilder, private stockOrderService : StockOrderService, private router: Router, private swal : AlertService) {
    this.createOrderForm = this.fb.group({
      amount: ['', Validators.required],
      price: ['', Validators.required],
      matchType: [null, Validators.required],
      orderType: [null, Validators.required]
    });
  }

  onSubmit() {
    if (this.createOrderForm.valid) {
      const dto : NewStockOrderDto = {
        amount : this.createOrderForm.get('amount')?.value,
        price : this.createOrderForm.get('price')?.value,
        stockOrderMatchingType : this.createOrderForm.get('matchType')?.value,
        stockOrderType : this.createOrderForm.get('orderType')?.value
      };
      this.stockOrderService.create(dto).pipe(takeUntil(this.destroy$), tap(
        response => {
          this.swal.fireSwalSuccess(response.message)
          this.router.navigate(['order-book'])
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
