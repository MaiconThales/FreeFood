import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { RestaurantDialogComponent, RestaurantLiberateComponent } from '../';
import { RestaurantService, TokenStorageService } from 'src/app/services';
import { Restaurant } from 'src/app/models';

@Component({
  selector: 'app-restaurant-crud',
  templateUrl: './restaurant-crud.component.html',
  styleUrls: ['./restaurant-crud.component.css']
})
export class RestaurantCrudComponent implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['id', 'name', 'address', 'options'];
  dataSource!: MatTableDataSource<Restaurant>;
  restaurants: Restaurant[] = [];

  constructor(
    public dialog: MatDialog,
    private restaurantService: RestaurantService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private token: TokenStorageService
  ) { }

  ngOnInit(): void {
    this.getRestaurant();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getRestaurant(): void {
    this.restaurantService.getRestaurant(this.token.getIdUser()).subscribe({
      next: data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.restaurants = data;
      },
      error: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  openDialogCreateRestaurant(type: number, object: any): void {
    const dialogRef = this.dialog.open(RestaurantDialogComponent, {
      width: '500px',
      height: '220px',
      data: object
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        switch (type) {
          case 1:
            this.saveRestaurant(result);
            break;
          case 2:
            this.updateRestaurant(result);
            break;
        }
      }
    });
  }

  openDialogLiberateRestaurant(restaurant: Restaurant): void {
    const dialogRef = this.dialog.open(RestaurantLiberateComponent, {
      width: '500px',
      height: '220px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.liberateRestaurant(restaurant, result);
      }
    });
  }

  saveRestaurant(restaurant: Restaurant): void {
    this.restaurantService.saveRestaurant(restaurant).subscribe({
      next: () => {
        this.getRestaurant();
      },
      error: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  updateRestaurant(restaurant: Restaurant): void {
    this.restaurantService.updateRestaurant(restaurant, this.token.getIdUser()).subscribe({
      next: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.MSG_EDIT'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
        this.getRestaurant();
      },
      error: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  liberateRestaurant(restaurant: Restaurant, username: String): void {
    this.restaurantService.liberateRestaurant(restaurant, this.token.getIdUser(), username). subscribe({
      next: data => {
        this.snackBar.open(this.translate.instant(data.message), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      },
      error: err => {
        this.snackBar.open(this.translate.instant(err.error.message), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  removeRestaurant(idRestaurant: number): void {
    this.restaurantService.removeRestaurant(idRestaurant, this.token.getIdUser()).subscribe({
      next: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.MSG_REMOVE'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
        this.getRestaurant();
      },
      error: () => {
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

}
