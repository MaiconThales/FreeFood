import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

import { environment as e } from '../../../environments/environment.prod';
import { LoginUser, User } from '../../models';
import { TokenStorageService } from '../';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    public jwtHelper: JwtHelperService,
    private tokenStorageService: TokenStorageService
  ) { }

  login(userLogin: LoginUser): Observable<any> {
    return this.http.post(e.AUTH_API + e.USER_CONTROLLER + '/authenticate', userLogin, httpOptions);
  }

  register(userData: User): Observable<any> {
    return this.http.post(e.AUTH_API + e.USER_CONTROLLER + '/register', userData, httpOptions);
  }

  public isAuthenticated(): boolean {
    const token = this.tokenStorageService.getToken();

    if(token != undefined) {
      return !this.jwtHelper.isTokenExpired(token);
    } else {
      return false;
    }
  }

}
