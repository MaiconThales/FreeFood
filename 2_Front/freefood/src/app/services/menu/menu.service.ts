import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment as e } from '../../../environments/environment.prod';
import { Menu } from 'src/app/models';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(
    private http: HttpClient
  ) { }

  getMenu(idRestaurant: number, idUser: number): Observable<any> {
    let param: any = {'idRestaurant': idRestaurant, 'idUser': idUser};
    return this.http.get(e.AUTH_API + e.MENU_CONTROLLER + '/getMenu', { params: param });
  }

  saveMenu(menu: Menu): Observable<any> {
    return this.http.post(e.AUTH_API + e.MENU_CONTROLLER + '/createMenu', menu);
  }

  removeMenu(idMenu: any, idUser: number, idRestaurant: any): Observable<any> {
    let param: any = {'idMenu': idMenu, 'idUser': idUser, 'idRestaurant': idRestaurant};
    return this.http.delete(e.AUTH_API + e.MENU_CONTROLLER + '/deleteMenu', { params: param });
  }

  updateMenu(menu: Menu): Observable<any> {
    return this.http.put(e.AUTH_API + e.MENU_CONTROLLER + '/updateMenu', menu);
  }

}
