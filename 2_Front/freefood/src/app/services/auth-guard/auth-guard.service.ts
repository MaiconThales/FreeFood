import { Injectable } from '@angular/core';
import { Router, CanActivate, CanLoad, Route, UrlSegment, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

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
    if(this.authService.isAuthenticated()) {
      return true;
    }
    return false;
  }

  canActivate(): boolean {
    if(this.authService.isAuthenticated()) {
      return true;
    }
    return false;
  }

}
