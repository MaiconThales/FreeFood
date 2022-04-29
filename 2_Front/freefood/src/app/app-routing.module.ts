import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { 
  DashboardPainelOneComponent,
  LoginAuthenticationComponent,
  RestaurantCrudComponent,
  MenuCruComponent,
  RequestCrudComponent
} from './components';

const routes: Routes = [
  { path: '', redirectTo: '/authentication', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardPainelOneComponent },
  { path: 'authentication', component: LoginAuthenticationComponent },
  { path: 'restaurant', component: RestaurantCrudComponent },
  { path: 'menu', component: MenuCruComponent },
  { path: 'request', component: RequestCrudComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
