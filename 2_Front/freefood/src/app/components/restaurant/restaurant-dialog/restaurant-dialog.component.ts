import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';

import { Restaurant } from 'src/app/models';
import { TokenStorageService } from 'src/app/services';
import { MyErrorStateMatcher } from 'src/app/shared';

@Component({
  selector: 'app-restaurant-dialog',
  templateUrl: './restaurant-dialog.component.html',
  styleUrls: ['./restaurant-dialog.component.css']
})
export class RestaurantDialogComponent {

  restaurantForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  isCreate: boolean = true;

  labelClose!: string;
  labelSave!: string;

  constructor(
    public dialogRef: MatDialogRef<RestaurantDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Restaurant,
    private token: TokenStorageService,
    private translate: TranslateService
  ) { 
    if(data == null) {
      this.isCreate = true;
    } else {
      this.isCreate = false;
    }

    this.setLabels();
    this.createForm();
  }

  setLabels(): void {
    this.labelClose = this.translate.instant('GLOBAL_WORD.WORD_CLOSE');
    this.labelSave = this.translate.instant('GLOBAL_WORD.WORD_SAVE');
  }

  createForm(): void {
    if(this.isCreate) {
      this.restaurantForm = new FormGroup({
        name: new FormControl('', [Validators.required]),
        address: new FormControl('', [Validators.required])
      });
    } else {
      this.restaurantForm = new FormGroup({
        id: new FormControl(this.data.id),
        name: new FormControl(this.data.name, [Validators.required]),
        address: new FormControl(this.data.address, [Validators.required])
      });
    }
  }

  onSubmit(): void {
    let restaurant: Restaurant;
    if(this.isCreate) {
      restaurant = {
        id: this.restaurantForm.get('id')?.value,
        name: this.restaurantForm.get('name')?.value,
        address: this.restaurantForm.get('address')?.value,
        users: [{id: this.token.getIdUser().toString()}]
      }
    } else {
      restaurant = {
        id: this.restaurantForm.get('id')?.value,
        name: this.restaurantForm.get('name')?.value,
        address: this.restaurantForm.get('address')?.value,
        users: this.data.users
      }
    }
    
    this.dialogRef.close(restaurant);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
