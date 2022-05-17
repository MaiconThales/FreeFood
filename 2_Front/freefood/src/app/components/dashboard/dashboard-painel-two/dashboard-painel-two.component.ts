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
  ) { }

  ngOnInit(): void {
  }

}
