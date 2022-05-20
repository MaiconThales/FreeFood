import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';

import { EventData, Menu } from 'src/app/models';
import { MenuService, ShoppingCarService } from 'src/app/services';
import { EventBusService } from 'src/app/shared';
import { DashboardDialogDetailMenuComponent } from 'src/app/components/dashboard';

@Component({
  selector: 'app-dashboard-painel-two',
  templateUrl: './dashboard-painel-two.component.html',
  styleUrls: ['./dashboard-painel-two.component.css']
})
export class DashboardPainelTwoComponent implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['name', 'options'];
  dataSource!: MatTableDataSource<Menu>;
  menus: Menu[] = [];

  idRestaurantSelect!: number;

  labelMoreDetail!: string;
  labelAddMenu!: string;

  constructor(
    private actRoute: ActivatedRoute,
    private menuService: MenuService,
    private eventBusService: EventBusService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    public dialog: MatDialog,
    private shoppingCar: ShoppingCarService
  ) { }

  ngOnInit(): void {
    this.idRestaurantSelect = this.actRoute.snapshot.params['id'];
    this.getLabel();
    this.getMenuByRestaurant();
  }

  getLabel(): void {
    this.labelMoreDetail = this.translate.instant('DASHBOARD.MORE_DETAIL_MENU');
    this.labelAddMenu = this.translate.instant('DASHBOARD.ADD_MENU_SHOP');
  }

  getMenuByRestaurant(): void {
    this.menuService.getMenuByRestaurant(this.idRestaurantSelect).subscribe({
      next: data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.menus = data;
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

  openDialogDetailMenu(obj: Menu): void {
    const dialogRef = this.dialog.open(DashboardDialogDetailMenuComponent, {
      width: '500px',
      height: '310px',
      data: obj
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        
      }
    });
  }

  addShoppingCar(menu: Menu): void {
    this.shoppingCar.addMenuInShoppingCar(menu);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
