import { Injectable } from '@angular/core';
import { Router, CanActivate, CanLoad } from '@angular/router';

import { AuthService } from '../';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanLoad, CanActivate {

  constructor(
    public authService: AuthService,
    public router: Router
  ) { }

  canLoad(): boolean {
    if(!this.authService.isAuthenticated()) {
      return false;
    }
    return true;
  }

  canActivate(): boolean {
    if(!this.authService.isAuthenticated()) {
      return false;
    }
    return true;
  }

}
