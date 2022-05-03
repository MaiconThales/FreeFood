import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

import { environment as e } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private snackBar: MatSnackBar
  ) { }

  listAllUser(): Observable<any> {
    return this.http.get(e.AUTH_API + e.USER_CONTROLLER + '/all', { responseType: 'text' });
  }
  
}
