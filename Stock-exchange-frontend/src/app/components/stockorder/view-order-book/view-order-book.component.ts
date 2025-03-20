import { Component } from '@angular/core';
import { takeUntil, tap } from 'rxjs';
import { Subject } from 'rxjs/internal/Subject';
import { StockOrder } from 'src/app/core/model/stock-order';
import { StockOrderService } from 'src/app/core/services/stock-order.service';

@Component({
  selector: 'app-view-order-book',
  templateUrl: './view-order-book.component.html',
  styleUrls: ['./view-order-book.component.css']
})
export class ViewOrderBookComponent {

  private destroy$ = new Subject<void>();

  displayedColumns: string[] = [
    'userEmail',
    'amount',
    'price',
  ];

  buyingOrders !: StockOrder[] 
  sellingOrders !: StockOrder[] 

  constructor(private stockOrderService : StockOrderService) { }


  ngOnInit() {
    this.stockOrderService.getBuying().pipe(
      takeUntil(this.destroy$),
      tap( response =>
        this.buyingOrders = response
      )
    ).subscribe()

    this.stockOrderService.getSelling().pipe(
      takeUntil(this.destroy$),
      tap( response =>
        this.sellingOrders = response
      )
    ).subscribe()
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
