import { Injectable } from '@angular/core';

import { LayoutMenuService } from '../index';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor(
    private layoutMenuService: LayoutMenuService
  ) { }

  signOut(): void {
    window.sessionStorage.clear();
    this.layoutMenuService.alterValue(false);
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    var token = window.sessionStorage.getItem(TOKEN_KEY);
    if(token != null) {
      this.layoutMenuService.alterValue(true);
    }
    return token;
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }

}
