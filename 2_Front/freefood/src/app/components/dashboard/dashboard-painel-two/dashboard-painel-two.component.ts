import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { EventBusService, MyErrorStateMatcher } from 'src/app/shared';
import { EventData, Menu, Request, Restaurant, User } from 'src/app/models';
import { AddressService, MenuService, TokenStorageService } from 'src/app/services';

@Component({
  selector: 'app-dashboard-painel-two',
  templateUrl: './dashboard-painel-two.component.html',
  styleUrls: ['./dashboard-painel-two.component.css'],
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { displayDefaultIndicatorType: false },
    },
  ],
})
export class DashboardPainelTwoComponent implements OnInit {

  idRestaurantSelect!: number;
  request!: Request;

  requestForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  displayedColumns: string[] = ['select', 'idMenu', 'name', 'description'];
  dataSource!: MatTableDataSource<Menu>;
  selection = new SelectionModel<Menu>(true, []);

  constructor(
    private actRoute: ActivatedRoute,
    private menuService: MenuService,
    private eventBusService: EventBusService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private token: TokenStorageService,
    private addressService: AddressService
  ) { }

  ngOnInit(): void {
    this.idRestaurantSelect = this.actRoute.snapshot.params['id'];
    this.createForm();
    this.getMenuByRestaurant();
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    let numSelected = 0;
    let numRows = 0;
    if (this.dataSource != null) {
      numSelected = this.selection.selected.length;
      numRows = this.dataSource.data.length;
    }
    return numSelected === numRows;
  }
  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }
  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Menu): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idMenu}`;
  }

  getMenuByRestaurant(): void {
    this.menuService.getMenuByRestaurant(this.idRestaurantSelect).subscribe({
      next: data => {
        this.dataSource = new MatTableDataSource<Menu>(data);
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

  createForm(): void {
    this.requestForm = new FormGroup({
      amount: new FormControl(1, [Validators.required, Validators.min(1)]),
      observation: new FormControl('')
    })
  }

  setRequest(): void {
    let menu: Menu[] = this.selection.selected;
    if(menu.length > 0) {
      menu.forEach(function(m) {

      });
    }

    let user: User = {
      id: this.token.getIdUser().toString()
    };
    let restaurant: Restaurant = {
      id: menu[0].restaurant.id,
      name: menu[0].name
    };
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
