import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LayoutMenuService {

  showMenu = new EventEmitter<boolean>();

  constructor() { }

  alterValue(value: boolean): void {
    this.showMenu.emit(value);
  }

}
