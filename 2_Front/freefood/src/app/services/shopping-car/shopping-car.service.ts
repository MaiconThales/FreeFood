import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

import { Menu } from 'src/app/models';

const SHOPPING_KEY = 'shopping-car';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCarService {

  constructor(
    private translate: TranslateService,
    private snackBar: MatSnackBar
  ) { }

  getShoppingCar(): Menu[] {
    let value = window.sessionStorage.getItem(SHOPPING_KEY);
    let result = [];

    if(value != null) {
      result = JSON.parse(value);
    }

    return result;
  }

  addMenuInShoppingCar(menu: Menu): void {
    if(window.sessionStorage.getItem(SHOPPING_KEY) == null) {
      window.sessionStorage.setItem(SHOPPING_KEY, JSON.stringify(menu));
      this.showSnackBar('GLOBAL_WORD.MSG_ADD_SHOPPING_CAR');
    } else {
      let value = [];
      let valueSessionStorage = window.sessionStorage.getItem(SHOPPING_KEY);
      
      if(!this.verifyItemExists(menu)) {
        if(valueSessionStorage != null) {
          value.push(JSON.parse(valueSessionStorage));
        }
        value.push(menu);
        window.sessionStorage.setItem(SHOPPING_KEY, JSON.stringify(value));
        this.showSnackBar('GLOBAL_WORD.MSG_ADD_SHOPPING_CAR');
      } else {
        this.showSnackBar('GLOBAL_WORD.MSG_ITEM_REPEAT');
      }
    }
  }

  verifyItemExists(menu: Menu): boolean {
    let items = this.getShoppingCar();

    if(items.length > 0) {
      for(let i=0; i<items.length; i++) {
        if(items[i].idMenu == menu.idMenu) {
          return true;
        }
      }
    }

    return false;
  }

  removeMenuInShoppingCar(idMenu: number): void {
    let items = this.getShoppingCar();
    if(items.length == 1) {
      window.sessionStorage.removeItem(SHOPPING_KEY);
    } else {
      for(let i=0; i<items.length; i++) {
        if(items[i].idMenu == idMenu) {
          items.splice(i, 1);
        }
      }
      window.sessionStorage.removeItem(SHOPPING_KEY);
      window.sessionStorage.setItem(SHOPPING_KEY, JSON.stringify(items));
    }
    this.showSnackBar('GLOBAL_WORD.MSG_REMOVE');
  }

  showSnackBar(msg: string): void {
    this.snackBar.open(this.translate.instant(msg), 'Ok', {
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      duration: 10000
    });
  }

}
