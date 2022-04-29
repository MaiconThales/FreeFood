import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService as AuthGuard } from './services';

import { 
  DashboardPainelOneComponent,
  LoginAuthenticationComponent,
  RestaurantCrudComponent,
  MenuCruComponent,
  RequestCrudComponent
} from './components';

const routes: Routes = [
  
  { path: 'authentication', component: LoginAuthenticationComponent },
  { path: 'dashboard', component: DashboardPainelOneComponent, canActivate: [AuthGuard] },
  { path: 'restaurant', component: RestaurantCrudComponent, canActivate: [AuthGuard] },
  { path: 'menu', component: MenuCruComponent, canActivate: [AuthGuard] },
  { path: 'request', component: RequestCrudComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/authentication', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
