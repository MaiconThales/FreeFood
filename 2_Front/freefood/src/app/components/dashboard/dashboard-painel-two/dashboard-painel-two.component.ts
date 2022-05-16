import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material/snack-bar';

import { EventBusService } from 'src/app/shared';
import { EventData, Menu } from 'src/app/models';
import { MenuService } from 'src/app/services';

@Component({
  selector: 'app-dashboard-painel-two',
  templateUrl: './dashboard-painel-two.component.html',
  styleUrls: ['./dashboard-painel-two.component.css']
})
export class DashboardPainelTwoComponent implements OnInit {

  menusAll: Menu[] = [];

  constructor(
    private eventBusService: EventBusService,
    private translate: TranslateService,
    private menuService: MenuService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getAllMenus();
  }

  getAllMenus(): void {
    this.menuService.getAllMenu().subscribe({
      next: data => {
        this.menusAll = data;
      },
      error: err => {
        this.functionBusService(err);
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
