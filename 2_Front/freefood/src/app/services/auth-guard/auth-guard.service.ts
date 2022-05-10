import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

import { AuthService, TokenStorageService } from '../';
import { environment as e } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    public authService: AuthService,
    public router: Router,
    private token: TokenStorageService
  ) { }

  canActivate(): boolean {
    /*if(!this.authService.isAuthenticated()) {
      this.router.navigate([e.REDIRECT_AUTHENTICATION]);
      this.token.signOut();
      return false;
    }*/
    return true;
  }

}
