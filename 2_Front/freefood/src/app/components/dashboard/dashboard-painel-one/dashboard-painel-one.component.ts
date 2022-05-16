import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { EventData, Menu } from 'src/app/models';
import { MenuService } from 'src/app/services';
import { EventBusService } from 'src/app/shared';
import { environment as e } from 'src/environments/environment.prod';

@Component({
  selector: 'app-dashboard-painel-one',
  templateUrl: './dashboard-painel-one.component.html',
  styleUrls: ['./dashboard-painel-one.component.css']
})
export class DashboardPainelOneComponent implements OnInit {

  menusAll: Menu[] = [];

  constructor(
    private eventBusService: EventBusService,
    private menuService: MenuService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.getAllMenus()
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

  redirectDashboardTwo(): void {
    this.route.navigate([e.REDIRECT_DASHBOARD_TWO]);
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
