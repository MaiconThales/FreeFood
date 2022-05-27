import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';

import { Address } from 'src/app/models';

@Component({
  selector: 'app-address-select-dialog',
  templateUrl: './address-select-dialog.component.html',
  styleUrls: ['./address-select-dialog.component.css']
})
export class AddressSelectDialogComponent {

  selectAddressId!: number;
  selectAddress!: Address;
  address!: Address[];

  labelClose!: string;
  labelSave!: string;

  constructor(
    public dialogRef: MatDialogRef<AddressSelectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private translate: TranslateService
  ) { 
    this.address = data.address;
    this.setLabels();
    this.setAddressSelect();
  }

  setAddressSelect(): void {
    this.selectAddressId = this.data.addressSelect.id;
  }

  setLabels(): void {
    this.labelClose = this.translate.instant('GLOBAL_WORD.WORD_CLOSE');
    this.labelSave = this.translate.instant('GLOBAL_WORD.WORD_SAVE');
  }

  alterAddress(): void {
    this.address.forEach((a) => {
      if(a.id == this.selectAddressId) {
        this.selectAddress = a;
      }
    });
    this.dialogRef.close(this.selectAddress);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
