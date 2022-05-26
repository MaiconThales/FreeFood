import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

import { Address, EventData, Menu } from 'src/app/models';
import { AddressService, ShoppingCarService, TokenStorageService } from 'src/app/services';
import { EventBusService } from 'src/app/shared';
import { AddressSelectDialogComponent } from 'src/app/components'

@Component({
  selector: 'app-shopping-car',
  templateUrl: './shopping-car.component.html',
  styleUrls: ['./shopping-car.component.css']
})
export class ShoppingCarComponent implements OnInit {

  idUserLogged!: number;
  menusSelect: Menu[] = [];
  address!: Address[];
  addressSelect!: Address;

  constructor(
    private shoppingCarService: ShoppingCarService,
    private token: TokenStorageService,
    private addressService: AddressService,
    private eventBusService: EventBusService,
    private translate: TranslateService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.idUserLogged = this.token.getIdUser();
    this.getShoppingCar();
    this.getAddressByUser();
  }

  getShoppingCar(): void {
    this.menusSelect = this.shoppingCarService.getShoppingCar();
  }

  removeItemShoppingCar(idMenu: any): void {
    this.shoppingCarService.removeMenuInShoppingCar(idMenu);
    this.getShoppingCar();
  }

  getAddressByUser(): void {
    this.addressService.getAddressByUser(this.idUserLogged).subscribe({
      next: data => {
        if(data != null) {
          data.forEach((d: Address) => {
            if(d.isDefault) {
              this.addressSelect = d;
            }
          });
          this.address = data;
        }
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

  openDialogSelectAddress(): void {
    const dialogRef = this.dialog.open(AddressSelectDialogComponent, {
      width: '500px',
      height: '280px',
      data: {
        address: this.address,
        addressSelect: this.addressSelect
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.addressSelect = result;
      }
    });
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
