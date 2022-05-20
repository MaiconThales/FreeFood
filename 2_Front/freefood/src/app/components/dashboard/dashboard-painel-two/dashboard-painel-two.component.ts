import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormArray, FormBuilder } from '@angular/forms'

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

  generalForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  displayedColumns: string[] = ['select', 'idMenu', 'name', 'description'];
  dataSource!: MatTableDataSource<Menu>;
  selection = new SelectionModel<Menu>(true, []);

  isShowStep2: boolean = false;

  labelStep1!: string;
  labelStep2!: string;
  labelStep3!: string;

  constructor(
    private actRoute: ActivatedRoute,
    private menuService: MenuService,
    private eventBusService: EventBusService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private token: TokenStorageService,
    private addressService: AddressService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.idRestaurantSelect = this.actRoute.snapshot.params['id'];
    this.setLabels();
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
      this.setRequest(null, 2, false);
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
    this.setRequest(null, 2, true);
  }
  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Menu): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idMenu}`;
  }
  
  createForm(): void {
    this.generalForm = this.fb.group({
      request: this.fb.array([])
    });
  }
  get request(): FormArray {
    return this.generalForm.get("request") as FormArray
  }
  newRequest(menu: Menu): FormGroup {
    return this.fb.group({
      amount: new FormControl('', [Validators.required, Validators.min(1)]),
      observation: new FormControl(''),
      menu: new FormGroup({
        idMenu: new FormControl(menu.idMenu),
        name: new FormControl({value: menu.name, disabled: true})
      })
    })
  }
  addRequest(menu: Menu) {
    this.request.push(this.newRequest(menu));
  }
  removeRequest(i: number) {
    this.request.removeAt(i);
  }
  onSubmit() {
    console.log(this.selection);
    console.log(this.selection.selected);
  }
  setRequest(value: any, type: number, check: boolean): void {
    switch (type) {
      case 1:
        if (check) {
          this.addRequest(value);
          this.isShowStep2 = true;
        } else {
          let index = (<FormArray>this.generalForm.get('request')).length;
          for (let i = 0; i < index; i++) {
            let id = (<FormArray>this.generalForm.get('request')).controls[i].get('menu')?.get('idMenu')?.value;
            if (id === value.idMenu) {
              this.removeRequest(i);
              break;
            }
          }
          if(index > 1) {
            this.isShowStep2 = true;
          } else {
            this.isShowStep2 = false;
          }
        }
        break;
      case 2:
        if (check) {
          let item = (<FormArray>this.generalForm.get('request')).length;
          for (let i = 0; i < this.selection.selected.length; i++) {
            if(item == 0) {
              this.addRequest(this.selection.selected[i]);
            } else {
              for (let j = 0; j < item; j++) {
                if(!((<FormArray>this.generalForm.get('request')).controls[j].get('menu')?.get('idMenu')?.value == this.selection.selected[i].idMenu)) {
                  this.addRequest(this.selection.selected[i]);
                }
              }
            }
          }
          this.isShowStep2 = true;
        } else {
          let item = (<FormArray>this.generalForm.get('request')).length;
          for (let i = 0; i < item; i++) {
            this.removeRequest(0);
          }
          this.isShowStep2 = false;
        }
        break;
    }
  }

  setLabels(): void {
    this.labelStep1 = this.translate.instant('DASHBOARD.LABEL_SELECT_MENU');
    this.labelStep2 = this.translate.instant('DASHBOARD.LABEL_CONFIG_MENU');
    this.labelStep3 = this.translate.instant('DASHBOARD.LABEL_FINISH_MENU');
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

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
