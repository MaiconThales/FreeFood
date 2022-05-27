import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';

import { Menu } from 'src/app/models';
import { ShoppingCarService } from 'src/app/services';

@Component({
  selector: 'app-dashboard-dialog-detail-menu',
  templateUrl: './dashboard-dialog-detail-menu.component.html',
  styleUrls: ['./dashboard-dialog-detail-menu.component.css']
})
export class DashboardDialogDetailMenuComponent {

  labelAddMenu!: string;

  constructor(
    public dialogRef: MatDialogRef<DashboardDialogDetailMenuComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Menu,
    private translate: TranslateService,
    private shoppingCar: ShoppingCarService
  ) {
    this.getLabels();
  }

  getLabels(): void {
    this.labelAddMenu = this.translate.instant('DASHBOARD.ADD_MENU_SHOP');
  }

  addShoppingCar(): void {
    this.shoppingCar.addMenuInShoppingCar(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
