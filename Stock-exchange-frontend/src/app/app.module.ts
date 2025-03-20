import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './components/user/register/register.component';
import { LoginFormComponent } from './components/user/login-form/login-form.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule} from '@angular/material/button';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule} from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatTableModule} from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { HttpInterceptorService } from './core/services/http-interceptor.service';
import { MyOrdersComponent } from './components/stockorder/my-orders/my-orders.component';
import { WebsocketNotificationComponent } from './components/stockorder/websocket-notification/websocket-notification.component';
import { CreateStockOrderComponent } from './components/stockorder/create-stock-order/create-stock-order.component';
import { ViewOrderBookComponent } from './components/stockorder/view-order-book/view-order-book.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    RegisterComponent,
    MyOrdersComponent,
    WebsocketNotificationComponent,
    CreateStockOrderComponent,
    ViewOrderBookComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    MatTableModule,
    MatSelectModule,
    HttpClientModule,
    NoopAnimationsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
