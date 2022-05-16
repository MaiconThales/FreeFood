import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { MyErrorStateMatcher } from 'src/app/shared';
import { Address } from 'src/app/models';
import { TranslateService } from '@ngx-translate/core';
import { TokenStorageService } from 'src/app/services';

@Component({
  selector: 'app-address-user',
  templateUrl: './address-user.component.html',
  styleUrls: ['./address-user.component.css']
})
export class AddressUserComponent {

  addressForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  labelClose!: string;
  labelSave!: string;

  constructor(
    public dialogRef: MatDialogRef<AddressUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Address,
    private translate: TranslateService,
    private token: TokenStorageService
  ) { 
    this.setLabels();
    this.createFormUser(data);
  }

  setLabels(): void {
    this.labelClose = this.translate.instant('GLOBAL_WORD.WORD_CLOSE');
    this.labelSave = this.translate.instant('GLOBAL_WORD.WORD_SAVE');
  }

  createFormUser(address: Address): void {
    this.addressForm = new FormGroup({
      id: new FormControl((address != null && address.id != null) ? address.id : ''),
      street: new FormControl((address != null && address.street != null) ? address.street : '', [Validators.required]),
      district: new FormControl((address != null && address.district != null) ? address.district : '', [Validators.required]),
      number: new FormControl((address != null && address.number != null) ? address.number : '', [Validators.required]),
      complement: new FormControl((address != null && address.complement != null) ? address.complement : ''),
      user: new FormControl((address != null && address.user != null) ? address.user : null)
    });
  }

  onSubmit(): void {
    let address: Address;
    address = {
      id: this.addressForm.get('id')?.value,
      street: this.addressForm.get('street')?.value,
      district: this.addressForm.get('district')?.value,
      number: this.addressForm.get('number')?.value,
      complement: this.addressForm.get('complement')?.value,
      user: this.addressForm.get('user')?.value != null ? this.addressForm.get('user')?.value : {id: this.token.getIdUser()}
    }
    
    this.dialogRef.close(address);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
