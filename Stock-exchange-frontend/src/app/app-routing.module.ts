import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './components/user/login-form/login-form.component';
import { RegisterComponent } from './components/user/register/register.component';
import { MyOrdersComponent } from './components/stockorder/my-orders/my-orders.component';
import { ViewOrderBookComponent } from './components/stockorder/view-order-book/view-order-book.component';
import { CreateStockOrderComponent } from './components/stockorder/create-stock-order/create-stock-order.component';

const routes: Routes = [
  { path: '', component: LoginFormComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'my-orders', component: MyOrdersComponent},
  { path: 'order-book', component: ViewOrderBookComponent},
  { path: 'create-order', component: CreateStockOrderComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
