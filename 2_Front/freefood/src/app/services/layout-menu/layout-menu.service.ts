import { Injectable, EventEmitter } from '@angular/core';

import { UserAuth } from '../../models';

@Injectable({
  providedIn: 'root'
})
export class LayoutMenuService {

  showMenu = new EventEmitter<boolean>();
  user = new EventEmitter<UserAuth>();

  constructor() { }

  setValueUser(u: UserAuth): void {
    this.user.emit(u);
  }

  alterValue(value: boolean): void {
    this.showMenu.emit(value);
  }

}
