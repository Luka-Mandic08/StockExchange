import { Injectable } from '@angular/core';
import { NewStockOrderDto } from '../dto/new-stock-order.dto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CreateStockOrderResponseDto } from '../dto/create-stock-response.dto';
import { OrdersResponseDto } from '../dto/orders-response.dto';
import { StockOrder } from '../model/stock-order';

@Injectable({
  providedIn: 'root'
})
export class StockOrderService {

  constructor(private http: HttpClient) { }
    baseUrl = `${environment.apiUrl}/stockorders/`;
  
    create(dto : NewStockOrderDto): Observable<CreateStockOrderResponseDto> {
      return this.http.post<CreateStockOrderResponseDto>(`${this.baseUrl}create`,dto);
    }

    getBuying(): Observable<StockOrder[]> {
      return this.http.get<StockOrder[]>(`${this.baseUrl}gettopbuying`)
    }

    getSelling(): Observable<StockOrder[]> {
      return this.http.get<StockOrder[]>(`${this.baseUrl}gettopselling`)
    }
}
